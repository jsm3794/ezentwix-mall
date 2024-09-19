package com.ezentwix.teamcostco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ezentwix.teamcostco.dto.cart.CartDTO;
import com.ezentwix.teamcostco.service.CartService;

import lombok.RequiredArgsConstructor;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/cart")
    public String getMethodName(Model model) {
        cartService.configureModel(model);
        List<CartDTO> items = cartService.getAll();
        model.addAttribute("items", items);
        return "index";
    }

}
