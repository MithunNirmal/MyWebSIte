package com.mithunnirmal.merch.services;

import com.mithunnirmal.merch.auth.AuthenticationResponse;
import com.mithunnirmal.merch.config.JwtService;
import com.mithunnirmal.merch.entities.Address;
import com.mithunnirmal.merch.entities.User;
import com.mithunnirmal.merch.entities.verificationtoken.VerificationToken;
import com.mithunnirmal.merch.enums.UserRole;
import com.mithunnirmal.merch.event.RegistrationCompleteEvent;
import com.mithunnirmal.merch.exception.UserEmailTakenException;
import com.mithunnirmal.merch.exception.UserNotVerifiedException;
import com.mithunnirmal.merch.modelDtos.UserDto;
import com.mithunnirmal.merch.repositories.AddressRepository;
import com.mithunnirmal.merch.repositories.UserRepository;
import com.mithunnirmal.merch.repositories.VerificationTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mithunnirmal.merch.utils.UserUtils.applicationUrl;
import static com.mithunnirmal.merch.utils.UserUtils.notifyUserNotEnabled;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationService authService;

//    @Transactional
    public AuthenticationResponse registerUser (UserDto userDto , HttpServletRequest request) throws Exception, UserNotVerifiedException {
//        List<Address> addresss = userDto.getAddressDtos().stream().map(addressDto -> modelMapper.map(addressDto, Address.class))
//                                                                                              .collect(Collectors.toList());
        User user = modelMapper.map(userDto, User.class);
        user.setRole(UserRole.USER);
        Optional<User> found = userRepository.findByEmail(user.getEmail());
        if (found.isPresent())
            if(!found.get().isEnabled())
                notifyUserNotEnabled(found.get());
            else throw new UserEmailTakenException();
        else log.info("Indha email id pudhusu dhaa! prachana ila ");

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        //TODO: Address will not be received during registration in production
//        List<Address> addresses = user.getAddress();
//        user.setAddress(null);
        userRepository.save(user);
//        saveUserAddress(addresses, user);
        publisher.publishEvent(new RegistrationCompleteEvent(
                user,
                applicationUrl(request)));

        //Jwt processing goes here
        String jwt = jwtService.generateToken(user);
        authService.saveUserToken(user, jwt);
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .userName(user.getFirstName())
                .userId(user.getId())
                .build();
    }

    private void saveUserAddress(List<Address> addresses, User user) {
        for(Address ad : addresses)
            ad.setUsers(user);
        addressRepository.saveAll(addresses);
    }

    //Note: Verification token is different from jwt token
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken == null) {
            return "Invalid";
        }
        User user = verificationToken.getUser();
        if ((verificationToken.getExpirationTime().getTime()
                - Calendar.getInstance().getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken
                = verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    public String deleteUser(String userId) {
        userRepository.deleteById(userId);
        return userId;
    }

    public UserDto getUserDetails(String userId) {
        return modelMapper.map(userRepository.findById(userId).get(), UserDto.class );
    }
}
