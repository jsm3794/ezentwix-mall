package com.ezentwix.teamcostco.controller.wishlist;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ezentwix.teamcostco.dto.WishlistsDTO;
import com.ezentwix.teamcostco.service.AuthService;
import com.ezentwix.teamcostco.service.WishlistService;

import java.util.List;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WishlistPageController {
    private final WishlistService wishlistService;
    private final AuthService loginService;

    @GetMapping("/customer/wishlist")
    public String wishlistpage(Model model) {
        wishlistService.configureModel(model);
        List<WishlistsDTO> items = wishlistService.getWiList(loginService.getUserIdFromSession());
        model.addAttribute("wishlist", items);
        return "index";
    }

}
