package com.ezentwix.teamcostco.controller.customer;

import java.util.stream.Collectors;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezentwix.teamcostco.dto.faq.FAQsCategoryDTO;
import com.ezentwix.teamcostco.dto.faq.FAQsDTO;
import com.ezentwix.teamcostco.service.FAQsService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FAQsController {
    private final FAQsService faqsService;

    @GetMapping("/customer/FAQs")
    public String showFAQsList(@RequestParam(value = "categoryId", required = false) Long categoryId, Model model) {
        faqsService.configureModel(model);
        List<FAQsDTO> faqList = faqsService.getAllFAQList();
        List<FAQsCategoryDTO> categories = faqsService.getAllCategoryList();
    
        if (categoryId != null) {
            faqList = faqList.stream()
                    .filter(faq -> faq.getId() != null && faq.getId().equals(categoryId))
                    .collect(Collectors.toList());
        }

        model.addAttribute("faqList", faqList);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategoryId", categoryId);
        return "index";
    }


}
