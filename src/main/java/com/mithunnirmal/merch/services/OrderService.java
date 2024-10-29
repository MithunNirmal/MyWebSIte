package com.mithunnirmal.merch.services;

import com.mithunnirmal.merch.entities.Order;
import com.mithunnirmal.merch.entities.OrderContent;
import com.mithunnirmal.merch.entities.User;
import com.mithunnirmal.merch.enums.OrderStatus;
import com.mithunnirmal.merch.event.NewOrderPlacedEvent;
import com.mithunnirmal.merch.exception.UserNotFoundException;
import com.mithunnirmal.merch.modelDtos.CartDto;
import com.mithunnirmal.merch.repositories.OrderContentRepository;
import com.mithunnirmal.merch.repositories.OrderRepository;
import com.mithunnirmal.merch.repositories.UserRepository;
import com.mithunnirmal.merch.response.OrderPlacementResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderContentRepository orderContentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationEventPublisher publisher;

    public OrderPlacementResponse placeOrder(List<CartDto> cartDtoList, String userId, Integer amount) throws UserNotFoundException{

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Order order = Order.builder()
                            .user(user)
                            .status(OrderStatus.PLACED)
                            .orderAmount(amount)
                    .build();
        log.info("call save method");

        orderRepository.save(order);

        log.info("save method completed");

        List<OrderContent> orderContents = cartDtoList.stream().map(cartItem -> {
                            return OrderContent.builder()
                                    .productId(cartItem.getProductId())
                                    .productType(cartItem.getProductType())
                                    .order(order)
                                    .build();
                }).collect(Collectors.toList());

        orderContentRepository.saveAll(orderContents);


        order.setOrderContents(orderContents);

        publisher.publishEvent(new NewOrderPlacedEvent(order));

        return new OrderPlacementResponse(order.getId(), order.getOrderAmount().toString());
    }
}
