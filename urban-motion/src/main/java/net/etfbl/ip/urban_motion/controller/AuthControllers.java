package net.etfbl.ip.urban_motion.controller;

import net.etfbl.ip.urban_motion.dto.LoginDTO;
import net.etfbl.ip.urban_motion.model.Employee;
import net.etfbl.ip.urban_motion.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthControllers {
    private final AuthService authService;

    public AuthControllers(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<Employee> login(@RequestBody LoginDTO loginDTO) {
        Employee employee = authService.login(loginDTO);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employee);
    }
}
