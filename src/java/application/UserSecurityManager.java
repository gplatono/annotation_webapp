/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import beans.User;
import java.sql.SQLException;
import java.util.ArrayList;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Manages the security layer of the app, e.g., authentication and authorization
 * @author gplatono
 */
public class UserSecurityManager {
    private ArrayList<User> users;
    
    /**
     * Standard constructor
     * @throws SQLException
     */
    public UserSecurityManager() throws SQLException {
        this.users = JDBCHelper.getUsers();
    }
    
    /**
     * Performs the authentication
     * @param username
     * @param password
     * @return - the User object with all user-related data
     */
    public User login(String username, String password) {
        if (username != null && password != null) {
            for (User user : users) {
                if(user.getUsername().equals(username) && (user.getPassword().equals(password) || BCrypt.checkpw(password, user.getPassword())))
                    return user;
            }
        }
        return null;
    }
    
    /**
     * Adds a new user to the system
     * @param username
     * @param password
     * @return Success or error code in the string format
     * @throws SQLException
     */
    public String SignUp(String username, String password) throws SQLException {
        if (!JDBCHelper.CheckUsername(username)) {
            String salt = BCrypt.gensalt(12);
            String hashedPassword = BCrypt.hashpw(password, salt);
            User newUser = new User(username, hashedPassword, 1);
            JDBCHelper.AddUser(newUser);
            this.users = JDBCHelper.getUsers();
            return "OK";
        }
        else {
            return "USERNAME_EXISTS";            
        }
    }
}