package com.mithunnirmal.merch.repositories;

import com.mithunnirmal.merch.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public interface AddressRepository extends JpaRepository<Address,String > {
}
