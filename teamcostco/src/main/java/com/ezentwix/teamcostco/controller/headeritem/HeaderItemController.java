package com.ezentwix.teamcostco.controller.headeritem;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ezentwix.teamcostco.service.AboutService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HeaderItemController {

    private final AboutService aboutService;
    private final ContatUsService contactUsService;

    @GetMapping("/about-us")
    public String showAboutUs(Model model) {
        aboutService.configureModel(model);
        return "index";
    }

    

}
