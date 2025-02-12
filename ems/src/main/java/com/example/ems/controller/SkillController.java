package com.example.ems.controller;

import com.example.ems.model.Employee;
import com.example.ems.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/employees/{employeeId}/skills")
public class SkillController {

    private final EmployeeService employeeService;

    public SkillController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    public Employee addSkillToEmployee(@PathVariable Long employeeId, @RequestBody Map<String, String> request) {
        String skillName = request.get("skillName");
        return employeeService.addSkillToEmployee(employeeId, skillName);
    }


    @DeleteMapping("/remove")
    public Employee removeSkillFromEmployee(@PathVariable Long employeeId, @RequestParam String skillName) {
        return employeeService.removeSkillFromEmployee(employeeId, skillName);
    }
}
