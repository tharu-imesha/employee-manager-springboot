package com.example.employee_manager.service;

import com.example.employee_manager.model.Employee;
import com.example.employee_manager.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        Optional<Employee> optional = employeeRepository.findById(id);
        return optional.orElse(null);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public boolean isEmailTaken(String email, Long id) {
        Employee existingEmployee = employeeRepository.findByEmail(email);
        if (existingEmployee == null) {
            return false;
        }
        if (id != null && existingEmployee.getId().equals(id)) {
            return false;
        }
        return true;
    }
}