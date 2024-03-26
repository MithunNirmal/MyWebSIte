package com.mithunnirmal.merch.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mithunnirmal.merch.enums.ProductType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column
    String productId;

    String name;

    String imageLink;

    String price;

    @Enumerated(EnumType.STRING)
    ProductType productType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Cart)) {
            return false;
        }

        Cart item = (Cart) o;

        return this.productId.equals(item.productId);
    }
}
