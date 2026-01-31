package com.samyak.projectmanager.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
public class ProjectUiController {

    @GetMapping
    public String projectsPage() {
        return "projects/project-page";
    }

    @GetMapping("/create")
    public String createProjectPage() {
        return "projects/project-create";
    }

    @GetMapping("/archived")
    public String archivedProjectsPage() {
        return "projects/project-archived";
    }
}
