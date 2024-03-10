package com.mithunnirmal.merch.repositories;

import com.mithunnirmal.merch.entities.verificationtoken.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {
    VerificationToken findByToken(String token);

    List<VerificationToken> findAllByUserId(String id);
}
