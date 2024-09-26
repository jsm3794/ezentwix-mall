package com.ezentwix.teamcostco.dto.customer;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrderShippingAddressDTO {
    private Long address_id;
    private String social_id;
    private Long sales_id;
    private String recipient_name;
    private String road_name_address;
    private String lot_number_address;
    private String detail_address;
    private String postal_code;
    private String address_alias;
    private String recipient_phone_number;
    private String sender_name;
    private String sender_phone_number;
    private String invoice_number;
    private String shipping_status;
    private String shipping_request_message;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    
}
