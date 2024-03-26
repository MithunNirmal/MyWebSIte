package com.mithunnirmal.merch.controllers;

import com.mithunnirmal.merch.auth.AuthenticationRequest;
import com.mithunnirmal.merch.auth.AuthenticationResponse;
import com.mithunnirmal.merch.exception.UserNotFoundException;
import com.mithunnirmal.merch.exception.UserNotVerifiedException;
import com.mithunnirmal.merch.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;

    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate
            (@RequestBody AuthenticationRequest request) throws UserNotFoundException, UserNotVerifiedException {
        AuthenticationResponse response;
        try {
            response = authService.authenticate(request);
        }
        catch (UserNotFoundException e){throw new UserNotFoundException();}
        catch (UserNotVerifiedException e) {throw new UserNotVerifiedException(e.getMessage());}
        catch (BadCredentialsException e) {throw new BadCredentialsException(e.getMessage());}
        catch (Exception e){return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}
        return ResponseEntity.ok(response);
    }

}
