package com.ezentwix.teamcostco.controller.customer;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ezentwix.teamcostco.dto.customer.ShippingAddressDTO;
import com.ezentwix.teamcostco.service.CustomerService;
import com.ezentwix.teamcostco.service.ShippingAddressService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ShippingAddressController {
    private final ShippingAddressService shippingAddressService;
    private final CustomerService customerService;

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

    @PostMapping("/customer/shipping_address/add")
    public ResponseEntity<?> addShippingAddress(@RequestBody ShippingAddressDTO shippingAddressDTO) {
        boolean isSuccess = shippingAddressService.addShippingAddress(shippingAddressDTO);
        if (isSuccess) {
            return ResponseEntity.ok().body(Map.of("success", true, "message", "배송지가 추가되었습니다."));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "배송지 추가에 실패했습니다."));
        }
    }

    @GetMapping("/customer/address/delete/{address_id}/{social_id}")
    public String deleteAddress(@PathVariable Long address_id, @PathVariable String social_id) {

        ShippingAddressDTO dto = new ShippingAddressDTO();
        dto.setAddress_id(address_id);
        dto.setSocial_id(social_id);

        shippingAddressService.deleteAddress(dto);

        return "redirect:/customer/shipping_address";
    }

    @GetMapping("/customer/address/fix/{address_id}")
    public String defaultDeliveryDestination(@PathVariable Long address_id) {
        
        String social_id = customerService.getSocialIdFromSession();

        ShippingAddressDTO dto = new ShippingAddressDTO();
        dto.setAddress_id(address_id);
        dto.setSocial_id(social_id);

        shippingAddressService.updateDefaultDestination(dto);


        return "redirect:/customer/shipping_address";
    }

}
