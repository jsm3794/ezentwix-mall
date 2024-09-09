package com.ezentwix.teamcostco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ezentwix.teamcostco.service.IntroService;
import com.ezentwix.teamcostco.service.IndexService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final IndexService indexService;
    private final IntroService introService;

    @GetMapping("/")
    public String showIndex(Model model) {
        indexService.setCategoryModel(model);
        introService.configureModel(model);
        return "index";
    }

}