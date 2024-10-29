package com.mithunnirmal.merch.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacementResponse {
    String orderId;
    String amount;
}
