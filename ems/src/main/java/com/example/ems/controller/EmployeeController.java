package com.example.ems.controller;

import com.example.ems.dto.EmployeeDTO;
import com.example.ems.model.Employee;
import com.example.ems.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Add Employee
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
        log.info("Received request to add employee: {}", employeeDTO);
        return ResponseEntity.ok(employeeService.addEmployee(employeeDTO));
    }

    // Update Employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        Employee employee = employeeService.updateEmployee(id, updatedEmployee);
        return ResponseEntity.ok(employee);
    }

    // Delete Employee
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        log.info("Received request to delete employee ID: {}", id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    // Get All Employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PutMapping("/{id}/assign-manager/{managerId}")
    public ResponseEntity<Employee> assignManager(@PathVariable Long id, @PathVariable Long managerId) {
        return ResponseEntity.ok(employeeService.assignManagerToEmployee(id, managerId));
    }


}
