/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import beans.Scene;
import beans.TestInstance;
import beans.Testcase;
import beans.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Manages the persistence layer of the app
 * @author gplatono
 */
public class JDBCHelper {
        
    private final String JDBC_DRIVER = "org.postgresql.Driver";  
    private final String DB_URL = "jdbc:postgresql://localhost:5432/srp";
    private final String USER = "gplatono";
    private final String PASS = "";
    private static Connection dbConnection = null;
    
    /**
     * Standard constructor
     */
    public JDBCHelper() {
        try {
            Class.forName(JDBC_DRIVER);
            dbConnection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = dbConnection.createStatement();
            
        }
        catch(Exception ex) {
            String str = ex.getMessage();
        }                
    }
    
    /**
     * Retrieves all the testcases stored in the database
     * @return List of Testcase objects
     * @throws SQLException
     */
    public static ArrayList<Testcase> getAllTestcases() throws SQLException {
        ArrayList<Testcase> testcases = new ArrayList<>();
        Statement statement = dbConnection.createStatement();
        String query = "SELECT * FROM testcases;";
        ResultSet results = statement.executeQuery(query);
        while(results.next()) {
            Testcase testcase = new Testcase();
            testcase.setId(results.getInt("ID"));
            testcase.setQueryType(results.getInt("TYPE"));
            testcase.setSceneID(results.getInt("SCENE_ID"));
            testcase.setRelation(results.getString("RELATION"));
            testcase.setRelatum(results.getString("RELATUM"));
            testcase.setReferent1(results.getString("REFERENT1"));
            testcase.setReferent2(results.getString("REFERENT2"));
            testcase.setEnabled(results.getBoolean("ENABLED"));
            testcase.setQuery(results.getString("QUERY"));
            
            //REMOVE THE CONDITION LATER
            //if(testcase.getQueryType() == 2) {
            testcases.add(testcase);
            //}
        }
        results.close();
        statement.close();
        return testcases;
    }
    
    /**
     * Retrieves all the enabled (active) testcases of the specified type
     * @param type - the desired type of the testcases to be returned
     * 0 - truth-judgment, 1 - description task type
     * @return List of Testcase objects
     * @throws SQLException
     */
    public static ArrayList<Testcase> getEnabledTypedTestcases(int type) throws SQLException {
        ArrayList<Testcase> testcases = new ArrayList<>();
        Statement statement = dbConnection.createStatement();
        String query = "SELECT * FROM testcases WHERE enabled = true and type = " + type + ";";
        ResultSet results = statement.executeQuery(query);
        while(results.next()) {
            Testcase testcase = new Testcase();
            testcase.setId(results.getInt("ID"));
            testcase.setQueryType(results.getInt("TYPE"));
            testcase.setSceneID(results.getInt("SCENE_ID"));
            testcase.setRelation(results.getString("RELATION"));
            testcase.setRelatum(results.getString("RELATUM"));
            testcase.setReferent1(results.getString("REFERENT1"));
            testcase.setReferent2(results.getString("REFERENT2"));
            testcase.setEnabled(results.getBoolean("ENABLED"));
            testcase.setQuery(results.getString("QUERY"));
            
            //REMOVE THE CONDITION LATER
            //if(testcase.getQueryType() == 2) {            
            testcases.add(testcase);
        }
        results.close();
        statement.close();
        return testcases;
    }
    
    /**
     * Retrieves all the enabled (active) testcases from the database
     * @return List of Testcase objects
     * @throws SQLException
     */
    public static ArrayList<Testcase> getEnabledTestcases() throws SQLException {
        ArrayList<Testcase> testcases = new ArrayList<>();
        Statement statement = dbConnection.createStatement();
        String query = "SELECT * FROM testcases WHERE enabled = true;";
        ResultSet results = statement.executeQuery(query);
        while(results.next()) {
            Testcase testcase = new Testcase();
            testcase.setId(results.getInt("ID"));
            testcase.setQueryType(results.getInt("TYPE"));
            testcase.setSceneID(results.getInt("SCENE_ID"));
            testcase.setRelation(results.getString("RELATION"));
            testcase.setRelatum(results.getString("RELATUM"));
            testcase.setReferent1(results.getString("REFERENT1"));
            testcase.setReferent2(results.getString("REFERENT2"));
            testcase.setEnabled(results.getBoolean("ENABLED"));
            testcase.setQuery(results.getString("QUERY"));
            
            //REMOVE THE CONDITION LATER
            //if(testcase.getQueryType() == 2) {
            testcases.add(testcase);
            //}
        }
        results.close();
        statement.close();
        return testcases;
    }
    
