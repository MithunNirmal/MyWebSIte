package com.mithunnirmal.merch.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mithunnirmal.merch.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String Id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id"//, nullable = false,
           // foreignKey = @ForeignKey(name = "FK_USER_ORDER")
    )
    User user;

//    @Column
//    String userId;

    @Column
    Integer orderAmount;

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @Column
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    List<OrderContent> orderContents;

}
