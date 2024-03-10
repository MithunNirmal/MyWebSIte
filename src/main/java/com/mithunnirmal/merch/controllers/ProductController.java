package com.mithunnirmal.merch.controllers;

import com.mithunnirmal.merch.entities.Product;
import com.mithunnirmal.merch.modelDtos.ProductDto;
import com.mithunnirmal.merch.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin//(origins = "http://localhost:5173")
@RequestMapping ("/api/v1")
public class ProductController {

    public static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductService productService;

    @GetMapping(path = "/public/product")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> productsDtoList =  null;
        try{
            System.out.println("jghvcvoigcovchvuvj ghvg     6666666666666");
            productsDtoList = productService.getProducts();
        }
        catch(Exception e) {
            LOGGER.info("Error occurred " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(productsDtoList, HttpStatus.OK);
    }

    @PostMapping(path = "/public/addProduct")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        ProductDto addedProductDto =  null;
        try {
            addedProductDto = productService.addProduct(productDto);
        }
        catch (Exception e) {
            LOGGER.info("Error occurred in addProduct method :" + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(addedProductDto, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<String> test ()  {
        return  ResponseEntity.ok("Test successfull");
    }


}
