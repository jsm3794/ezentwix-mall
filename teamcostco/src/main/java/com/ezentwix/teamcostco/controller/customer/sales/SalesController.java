package com.ezentwix.teamcostco.controller.customer.sales;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ezentwix.teamcostco.dto.sales.SalesDTO;
import com.ezentwix.teamcostco.service.SalesService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class SalesController {
    private final SalesService salesService;

    @GetMapping("/customer/saleslist")
    public String getMethodName(Model model) {
        salesService.configureModel(model);
        List<SalesDTO> items = salesService.getSalesBySocialId();
        model.addAttribute("items", items);
        return "index";
    }
    
}
