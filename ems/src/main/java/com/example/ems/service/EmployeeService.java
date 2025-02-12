package com.example.ems.service;

import com.example.ems.dto.EmployeeDTO;
import com.example.ems.exception.EmployeeNotFoundException;
import com.example.ems.model.Employee;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.model.Manager;
import com.example.ems.repository.ManagerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ems.model.Skill;
import com.example.ems.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.List;


@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private final SkillRepository skillRepository;

    public EmployeeService(EmployeeRepository employeeRepository, SkillRepository skillRepository, ManagerRepository managerRepository) {
        this.employeeRepository = employeeRepository;
        this.skillRepository = skillRepository;
        this.managerRepository = managerRepository;
    }

    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }


    // Add Employee
    public Employee addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = Employee.builder()
                .name(employeeDTO.getName())
                .email(employeeDTO.getEmail())
                .role(employeeDTO.getRole())
                .department(employeeDTO.getDepartment())
                .build();
        log.info("Adding new employee: {}", employee);
        return employeeRepository.save(employee);
    }

    // Edit Employee
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Optional<Employee> existingEmployeeOptional = employeeRepository.findById(id);
        if (existingEmployeeOptional.isPresent()) {
            Employee existingEmployee = existingEmployeeOptional.get();
            existingEmployee.setName(updatedEmployee.getName());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            existingEmployee.setRole(updatedEmployee.getRole());
            existingEmployee.setDepartment(updatedEmployee.getDepartment());

            return employeeRepository.save(existingEmployee);
        } else {
            throw new RuntimeException("Employee not found with ID: " + id);
        }
    }

    // Delete Employee
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee with ID " + id + " not found");
        }
        log.info("Deleting employee with ID: {}", id);
        employeeRepository.deleteById(id);
    }

    // Get All Employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee addSkillToEmployee(Long employeeId, String skillName) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Check if skill exists, otherwise create it
        Skill skill = skillRepository.findByName(skillName)
                .orElseGet(() -> {
                    Skill newSkill = new Skill();
                    newSkill.setName(skillName);
                    return skillRepository.save(newSkill);
                });

        // Add skill to employee
        employee.getSkills().add(skill);
        return employeeRepository.save(employee);
    }

    public Employee removeSkillFromEmployee(Long employeeId, String skillName) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Skill skill = skillRepository.findByName(skillName)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        employee.getSkills().remove(skill);
        return employeeRepository.save(employee);
    }

    @Autowired
    private final ManagerRepository managerRepository;

    public Employee assignManagerToEmployee(Long employeeId, Long managerId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        employee.setManager(manager);
        return employeeRepository.save(employee);
    }
}
