package com.ezentwix.teamcostco.dto.sales;

import java.util.List;

import com.ezentwix.teamcostco.dto.customer.OrderShippingAddressDTO;

import lombok.Data;

@Data
public class SalesRequestDTO {
    private OrderShippingAddressDTO shipping_address;
    private List<ItemDto> items;
    private String payment_method;

    @Data
    public static class ItemDto {
        private Long product_code;
        private Integer qty;
    }
}
