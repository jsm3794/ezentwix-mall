package com.ezentwix.teamcostco.dto.customer;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ShippingAddressDTO {
    private Long address_id;
    private String social_id;
    private String recipient_name;
    private String road_name_address;
    private String lot_number_address;
    private String detail_address;
    private String postal_code;
    private String phone_number;
    private LocalDateTime create_date;
    private LocalDateTime update_date;
    private String alias;
}
