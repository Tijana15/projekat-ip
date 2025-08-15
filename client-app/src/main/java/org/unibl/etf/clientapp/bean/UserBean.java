package org.unibl.etf.clientapp.bean;

import org.unibl.etf.clientapp.dao.UserDAO;
import org.unibl.etf.clientapp.dto.User;

import java.io.Serializable;

public class UserBean implements Serializable {
    private User user = new User();
    private boolean isLoggedIn = false;

    public boolean login(String username, String password) {
        User userDTO = UserDAO.findByUsernameAndPassword(username, password);

        if (userDTO != null && !userDTO.isBlocked()) {
            
            this.user = userDTO;
            this.isLoggedIn = true;
            return true;
        }
        return false;
    }

    public boolean register(User user) {
        return UserDAO.addNewClient(user);
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        if (this.user == null) {
            return false;
        }
        return UserDAO.updatePassword(this.user.getId(), oldPassword, newPassword);
    }

    public boolean deactivateAccount() {
        if (this.user == null) {
            return false;
        }
        return UserDAO.deactivate(this.user.getId());
    }

    public Long getId() {
        return user.getId();
    }
}
