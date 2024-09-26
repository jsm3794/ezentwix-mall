package com.ezentwix.teamcostco.service;

import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.dto.customer.OrderShippingAddressDTO;
import com.ezentwix.teamcostco.repository.OrderShippingAddressRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderShippingAddressService {
    private final OrderShippingAddressRepository orderShippingAddressRepository;

    public boolean insertOrderShippingAddress(OrderShippingAddressDTO orderShippingAddressDTO) {
        return orderShippingAddressRepository.insertOrderShippingAddress(orderShippingAddressDTO);
    }

}
