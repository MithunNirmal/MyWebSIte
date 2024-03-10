package com.mithunnirmal.merch.utils;

import com.mithunnirmal.merch.entities.User;
import com.mithunnirmal.merch.entities.verificationtoken.VerificationToken;
import com.mithunnirmal.merch.exception.UserNotVerifiedException;
import com.mithunnirmal.merch.repositories.VerificationTokenRepository;
import com.mithunnirmal.merch.services.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.mithunnirmal.merch.utils.Final.*;

@Slf4j
public class UserUtils {
    /*
            In original reference code
            These methods were kept as private not static methods in user controller
    */

    @Autowired
    private static VerificationTokenRepository vtRepsitory;
    @Autowired
    private static EmailService emailService;

    //Used for creating the request url as string for a HttpServletRequest
    public static String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }

    public static void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url =
                applicationUrl
                        + VERIFICATION_URL
                        + verificationToken.getToken();

        //sendVerificationEmail()
        log.info("Click the link to verify your account: {}",
                url);
        emailService.sendSimpleMessage(
                user.getEmail(),
                VERIFICATION_SUBJECT,
                VERIFICATION_TEXT + url);
    }

    public static void notifyUserNotEnabled(User user) throws UserNotVerifiedException {
        List<VerificationToken> vts = vtRepsitory.findAllByUserId(user.getId());
        String oldVt = null;
        if(!vts.isEmpty())
            oldVt = vts.getLast().getToken();
        throw new UserNotVerifiedException(oldVt);
    }
}
