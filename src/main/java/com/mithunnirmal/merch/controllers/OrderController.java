package com.mithunnirmal.merch.controllers;

import com.mithunnirmal.merch.modelDtos.CartDto;
import com.mithunnirmal.merch.response.OrderPlacementResponse;
import com.mithunnirmal.merch.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class OrderController {

    @Autowired
    public OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<OrderPlacementResponse> placeOrder(@RequestBody List<CartDto> cartDtoList,
                                                             @RequestParam(required = true) String userId,
                                                             @RequestParam(required = true) Integer amount   ) {
        OrderPlacementResponse orderPlacementResponse = null;
        try {
            orderPlacementResponse = orderService.placeOrder(cartDtoList, userId, amount);
        }
        catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException();
        }

        return ResponseEntity.ok(orderPlacementResponse);
    }

 }
