package com.ezentwix.teamcostco.controller.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezentwix.teamcostco.dto.WishlistsDTO;
import com.ezentwix.teamcostco.dto.product.ProductDTO;
import com.ezentwix.teamcostco.service.ProductDetailService;
import com.ezentwix.teamcostco.service.ProductService;
import com.ezentwix.teamcostco.service.WishlistService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductDetailPageController {
    private final ProductDetailService productDetailService;
    private final ProductService productService;
    private final WishlistService wishlistService;

    @GetMapping("/product/product_detail")
    public String showDefective(@RequestParam(value = "product_code", required = true) Long product_code,
            Model model) {

        productDetailService.configureModel(model);
        ProductDTO productDTO = productService.getByProductCode(product_code);
        WishlistsDTO wishlistsDTO = new WishlistsDTO();
        wishlistsDTO.setProduct_code(product_code + "");
        Integer count = wishlistService.checkWishlist(wishlistsDTO);
        boolean isWishlisted = (count > 0);
        model.addAttribute("product", productDTO);
        model.addAttribute("isWishlisted", isWishlisted);
        return "index";
    }

}
