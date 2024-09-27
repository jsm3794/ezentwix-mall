package com.ezentwix.teamcostco.controller.cart;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ezentwix.teamcostco.dto.cart.CartDTO;
import com.ezentwix.teamcostco.service.CartService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CartPageController {
    private final CartService cartService;

    @GetMapping("/customer/cart")
    public String getMethodName(Model model) {
        cartService.configureModel(model);
        List<CartDTO> items = cartService.getAll();
        model.addAttribute("items", items);
        return "index";
    }

}
