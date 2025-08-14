package org.unibl.etf.clientapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String role;
}
