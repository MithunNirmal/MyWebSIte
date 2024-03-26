package com.mithunnirmal.merch.controllers;

import com.mithunnirmal.merch.auth.AuthenticationRequest;
import com.mithunnirmal.merch.auth.AuthenticationResponse;
import com.mithunnirmal.merch.entities.User;
import com.mithunnirmal.merch.entities.verificationtoken.VerificationToken;
import com.mithunnirmal.merch.exception.UserNotVerifiedException;
import com.mithunnirmal.merch.modelDtos.UserDto;
import com.mithunnirmal.merch.services.AuthenticationService;
import com.mithunnirmal.merch.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.mithunnirmal.merch.utils.UserUtils.applicationUrl;
import static com.mithunnirmal.merch.utils.UserUtils.resendVerificationTokenMail;

@RestController
@RequestMapping(path = "/api/v1")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable String userId) {
        UserDto userDto = null;
        try {
            userDto = userService.getUserDetails(userId);
        }
        catch (Exception e) {return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}
        return ResponseEntity.ok(userDto);
    }

    @PostMapping(path = "/auth/register")
    public ResponseEntity<AuthenticationResponse> registerUser
            (@RequestBody @Valid UserDto userDto, final HttpServletRequest request) throws UserNotVerifiedException {
        AuthenticationResponse response = null;
        try {
             response = userService.registerUser(userDto,  request);
        }
        catch (UserNotVerifiedException e) {throw new UserNotVerifiedException(e.getMessage());}
        catch (Exception e) {throw new RuntimeException(e);}
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify/verifyRegistration")
    public ResponseEntity<String> verifyRegistration(@RequestParam String token) {
        String result = userService.validateVerificationToken(token);
        if(result.equalsIgnoreCase("valid")) {
            return ResponseEntity.ok("User verification successful");
        }else if(result.equalsIgnoreCase("expired"))
            return ResponseEntity.ok("Token expired " + token);
        return ResponseEntity.ok("Not a valid user!");
    }

    @GetMapping("/verify/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken,
                                          HttpServletRequest request) {
        VerificationToken verificationToken
                = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerificationTokenMail(user, applicationUrl(request), verificationToken);
        return "Verification Link Sent";
    }


    @DeleteMapping(path = "/user/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }
}
