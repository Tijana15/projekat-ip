package org.unibl.etf.promotionsapp.beans;

import org.unibl.etf.promotionsapp.dao.UserDAO;

import java.io.Serializable;

public class UserBean implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;

    private boolean loggedIn = false;
    private String errorMessage;
    private UserDAO userDAO;

    public UserBean() {
        this.userDAO = new UserDAO();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstName) {
        this.firstname = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastName) {
        this.lastname = lastName;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String login() {
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            errorMessage = "Username and password are required!";
            return "error";
        }

        if (userDAO.authenticate(username, password)) {
            loggedIn = true;
            errorMessage = null;
            return "success";
        } else {
            errorMessage = "Wrong login data.";
            return "error";
        }
    }

    public void logout() {
        loggedIn = false;
        username = null;
        password = null;
        errorMessage = null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