    /**
     * Retrieves all the scene paths from the database
     * @return List of paths to the Scene files in the string format
     * @throws SQLException
     */
    public static ArrayList<String> getScenes1() throws SQLException {        
        ArrayList<String> paths = new ArrayList<String>();
        Statement statement = dbConnection.createStatement();
        String query = "SELECT * FROM SCENES";
        ResultSet results = statement.executeQuery(query);        
        while(results.next()) {
            paths.add(results.getString("path"));
        }
        results.close();
        statement.close();
        return paths;
    }
    
    /**
     * Retrieves all the scenes from the database
     * @return List of Scene objects
     * @throws SQLException
     */
    public static ArrayList<Scene> getScenes() throws SQLException {        
        ArrayList<Scene> scenes = new ArrayList<Scene>();
        Statement statement = dbConnection.createStatement();
        String query = "SELECT * FROM SCENES";
        ResultSet results = statement.executeQuery(query);        
        while(results.next()) {
            scenes.add(new Scene(Integer.parseInt(results.getString("id")), results.getString("path"), results.getString("name"), results.getString("task_type")));
            //paths.add(results.getString("path"));
        }
        results.close();
        statement.close();
        return scenes;
    }
    
    /**
     * Searches for and retrieves a specific scene by its integer id
     * @param id - the unique id of the scene
     * @return The Scene object (null if not found)
     * @throws SQLException
     */
    public static Scene getSceneById(int id) throws SQLException {
        Scene scene = null;
        Statement statement = dbConnection.createStatement();
        String query = "SELECT * FROM scenes WHERE id = " + id;
        ResultSet results = statement.executeQuery(query);
        if(results.next()) {
            scene = new Scene(id, results.getString("path"), results.getString("name"), results.getString("task_type"));
        }
        results.close();
        statement.close();
        return scene;
    }
    
    /**
     * Saves the TestInstance object into the database
     * @param testInstance - the object that stores the user annotation and associated 
     * data
     * @throws SQLException
     */
    public static void saveResponse(TestInstance testInstance) throws SQLException {
        Statement statement = dbConnection.createStatement();
        String query = "INSERT INTO responses(testcase, user_id, response) VALUES (";
        //query += (new Random().nextInt(1000000)) + ","; 
        query += testInstance.getTestcase().getId() + ",";
        query += testInstance.getUserID() + ",";
        query += "'" + testInstance.getResponse() + "'" + ");";
        statement.executeUpdate(query);
        statement.close();        
    }
    
    /**
     * Creates a database dump
     * @param filename - the path for the dump file
     * @throws SQLException
     * @throws FileNotFoundException
     */
    public static void dump_responses(String filename) throws SQLException, FileNotFoundException {
                
        Statement statement = dbConnection.createStatement();
        String query = "SELECT * FROM responses;";
        ResultSet results = null;
        PrintWriter writer = new PrintWriter(filename);
        results = statement.executeQuery(query);
            while(results.next()) {
                ResultSet testcase_db = dbConnection.createStatement().executeQuery("SELECT * FROM testcases WHERE id = " + results.getInt("testcase") + ";");
                if(testcase_db.next()) {
                    ResultSet scene = dbConnection.createStatement().executeQuery("SELECT * FROM scenes WHERE id = " + testcase_db.getInt("scene_id") + ";");                    
                        writer.print(results.getInt("id") + ":");
                        writer.print(testcase_db.getInt("id") + ":");
                        writer.print(results.getInt("user_id") + ":");
                        writer.print(testcase_db.getInt("scene_id") + ":");
                        if (scene.next()) {
                            writer.print(scene.getString("name") + ":");
                        }
                        writer.print(testcase_db.getString("relation") + ":");
                        writer.print(testcase_db.getString("relatum") + ":");
                        writer.print(testcase_db.getString("referent1") + ":");
                        writer.print(testcase_db.getString("referent2") + ":");
                        writer.print(testcase_db.getInt("type") + ":");
                        //String str = results.getString("response");
                        writer.println(results.getString("response") + "###");
                }
                testcase_db.close();            
            }
            
        writer.close();
        results.close();
        statement.close();
    }
    
