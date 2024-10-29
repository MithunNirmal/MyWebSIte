package com.mithunnirmal.merch.event;

import com.mithunnirmal.merch.entities.Order;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class NewOrderPlacedEvent extends ApplicationEvent {

    private final Order order;

    public NewOrderPlacedEvent(Order order ) {
        super(order);
        this.order = order;
    }
}
