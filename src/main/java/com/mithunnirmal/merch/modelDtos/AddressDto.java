package com.mithunnirmal.merch.modelDtos;

import com.mithunnirmal.merch.entities.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class AddressDto {

    String id;

    String lineOne;
    String lineTwo;
    String city;

    String pincode;
    String user_id;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id"
//            ,nullable = false,
//            foreignKey = @ForeignKey(name = "FK_USER_ADDRESS"))
//    User users;
}
