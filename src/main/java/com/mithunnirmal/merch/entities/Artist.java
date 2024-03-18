package com.mithunnirmal.merch.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Artist extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;

    String dp;

//    @OneToMany
//    Album album;
}
