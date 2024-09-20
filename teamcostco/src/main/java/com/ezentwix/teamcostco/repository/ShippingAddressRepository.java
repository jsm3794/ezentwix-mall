package com.ezentwix.teamcostco.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezentwix.teamcostco.dto.customer.ShippingAddressDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShippingAddressRepository {
    private final SqlSessionTemplate sql;

    public ShippingAddressDTO getDefaultAddressByCustomerId(String customerId){
        return sql.selectOne("ShippingAddresses.getDefaultAddressByCustomerId", customerId);
    }

    public List<ShippingAddressDTO> getByCustomerId(String customerId){
        return sql.selectList("ShippingAddresses.getByCustomerId", customerId);
    }
    
}
