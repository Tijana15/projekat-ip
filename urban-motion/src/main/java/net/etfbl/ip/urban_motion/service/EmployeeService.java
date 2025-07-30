package net.etfbl.ip.urban_motion.service;

import net.etfbl.ip.urban_motion.dto.AddEmployeeDTO;
import net.etfbl.ip.urban_motion.model.Employee;
import net.etfbl.ip.urban_motion.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return this.employeeRepository.findById(id).orElse(null);
    }

    public Employee addEmployee(AddEmployeeDTO addEmployeeDTO) {
        if (employeeRepository.existsByUsername(addEmployeeDTO.getUsername())) {
            return null;
        }
        return employeeRepository.save(new Employee(
                addEmployeeDTO.getFirstName(), addEmployeeDTO.getLastName(),
                addEmployeeDTO.getUsername(), addEmployeeDTO.getPassword(), addEmployeeDTO.getRole()
        ));
    }

    public boolean deleteEmployeeById(Long id) {
        Employee employee = this.employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            this.employeeRepository.delete(employee);
        }
        return employee != null;
    }

}
