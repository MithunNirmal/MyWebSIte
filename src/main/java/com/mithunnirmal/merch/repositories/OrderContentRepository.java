package com.mithunnirmal.merch.repositories;

import com.mithunnirmal.merch.entities.OrderContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderContentRepository extends JpaRepository<OrderContent, String> {
}
