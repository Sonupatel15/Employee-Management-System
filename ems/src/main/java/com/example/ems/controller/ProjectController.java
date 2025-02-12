package com.example.ems.controller;

import com.example.ems.model.Employee;
import com.example.ems.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // Assign Employee to Project
    @PostMapping("/{projectId}/assign/{employeeId}")
    public Employee assignEmployeeToProject(@PathVariable Long projectId, @PathVariable Long employeeId) {
        return projectService.assignEmployeeToProject(employeeId, projectId);
    }

    // Remove Employee from Project
    @DeleteMapping("/{projectId}/remove/{employeeId}")
    public Employee removeEmployeeFromProject(@PathVariable Long projectId, @PathVariable Long employeeId) {
        return projectService.removeEmployeeFromProject(employeeId, projectId);
    }

    // Get Employees Assigned to Project
    @GetMapping("/{projectId}/employees")
    public Set<Employee> getEmployeesByProject(@PathVariable Long projectId) {
        return projectService.getEmployeesByProject(projectId);
    }
}
