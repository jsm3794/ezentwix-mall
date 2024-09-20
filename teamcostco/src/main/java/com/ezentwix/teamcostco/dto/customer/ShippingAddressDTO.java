package com.ezentwix.teamcostco.dto.customer;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ShippingAddressDTO {
    Long address_id;
    Long customer_id;
    String recipient_name;
    String road_name_address;
    String lot_number_address;
    String detail_address;
    String postal_code;
    String phone_number;
    LocalDateTime create_date;
    LocalDateTime update_date;
}
