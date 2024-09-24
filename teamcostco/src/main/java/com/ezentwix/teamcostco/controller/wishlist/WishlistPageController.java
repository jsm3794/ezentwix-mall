package com.ezentwix.teamcostco.controller.wishlist;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WishlistPageController {

    @GetMapping("/wishlist")
    public String wishlistpage() {

        return "index";
    }

}   
