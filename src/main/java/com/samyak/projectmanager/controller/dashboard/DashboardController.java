package com.samyak.projectmanager.controller.dashboard;

import com.samyak.projectmanager.dto.response.ApiResponse;
import com.samyak.projectmanager.service.dashboard.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ApiResponse<?> getSummary() {
        return ApiResponse.success(
                dashboardService.getDashboardSummary()
        );
    }
}
