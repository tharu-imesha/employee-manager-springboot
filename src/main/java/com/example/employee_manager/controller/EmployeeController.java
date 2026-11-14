package com.example.employee_manager.controller;

import com.example.employee_manager.model.Employee;
import com.example.employee_manager.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    
    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("employee", new Employee());
        return "employees"; 
    }

    
    @PostMapping
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model model) {
        
        if (employeeService.isEmailTaken(employee.getEmail(), employee.getId())) {
            result.rejectValue("email", "error.employee", "This email is already registered");
        }

        if (result.hasErrors()) {
            model.addAttribute("employees", employeeService.getAllEmployees());
            return "employees";
        }
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "edit-employee"; 
    }

    @PostMapping("/{id}/update")
    public String updateEmployee(@PathVariable("id") Long id, @Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model model) {
        
        if (employeeService.isEmailTaken(employee.getEmail(), id)) {
            result.rejectValue("email", "error.employee", "This email is already registered");
        }

        if (result.hasErrors()) {
            employee.setId(id);
            model.addAttribute("employees", employeeService.getAllEmployees());
            return "edit-employee";
        }
        employee.setId(id);
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }
   
    @GetMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }
}