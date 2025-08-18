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
    private boolean blocked;
    private String email;
    private String phone;
    private String id_document;
    private String avatarPicture;

    public User(String firstname, String lastname, String username, String password, String role, boolean blocked, String email, String phone, String id_document) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.role = role;
        this.blocked = blocked;
        this.email = email;
        this.phone = phone;
        this.id_document = id_document;
    }
}
