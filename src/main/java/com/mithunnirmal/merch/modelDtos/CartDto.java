package com.mithunnirmal.merch.modelDtos;

import com.mithunnirmal.merch.entities.Cart;
import com.mithunnirmal.merch.entities.User;
import com.mithunnirmal.merch.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {
    String id;

    String productId;

    String name;

    String imageLink;

    String price;

    ProductType productType;

    String userId;
    //UserDto userDto;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof CartDto)) {
            return false;
        }

        CartDto item = (CartDto) o;

        return this.productId.equals(item.productId);
    }
}
