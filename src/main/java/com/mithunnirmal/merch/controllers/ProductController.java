package com.mithunnirmal.merch.controllers;

import com.mithunnirmal.merch.modelDtos.ProductDto;
import com.mithunnirmal.merch.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@Slf4j
public class ProductController {

    @Autowired
    public ProductService productService;

    @GetMapping(path = "/public/products")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> productsDtoList =  null;
        try{
            productsDtoList = productService.getProducts();
        }
        catch(Exception e) {
            log.info("Error occurred " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(productsDtoList, HttpStatus.OK);
    }

    @PostMapping(path = "/addProduct")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        ProductDto addedProductDto =  null;
        try {
            addedProductDto = productService.addProduct(productDto);
        }
        catch (Exception e) {
            log.info("Error occurred in addProduct method :" + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(addedProductDto, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<String> test ()  {
        return  ResponseEntity.ok("Test successfull");
    }

}
