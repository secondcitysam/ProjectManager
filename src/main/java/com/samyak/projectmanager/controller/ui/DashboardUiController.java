package com.samyak.projectmanager.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardUiController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard/dashboard-page";

    }
}
