package com.ezentwix.teamcostco.controller.purchase;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ezentwix.teamcostco.service.PurchaseService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class PurchasePageController {
    private final PurchaseService purchaseService;

    @GetMapping("/purchase")
    public String getMethodName(Model model) {
        purchaseService.configureModel(model);
        return "index";
    }
    

}
