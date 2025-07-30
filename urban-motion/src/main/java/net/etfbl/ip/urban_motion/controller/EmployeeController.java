package net.etfbl.ip.urban_motion.controller;

import jakarta.websocket.server.PathParam;
import net.etfbl.ip.urban_motion.dto.AddEmployeeDTO;
import net.etfbl.ip.urban_motion.model.Employee;
import net.etfbl.ip.urban_motion.model.Role;
import net.etfbl.ip.urban_motion.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployee(@RequestParam Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody AddEmployeeDTO addEmployeeDTO) {
        if (addEmployeeDTO.getRole().equals(Role.CLIENT)) {
            return ResponseEntity.status(403).build();
        }
        Employee employee = employeeService.addEmployee(addEmployeeDTO);
        if (employee == null) {
            return ResponseEntity.status(409).build();
        }
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        boolean isDeleted = this.employeeService.deleteEmployeeById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody AddEmployeeDTO updateEmployeeDTO) {
        try {
            Employee updatedEmployee = employeeService.updateEmployeeById(id, updateEmployeeDTO);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } catch (RuntimeException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
