package com.mithunnirmal.merch.modelDtos;


import com.mithunnirmal.merch.entities.Address;
import com.mithunnirmal.merch.entities.User;
import com.mithunnirmal.merch.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String firstName;
    String middleName;
    String lastName;

    @Column(length = 60)
    String password;

    @Email
    String email;

    @Autowired
    List<AddressDto> addressDtos;

    @Enumerated(EnumType.ORDINAL)
    UserRole role;
//    String role;

    boolean enabled = false;
}
