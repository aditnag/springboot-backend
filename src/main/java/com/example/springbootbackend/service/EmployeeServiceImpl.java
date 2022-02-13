package com.example.springbootbackend.service;

import com.example.springbootbackend.exception.ResourceNotFoundException;
import com.example.springbootbackend.model.Employee;
import com.example.springbootbackend.repository.EmployeeRepository;
import com.example.springbootbackend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService
{
    private EmployeeRepository employeerepo;

    @Autowired
    public EmployeeServiceImpl(@Lazy EmployeeRepository employeerepo)
    {
        this.employeerepo = employeerepo;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return  employeerepo.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeerepo.findAll();
    }

    @Override
    public Employee getEmployeeById(long id) {
//        Optional<Employee> employee = employeerepo.findById(id);
//        if(employee.isPresent())
//            return employee.get();
//        else
//            throw new ResourceNotFoundException("Employee", "Id", id);

        return employeerepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Employee", "Id", id));
    }

    @Override
    public Employee updateEmployee(Employee employee, long id) {
        Employee existingEmployee = employeerepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "Id", id));
        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());
        employeerepo.save(existingEmployee);
        return existingEmployee;
    }

    @Override
    public void deleteEmployee(long id) {
        // if emp exist iin db
        employeerepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "Id", id));
        employeerepo.deleteById(id);
    }


}
