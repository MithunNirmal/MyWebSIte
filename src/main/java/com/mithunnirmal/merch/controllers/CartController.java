package com.mithunnirmal.merch.controllers;


import com.mithunnirmal.merch.entities.Cart;
import com.mithunnirmal.merch.modelDtos.CartDto;
import com.mithunnirmal.merch.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/cart/sync")
    public ResponseEntity<List<CartDto>> syncUserCart(@RequestBody List<CartDto> cartDtoList
                                               // , @PathVariable String userId
                                                , @RequestParam(required = true)  String userId) {
        List<CartDto> cartSetToReturn = null;
        try{
            cartSetToReturn = cartService.syncUserCart(cartDtoList, userId);
        }
        catch (Exception e) {return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}

        return ResponseEntity.ok(cartSetToReturn);
    }


    @PostMapping("/cart/add")
    public ResponseEntity<List<CartDto>> updateUserCart(@RequestBody List<CartDto> cartDtoList
                                                        // , @PathVariable String userId
                                                        , @RequestParam(required = true)  String userId) {
        List<CartDto> cartSetToReturn = null;
        try{
            cartSetToReturn = cartService.updateUserCart(cartDtoList, userId);
        }
        catch (Exception e) {return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}

        return ResponseEntity.ok(cartSetToReturn);
    }
}
