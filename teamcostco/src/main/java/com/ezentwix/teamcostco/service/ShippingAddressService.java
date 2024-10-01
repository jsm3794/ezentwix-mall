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

    public ShippingAddressDTO getDefaultAddressBySocialId() {
        String socialId = customerService.getSocialIdFromSession();
        return shippingAddressRepository.getDefaultAddressBySocialId(socialId);
    }

    public List<ShippingAddressDTO> getBySocialId() {
        return shippingAddressRepository.getBySocialId(customerService.getSocialIdFromSession());
    }
    

    public boolean addShippingAddress(ShippingAddressDTO shippingAddressDTO) {
        String socialId = customerService.getSocialIdFromSession();
        shippingAddressDTO.setSocial_id(socialId);

        try {
            shippingAddressRepository.addShippingAddress(shippingAddressDTO);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteAddress(ShippingAddressDTO shippingAddressDTO) {
        shippingAddressRepository.deleteAddress(shippingAddressDTO);
    }

    public void updateDefaultDestination(ShippingAddressDTO shippingAddressDTO) {
        shippingAddressRepository.updateDefaultDestination(shippingAddressDTO);
    }

    @Override
    public String getUri() {
        return "customer/shipping_address";
    }

    @Override
    public List<String> getCssFiles() {
        return List.of("/css/contents/shipping_address.css");
    }

    @Override
    public List<String> getJsFiles() {
        return List.of("/js/contents/shipping_address.js");
    }

    @Override
    public String getPageTitle() {
        return "배송지 관리";
    }
}
