package com.mithunnirmal.merch.services;

import com.mithunnirmal.merch.entities.Cart;
import com.mithunnirmal.merch.entities.User;
import com.mithunnirmal.merch.modelDtos.CartDto;
import com.mithunnirmal.merch.modelDtos.UserDto;
import com.mithunnirmal.merch.repositories.CartRepository;
import com.mithunnirmal.merch.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<CartDto> syncUserCart(List<CartDto> cartDtoList, String userId) {

        List<Cart> cartList = cartRepository.findAllByUserId(userId);

        Set<String> productIdList = cartList.stream()
                                        .map(Cart::getProductId)
                                        .collect(Collectors.toSet());
        List<CartDto> newCartDtoList = cartDtoList.stream()
                                        .filter(cartDto -> !productIdList.contains(cartDto.getProductId())).toList();

        Optional<User> user = userRepository.findById(userId);
        List<Cart> cartListToSave = new ArrayList<>(newCartDtoList.stream().map(cartDto -> //modelMapper.map(cartDto, Cart.class))
                        Cart.builder()
                                .user(user.get())
                                .productId(cartDto.getProductId())
                                .productType(cartDto.getProductType())
                                .name(cartDto.getName())
                                .imageLink(cartDto.getImageLink())
                                .price(cartDto.getPrice())
                                .build())
                .toList());
        cartListToSave.addAll(cartList);

        cartRepository.deleteAll(cartList);
        cartRepository.saveAll(cartListToSave);
//        Optional<User> user = userRepository.findById(userId);
//        Set<Cart> cartSet = new HashSet<>(cartlist);
//
//        cartSet.addAll(cartDtoList.stream().map(cartDto ->
//                                                    Cart.builder()
//                                                            .user(user.get())
//                                                            .productId(cartDto.getProductId())
//                                                            .name(cartDto.getName())
//                                                            .imageLink(cartDto.getImageLink())
//                                                            .price(cartDto.getPrice())
//                                                            .productType(cartDto.getProductType())
//                                                            .build())
//                                                    .toList());
//        cartRepository.deleteByUserId(userId);
//        cartRepository.saveAll(cartSet);
//
        List<CartDto> cartListToReturn = new ArrayList<>(cartListToSave.stream()
                                            .map(cartSetItem ->
                                                CartDto.builder()
                                                        .userId(userId)
                                                        .productId(cartSetItem.getProductId())
                                                        .name(cartSetItem.getName())
                                                        .imageLink(cartSetItem.getImageLink())
                                                        .price(cartSetItem.getId())
                                                        .productType(cartSetItem.getProductType())
                                                        .build())
                                                .toList());
        return cartListToReturn;
    }

    public List<CartDto> updateUserCart(List<CartDto> cartDtoList, String userId) {
        List<Cart> cartList = cartRepository.findAllByUserId(userId);
        Optional<User> user = userRepository.findById(userId);


        List<Cart> cartListToSave = new ArrayList<>(cartDtoList.stream().map(cartDto -> //modelMapper.map(cartDto, Cart.class))
                        Cart.builder()
                                .user(user.get())
                                .productId(cartDto.getProductId())
                                .productType(cartDto.getProductType())
                                .name(cartDto.getName())
                                .imageLink(cartDto.getImageLink())
                                .price(cartDto.getPrice())
                                .build())
                .toList());

        cartRepository.deleteAll(cartList);
        cartRepository.saveAll(cartListToSave);
        return cartDtoList;
    }
}
