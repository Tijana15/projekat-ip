package org.unibl.etf.clientapp.bean;

import org.unibl.etf.clientapp.dao.UserDAO;
import org.unibl.etf.clientapp.dto.User;

import java.io.Serializable;

public class UserBean implements Serializable {
    private User user = new User();
    private boolean isLoggedIn = false;

    public boolean login(String username, String password) {
        if ((user = UserDAO.findByUsernameAndPassword(username, password)) != null) {
            isLoggedIn = true;
            return true;
        }
        return false;
    }

    public boolean register(User user) {
        return UserDAO.addNewClient(user);
    }
}
