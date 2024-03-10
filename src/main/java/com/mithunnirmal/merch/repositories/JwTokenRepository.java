package com.mithunnirmal.merch.repositories;


import com.mithunnirmal.merch.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwTokenRepository extends JpaRepository<Token, String> {

    Optional<Token> findByToken(String token);
}
