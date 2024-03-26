package com.mithunnirmal.merch.modelDtos;

import com.mithunnirmal.merch.enums.ProductType;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class ProductDto {
    String id;
    String productName;
    Integer price;

    String variantName;
    List<String> variants = new ArrayList<>();

    List<String> imageLinks = new ArrayList<>();
    ProductType productType = ProductType.DELIVERABLE;
}
