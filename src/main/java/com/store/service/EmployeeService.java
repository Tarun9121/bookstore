package com.store.service;
import com.store.entity.Employee;
import com.store.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

     
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

     
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

     
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

     
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setName(employeeDetails.getName());
            employee.setPosition(employeeDetails.getPosition());
            employee.setSalary(employeeDetails.getSalary());
            return employeeRepository.save(employee);
        }).orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }

     
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
