package com.mithunnirmal.merch.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/pay")
public class PaymentController {

    @PostMapping("/makePayment")
    public ResponseEntity<String> makePayment() {

        //Dummy code needs to be executed

        return ResponseEntity.ok(null);
    }
}
