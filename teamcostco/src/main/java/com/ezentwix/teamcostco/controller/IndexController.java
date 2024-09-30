package com.ezentwix.teamcostco.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ezentwix.teamcostco.dto.product.ProductDTO;
import com.ezentwix.teamcostco.dto.product.RecommendProductDTO;
import com.ezentwix.teamcostco.service.IndexService;
import com.ezentwix.teamcostco.service.IntroService;
import com.ezentwix.teamcostco.service.ProductService;

import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class IndexController {
    private final IndexService indexService;
    private final IntroService introService;
    private final ProductService productService;

    @GetMapping("/")
    public String showIndex(Model model) {
        //indexService.setCategoryModel(model);
        introService.configureModel(model);
        // 추천상품
        List<RecommendProductDTO> productList = productService.getRecommendProducts();
        model.addAttribute("recommend_products", productList);
        return "index";
    }

}