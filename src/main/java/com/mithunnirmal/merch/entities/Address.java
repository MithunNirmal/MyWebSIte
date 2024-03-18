package com.mithunnirmal.merch.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue (strategy =  GenerationType.UUID)
    String id;

    String lineOne;
    String lineTwo;
    String city;

    @Length( max = 6, min = 6 )
    String pincode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName="id"
            ,nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_ADDRESS"))
    User users;

}
