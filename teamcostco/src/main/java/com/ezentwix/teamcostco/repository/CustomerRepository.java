package com.ezentwix.teamcostco.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezentwix.teamcostco.dto.customer.CustomerDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomerRepository {
    private final SqlSessionTemplate sessionTemplate;

    public CustomerDTO getBySocialId(String social_id) {
        return sessionTemplate.selectOne("Customer.getBySocialId", social_id);
    }

    public Boolean insertCustomer(CustomerDTO customerDTO) {
        int row = sessionTemplate.insert("Customer.insertCustomer", customerDTO);
        if (row > 0) {
            return true;
        }
        return false;
    }

}
