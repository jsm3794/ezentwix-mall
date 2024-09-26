package com.ezentwix.teamcostco.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.PageMetadataProvider;
import com.ezentwix.teamcostco.dto.customer.ShippingAddressDTO;
import com.ezentwix.teamcostco.repository.ShippingAddressRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShippingAddressService implements PageMetadataProvider {
    private final ShippingAddressRepository shippingAddressRepository;
    private final CustomerService customerService;

    public ShippingAddressDTO getDefaultAddressBySocialId(){
        return shippingAddressRepository.getDefaultAddressBySocialId(customerService.getSocialIdFromSession());
    }

    public List<ShippingAddressDTO> getBySocialId(){
        return shippingAddressRepository.getBySocialId(customerService.getSocialIdFromSession());
    }

    @Override
    public String getUri() {
        return "/customer/shipping_address";
    }

    
    @Override
    public List<String> getCssFiles() {
        return List.of("/css/contents/shipping_address.css");
    }
    
    @Override
    public String getPageTitle() {
        return "배송지 관리";
    }
}
