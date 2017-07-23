/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sunny
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.mail.PasswordAuthentication;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.RequestDispatcher;


public class ForgotServ extends HttpServlet {
    
    
    Connection con;
    ResultSet rs;
    PreparedStatement ps;
    String pass;
    String to;
    String utyp;
     

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
          to=request.getParameter("fgtemail");
          utyp=(String)request.getAttribute("ut");
        
          if(to==null) {
              to=(String)request.getAttribute("fml");
          }
        
          Class.forName("oracle.jdbc.driver.OracleDriver");
          con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","scott","tiger");
          if(utyp.equals("Faculty")){
            ps=con.prepareStatement("select * from olfacreg where email='"+to+"'");
          
            rs=ps.executeQuery();
            if(rs.next()==true) {
              if(to.equals(rs.getString(3))) {
               pass=rs.getString(4);
              }
            }
          } else {
            ps=con.prepareStatement("select * from olstdreg where email='"+to+"'");
            rs=ps.executeQuery();

            if(rs.next()==true) {
              if(to.equals(rs.getString(5))) {
                pass=rs.getString(6);
              }
            }
          }
        
      //Get the session object  
         if(to!=null){
         RequestDispatcher rd=request.getRequestDispatcher("/home.html");
         rd.forward(request, response); }
         
        }catch(Exception e)
    {
     PrintWriter out = response.getWriter();   
     out.println("<center><h3>Login Failed,Please Try Again!</h3></center>");
     RequestDispatcher rd=request.getRequestDispatcher("/home.html");
     rd.include(request, response);
     System.out.println(e);
    }
       

    }protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
            
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
          
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
          

