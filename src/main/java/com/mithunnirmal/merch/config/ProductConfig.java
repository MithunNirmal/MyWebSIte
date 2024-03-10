package com.mithunnirmal.merch.config;

import com.mithunnirmal.merch.entities.Product;
import com.mithunnirmal.merch.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class ProductConfig {
//    @Bean
//    CommandLineRunner commandLineRunner(ProductRepository repository) {
//        return args -> {
//            Product shirt = new Product(
//              //      "sjhdfjdafckjsdfibwdsk",
//                    "Shirt",
//                    1500,
//                    "shirt link"
//
//            );
//            Product pant =  new Product(
//              //      "skjdadjkdasdjka",
//                    "Pant",
//                    1500,
//                    "pant link"
//            );
//
//            repository.saveAll(List.of(shirt, pant));
//        };
//    }
}
