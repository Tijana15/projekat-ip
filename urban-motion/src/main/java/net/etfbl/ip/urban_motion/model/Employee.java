package net.etfbl.ip.urban_motion.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor

public class Employee extends User {
    public Employee(Long id, String username, String password, String firstName, String lastName, Role role) {
        super(id, username, password, firstName, lastName, role);
        if (role == Role.CLIENT) {
            throw new IllegalArgumentException("Employee cannot be client");
        }
    }
    public Employee( String firstName, String lastName,String username, String password, Role role) {
        super(firstName,lastName,username,password,role);
        if (role == Role.CLIENT) {
            throw new IllegalArgumentException("Employee cannot be client");
        }
    }
}
