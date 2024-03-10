package com.mithunnirmal.merch.event.listener;

import com.mithunnirmal.merch.entities.User;
import com.mithunnirmal.merch.event.RegistrationCompleteEvent;
import com.mithunnirmal.merch.services.EmailService;
import com.mithunnirmal.merch.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.mithunnirmal.merch.utils.Final.*;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements
        ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);

        String url = event.getApplicationUrl()
                        +VERIFICATION_URL //+ "/api/v1/verify/verifyRegistration?token="
                        + token;

        //sendVerificationEmail()
        log.info("Click the link to verify your account: {}",
                url);

        emailService.sendSimpleMessage(
                user.getEmail(),
                VERIFICATION_SUBJECT,
                VERIFICATION_TEXT + url);


    }
}
