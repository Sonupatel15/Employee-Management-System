package com.example.ems.service;

import com.example.ems.model.Employee;
import com.example.ems.model.Project;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProjectService {

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;

    public ProjectService(EmployeeRepository employeeRepository, ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }

    // Assign Employee to Project
    @Transactional
    public Employee assignEmployeeToProject(Long employeeId, Long projectId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.addEmployee(employee);  // Maintain bidirectional relationship
        projectRepository.save(project);
        return employee;
    }

    // Remove Employee from Project
    @Transactional
    public Employee removeEmployeeFromProject(Long employeeId, Long projectId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.removeEmployee(employee); // Maintain bidirectional relationship
        projectRepository.save(project);
        return employee;
    }

    // Get Employees in a Project
    public Set<Employee> getEmployeesByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return project.getEmployees();
    }
}
