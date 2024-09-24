package com.ezentwix.teamcostco.controller.wishlist;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ezentwix.teamcostco.service.WishlistService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WishlistPageController {
    private final WishlistService wishlistService;

    @GetMapping("/wishlist")
    public String wishlistpage(Model model) {
        wishlistService.configureModel(model);
        return "index";
    }

}
