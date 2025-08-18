package net.etfbl.ip.urban_motion.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Inheritance(
        strategy = InheritanceType.JOINED
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(Long id, String username, String password, String firstName, String lastName, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstName;
        this.lastname = lastName;
        this.role = role;
    }

    public User(String firstName, String lastName, String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.firstname = firstName;
        this.lastname = lastName;
        this.role = role;
    }

}
