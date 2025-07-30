package net.etfbl.ip.urban_motion.service;

import net.etfbl.ip.urban_motion.dto.LoginDTO;
import net.etfbl.ip.urban_motion.model.Employee;
import net.etfbl.ip.urban_motion.model.Role;
import net.etfbl.ip.urban_motion.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final EmployeeRepository employeeRepository;

    public AuthService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee login(LoginDTO loginDTO) {
        Employee employee = employeeRepository.findByUsername(loginDTO.getUsername()).orElse(null);
        if (employee == null || employee.getRole().equals(Role.CLIENT)) {
            return null;
        }
        if (!employee.getPassword().equals(loginDTO.getPassword())) {
            return null;
        }
        return employee;
    }
}
