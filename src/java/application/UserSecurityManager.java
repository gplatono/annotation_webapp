/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import beans.User;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author gplatono
 */
public class UserSecurityManager {
    private ArrayList<User> users;
    
    public UserSecurityManager() throws SQLException {
        this.users = JDBCHelper.getUsers();
    }
    
    public User login(String username, String password) {
        if (username != null && password != null) {
            for (User user : users) {
                if(user.getUsername().equals(username) && user.getPassword().equals(password))
                    return user;
            }
        }
        return null;
    }
}
