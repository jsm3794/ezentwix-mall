package com.ezentwix.teamcostco.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezentwix.teamcostco.dto.product.ProductBrandGroupDTO;
import com.ezentwix.teamcostco.service.ProductService;
import com.ezentwix.teamcostco.service.SearchListService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SearchListController {
    private final SearchListService searchListService;
    private final ProductService productService;

    @GetMapping("/search")
    public String showSearchListPage(Model model,
            @RequestParam(value = "query", defaultValue = "", required = false) String query) {
        searchListService.setTitle("검색결과>" + query);
        searchListService.configureModel(model);
        List<ProductBrandGroupDTO> brandGroupList = productService.getBrandGroup(query);
        model.addAttribute("brandgroup", brandGroupList);
        return "index";
    }

}
