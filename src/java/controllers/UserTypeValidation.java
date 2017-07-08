package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserTypeValidation extends HttpServlet{
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    boolean flag=false;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session=request.getSession();
        
        try (PrintWriter out = response.getWriter()) {
            flag=false;
            //old code
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            //con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","scott","tiger");
            // new code
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library","scott","tiger");
            
            String uname=request.getParameter("usr");
            session.setAttribute("name",uname);
            String upass=request.getParameter("pswd");
            String utype=request.getParameter("sel");
            
            if(utype.equals("Faculty")){
                flag=true;  
                ps=con.prepareStatement("select * from olfacreg where usertype='"+utype+"'");
                rs=ps.executeQuery();
          
                while(rs.next()){
                    if(uname.equals(rs.getString(1))&& upass.equals(rs.getString(4))&& utype.equals("Faculty")){
                        flag=false;
                        request.getRequestDispatcher("/Faculty.jsp").forward(request, response);           
                        break;
                    } 
                }
            }
            if(flag==true){
                flag=false;
                request.getRequestDispatcher("/Failed.jsp").forward(request, response);
            }
          
            if(utype.equals("Admin")){
                flag=true;
                ps=con.prepareStatement("select * from oladmreg where usertype='"+utype+"'");
                rs=ps.executeQuery();
      
                while(rs.next()){
                    if(uname.equals(rs.getString(1))&& upass.equals(rs.getString(3))&& utype.equals("Admin")){
                        flag=false;
                        request.getRequestDispatcher("/admin.jsp").forward(request, response);
                        break;
                    }
                }  
            }
            if(flag==true){
                flag=false;
                request.getRequestDispatcher("/Failed.jsp").forward(request, response);   
            }
           
            if(utype.equals("Student")){
                flag=true;
                ps=con.prepareStatement("select * from students");
                rs=ps.executeQuery();

                while(rs.next()){
                    String one = rs.getString(1);
                    String six = rs.getString(6);
                    if(uname.equals(one)&& upass.equals(six)){ 
                        flag=false;
                        request.getRequestDispatcher("/Student.jsp").forward(request, response);
                        break;
                    }
                }  
            }
            if(flag==true){
                flag=false;
                request.getRequestDispatcher("/Failed.jsp").forward(request, response);
            }
          }
        catch(Exception e){
            System.out.println(e);
        }
    }
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}