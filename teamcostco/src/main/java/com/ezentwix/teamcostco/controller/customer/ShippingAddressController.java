package com.ezentwix.teamcostco.controller.customer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ezentwix.teamcostco.dto.customer.ShippingAddressDTO;
import com.ezentwix.teamcostco.service.ShippingAddressService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ShippingAddressController {
    private final ShippingAddressService shippingAddressService;

    
    @GetMapping("/customer/shipping_address")
    public String showShippingAddress(Model model) {
        shippingAddressService.configureModel(model);


        List<ShippingAddressDTO> shippingAddresses = shippingAddressService.getBySocialId();
        ShippingAddressDTO defaultShippingAddress = shippingAddressService.getDefaultAddressBySocialId();
        
        // 모델에 데이터 추가
        model.addAttribute("shippingAddresses", shippingAddresses);
        model.addAttribute("defaultShippingAddress", defaultShippingAddress);
        return "index";
    }
}
