package com.mithunnirmal.merch.services;

import com.mithunnirmal.merch.entities.Product;
import com.mithunnirmal.merch.enums.ProductType;
import com.mithunnirmal.merch.modelDtos.ProductDto;
import com.mithunnirmal.merch.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    public ProductRepository productRepository;

    @Autowired
    public ModelMapper modelMapper;

    public List<ProductDto> getProducts() {
        Optional<List<Product>> products = Optional.of(productRepository.findAll());
        List<ProductDto> productsDto = products.get().stream().
                    map(Product -> modelMapper.map(Product, ProductDto.class)).collect(Collectors.toList());
        productsDto.forEach(productDto -> productDto.setProductType(ProductType.DELIVERABLE));
        return productsDto;
    }

    public ProductDto addProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        productRepository.save(product);
        return modelMapper.map(product, ProductDto.class);
    }
}
