/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import application.JDBCHelper;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author gplatono
 */
public class Configurator implements ServletContextListener {

    /**
     * Operations to be performed when the application starts
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        JDBCHelper helper = new JDBCHelper();
    }

    /**
     * Operations to be performed when the application terminates
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
