package com.mithunnirmal.merch.entities;

import com.mithunnirmal.merch.enums.ProductType;
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
public class OrderContent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String productId;

    @Enumerated(EnumType.ORDINAL)
    ProductType productType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_ORDER_CONTENT"))
    Order order;

}

