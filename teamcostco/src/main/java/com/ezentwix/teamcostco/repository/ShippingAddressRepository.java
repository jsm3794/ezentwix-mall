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

    public ShippingAddressDTO getDefaultAddressBySocialId(String socialId){
        return sql.selectOne("ShippingAddresses.getDefaultAddressBySocialId", socialId);
    }

    public List<ShippingAddressDTO> getBySocialId(String socialId){
        return sql.selectList("ShippingAddresses.getBySocialId", socialId);
    }
    
}
