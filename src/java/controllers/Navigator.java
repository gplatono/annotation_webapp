/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import application.JDBCHelper;
import beans.Testcase;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import application.TestGenerator;
import beans.Scene;
import beans.TestInstance;
import beans.User;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import application.UserSecurityManager;

/**
 *
 * @author Георгий Платонов
 */
public class Navigator extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String page;
        
                  
            try {
                UserSecurityManager manager = new UserSecurityManager();
                String url = request.getServletPath();
                if(url.contains("Login")) {
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    User user = manager.login(username, password);
                    if (request.getSession().getAttribute("user") == null && user != null)
                        request.getSession().setAttribute("user", user);
                }
                
                
                if(request.getSession().getAttribute("user") != null || request.getParameter("page").equals("sign_up")) {

                page = request.getParameter("page");
                
                if (page == null) {
                    page = "index_1";
                }
                
                if(page.equals("DUMP")) {
                    new JDBCHelper().dump_responses("dump");                
                }
                
                if(request.getParameter("submit_response") != null) {
                    TestInstance testInstance = (TestInstance)request.getSession().getAttribute("testInstance");                

                    if(request.getParameter("description") != null)
                        testInstance.setResponse(request.getParameter("description"));
                    else
                        testInstance.setResponse(request.getParameter("submit_response").toUpperCase());
                    request.setAttribute("result", "Thank you. Your last response - \'" + testInstance.getResponse() + "\' - has been saved...");                
                    JDBCHelper.saveResponse(testInstance);
                    
                    page = "eval";
                }
                else {
                    request.setAttribute("result", null);
                }
                if(page.equals("eval")) {
                    URL classpath = Navigator.class.getClassLoader().getResource("controllers/Navigator.class");
                    String appPath = classpath.getPath().split("WEB-INF")[0];                  
                    TestInstance testInstance = TestGenerator.generate((User)request.getSession().getAttribute("user"));
                    testInstance.setUserID(1);
                    request.getSession().setAttribute("testInstance", testInstance);
                }            
                if(page.equals("SCNUPLD"))
                    page = "scn_upload";
                }
                else page = "login";
                request.getRequestDispatcher("/jsp/" + page + ".jsp").forward(request, response);
            }
            catch(Exception ex) {
                response.getWriter().println(ex.getMessage());
            }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
