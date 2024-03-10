package com.mithunnirmal.merch.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Product extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column
    String productName;
    @Column
    Integer price;

    @Column
    String variantName;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name= "variants", joinColumns = @JoinColumn(name = "product_id"))
    @Column
    List<String> variants = new ArrayList<>();


    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name= "image", joinColumns = @JoinColumn(name = "product_id"))
    @Column
    List<String> imageLinks = new ArrayList<>(); // TODO: change imageLinks as list;
}