    /**
     * Checks if the specified scene exists in the database
     * @param name - the name of the scene to look for
     * @return boolean value
     * @throws SQLException
     */
    public static boolean sceneExist(String name) throws SQLException {
        Statement statement = dbConnection.createStatement();
        String query = "SELECT * FROM scenes WHERE name = '" + name + "'";
        ResultSet results = statement.executeQuery(query);        
        boolean ret_val = results.next();
        statement.close();
        results.close();
        return ret_val;
    }
    
    /**
     * Checks if the specified testcase exists in the database
     * @param signature - signature of the testcase is a string containing testcase
     * data, such as the relation and its arguments
     * @return boolean value
     * @throws SQLException
     */
    public static boolean testExist(String signature) throws SQLException {
        Statement statement = dbConnection.createStatement();
        String query = "SELECT * FROM testcases WHERE signature = '" + signature + "'";
        ResultSet results = statement.executeQuery(query);        
        boolean ret_val = results.next();
        statement.close();
        results.close();
        return ret_val;
    }
    
    /**
     * Add the scene to the database
     * @param scenes - List of Scene objects to be added
     * @throws SQLException
     */
    public static void add_scenes(ArrayList<Scene> scenes) throws SQLException {
        Statement statement = dbConnection.createStatement();
        String query = "INSERT INTO scenes(name, path, task_type) VALUES ";
        String value_list = "";
        for (Scene scene : scenes) {
            if (!sceneExist(scene.getName()))
                value_list += "(" + "'" + scene.getName() + "'" + "," + "'" + scene.getPath() + "'" + "," + "'" + scene.getTaskType() + "'" + "),";
        }
        if(!value_list.equals("")) {
            query += value_list;
            query = query.substring(0, query.length()-1) + ";";
            statement.executeUpdate(query);
        }        
        statement.close();
    }
    
    /**
     * Adds the testcases to the database
     * @param testcases - the list of TestCase objects to be added
     * @throws SQLException
     */
    public static void add_testcases(ArrayList<Testcase> testcases) throws SQLException {
        Statement statement = dbConnection.createStatement();
        String query = "INSERT INTO testcases(type, scene_id, relation, relatum, referent1, referent2, enabled, query, signature) VALUES";
        String value_list = "";
        for (Testcase testcase : testcases) {            
            if(!testExist(testcase.getSignature())) {
                value_list += "(" + testcase.getId() + ",";
                value_list += testcase.getSceneID() + ",";
                value_list += "'" + testcase.getRelation() + "',";
                value_list += "'" + testcase.getRelatum() + "',";
                value_list += "'" + testcase.getReferent1()+ "',";
                value_list += "'" + testcase.getReferent2() + "',";
                value_list += testcase.isEnabled() + ",";
                value_list += "'" + testcase.getQuery() + "',";
                value_list += "'" + testcase.getSignature() + "'),";
            }
        }
        if(!value_list.equals("")) {
            query += value_list;
            query = query.substring(0, query.length()-1) + ";";
            statement.executeUpdate(query);
        }        
        statement.close();
    }
    
