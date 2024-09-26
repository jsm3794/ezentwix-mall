package com.ezentwix.teamcostco.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezentwix.teamcostco.dto.customer.OrderShippingAddressDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderShippingAddressRepository {
    private final SqlSessionTemplate sql;

    public boolean insertOrderShippingAddress(OrderShippingAddressDTO orderShippingAddressDTO){
        int row = sql.insert("OrderShippingAddress.insertOrderShippingAddress", orderShippingAddressDTO);
        return row > 0;
    }


}
