package net.etfbl.ip.urban_motion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.etfbl.ip.urban_motion.model.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddEmployeeDTO {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private Role role;
}
