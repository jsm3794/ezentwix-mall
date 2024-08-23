package com.ezentwix.teamcostco.controller.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ezentwix.teamcostco.service.dashboard.DashBoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DashBoardController {
    private final DashBoardService dashBoardService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        dashBoardService.configureModel(model);
        return "index";
    }
}
