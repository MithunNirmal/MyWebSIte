package com.mithunnirmal.merch.repositories;


import com.mithunnirmal.merch.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    void deleteAllByUserId(String userId);

    List<Cart> findAllByUserId(String userId);

    @Transactional
    void deleteInBulkByUserId(String userId);
}
