package com.mithunnirmal.merch.services;

import com.mithunnirmal.merch.auth.AuthenticationRequest;
import com.mithunnirmal.merch.auth.AuthenticationResponse;
import com.mithunnirmal.merch.config.JwtService;
import com.mithunnirmal.merch.entities.Token;
import com.mithunnirmal.merch.entities.User;
import com.mithunnirmal.merch.entities.verificationtoken.VerificationToken;
import com.mithunnirmal.merch.enums.TokenType;
import com.mithunnirmal.merch.exception.UserNotFoundException;
import com.mithunnirmal.merch.exception.UserNotVerifiedException;
import com.mithunnirmal.merch.repositories.JwTokenRepository;
import com.mithunnirmal.merch.repositories.UserRepository;
import com.mithunnirmal.merch.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mithunnirmal.merch.utils.UserUtils.notifyUserNotEnabled;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private JwTokenRepository jwTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //Registration authentication is implemented in user controller

    public AuthenticationResponse authenticate(AuthenticationRequest request)
            throws UserNotFoundException, UserNotVerifiedException {

        User user = repository.findByEmail(request.getEmail())
                            .orElseThrow(() -> new UserNotFoundException("User not found"));
        if(!user.isEnabled()) {
            notifyUserNotEnabled(user);
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        }catch (BadCredentialsException e) {throw new BadCredentialsException(e.getMessage());}

        String jwt = jwtService.generateToken(user);
        saveUserToken(user, jwt);
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .userName(user.getFirstName())
                .userId(user.getId())
                .build();
    }

    public void saveUserToken(User user, String jwtToken) { //saves JWT token
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        jwTokenRepository.save(token);
    }
}