    /**
     * Retrieves all the users from the database
     * @return List of User objects
     * @throws SQLException
     */
    public static ArrayList<User> getUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<User>();
        Statement statement = dbConnection.createStatement();
        String query = "SELECT * FROM USERS;";
        ResultSet results = statement.executeQuery(query);
        while(results.next()) {
            users.add(new User(results.getInt("id"), results.getString("username"), results.getString("password"), results.getInt("role")));
        }
        results.close();
        statement.close();
        return users;
    }
    
    /**
     * Retrieves the list of unanswered testcases for the specific user
     * @param user - user id
     * @return the list of testcases the user has not annotated yet
     * @throws SQLException
     */
    public static ArrayList<Testcase> getUnansweredForUser(User user) throws SQLException {
        ArrayList<Testcase> testcases = new ArrayList<Testcase>();
        Statement statement = dbConnection.createStatement();
        String query = "SELECT * FROM TESTCASES WHERE enabled = true and id NOT IN (SELECT testcase FROM RESPONSES WHERE USER_ID = " + user.getId() + ");";
        ResultSet results = statement.executeQuery(query);
        while(results.next()) {
            Testcase testcase = new Testcase();
            testcase.setId(results.getInt("ID"));
            testcase.setQueryType(results.getInt("TYPE"));
            testcase.setSceneID(results.getInt("SCENE_ID"));
            testcase.setRelation(results.getString("RELATION"));
            testcase.setRelatum(results.getString("RELATUM"));
            testcase.setReferent1(results.getString("REFERENT1"));
            testcase.setReferent2(results.getString("REFERENT2"));
            testcase.setEnabled(results.getBoolean("ENABLED"));
            testcase.setQuery(results.getString("QUERY"));            
            testcases.add(testcase);        
        }
        results.close();
        statement.close();
        return testcases;       
    }
    
    /**
     * Retrieves the list of unanswered testcases of a specific type for the 
     * specific user     * 
     * @param user - user id
     * @param type - task type of the testcase
     * @return - the list of testcases of the required type the user has not 
     * annotated yet
     * @throws SQLException
     */
    public static ArrayList<Testcase> getUnansweredForUser(User user, int type) throws SQLException {
        ArrayList<Testcase> testcases = new ArrayList<Testcase>();
        Statement statement = dbConnection.createStatement();
        String query = "SELECT * FROM TESTCASES WHERE enabled = true and type = " + type + " and id NOT IN (SELECT testcase FROM RESPONSES WHERE USER_ID = " + user.getId() +");";
        ResultSet results = statement.executeQuery(query);
        while(results.next()) {
            Testcase testcase = new Testcase();
            testcase.setId(results.getInt("ID"));
            testcase.setQueryType(results.getInt("TYPE"));
            testcase.setSceneID(results.getInt("SCENE_ID"));
            testcase.setRelation(results.getString("RELATION"));
            testcase.setRelatum(results.getString("RELATUM"));
            testcase.setReferent1(results.getString("REFERENT1"));
            testcase.setReferent2(results.getString("REFERENT2"));
            testcase.setEnabled(results.getBoolean("ENABLED"));
            testcase.setQuery(results.getString("QUERY"));            
            testcases.add(testcase);        
        }
        results.close();
        statement.close();
        return testcases;       
    }    
    
    /**
     * Creates a new database record for a user
     * @param user - User object containing all the user data
     * @throws SQLException
     */
    public static void AddUser(User user) throws SQLException {
        Statement statement = dbConnection.createStatement();
        String query = "INSERT INTO users(username, password, role) VALUES (";
        query += "'" + user.getUsername() + "',";
        query += "'" + user.getPassword() + "',";
        query += user.getRole();
        query += ");";
        statement.executeUpdate(query);
        statement.close();
    }
    
    /**
     * Checks if the given username already exists in the database
     * @param username - username to check
     * @return boolean value
     * @throws SQLException
     */
    public static boolean CheckUsername(String username) throws SQLException {
        boolean ret_val;
        Statement statement = dbConnection.createStatement();
        String query = "SELECT * FROM USERS WHERE username = '" + username + "';";
        ret_val = statement.executeQuery(query).next();
        statement.close();
        return ret_val;
    }
}