/*
 * Todo : Add discription about this class
 */
package controllers;

/**
 *
 * @author sunny
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StdRegServ extends HttpServlet {
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    boolean flag = false;     // is already registered
    boolean valid = false;    // details entered valid or not
    boolean regis = false;    // registration status
    String branch, enroll, name, email, mob, password, fNumber, fFruit;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            flag=false;
            valid=false;
            /* TODO output your page here. You may use following sample code. */
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library","scott","tiger");

            branch=request.getParameter("branch");
            enroll=request.getParameter("enroll");
            name=request.getParameter("name");
            email=request.getParameter("email");
            mob=request.getParameter("mob"); 
            password=request.getParameter("password");
            fNumber=request.getParameter("fNumber");
            fFruit=request.getParameter("fFruit");

            ps=con.prepareStatement("select enrollment_number from students");
            rs=ps.executeQuery();
            
            // check if student is already registered
            while(rs.next()) {
                String s = rs.getString(1);
                if ( enroll.equals(s) ) {
                    flag = true;
                    break;
                }
            }
       
            if(flag) {
                // redirect to error page with msg
                // 1.) student already registered
                // 2.) page should have link of forgot password
                System.err.println("This student is already registered.");
                response.setContentType("text/html");
                out.println("<h2><b>This student is already registered.</b><h2>"); 
                //RequestDispatcher rd=request.getRequestDispatcher("home.html");
                //rd.include(request,response);  
            } else {
                // Server side validation on student details can be put here
                // now assuming all details are valid
                valid = true;
                if (valid) {
                    // adding new student
                    ps=con.prepareStatement("insert into students"
                            + "(branch, enrollment_number, _name, _password, email_id, "
                            + "mobile_no, number, fruit) values(?,?,?,?,?,?,?,?)");
                    ps.setString(1, branch);
                    ps.setString(2, enroll);
                    ps.setString(3, name);
                    ps.setString(4, password);
                    ps.setString(5, email);
                    ps.setString(6, mob);
                    ps.setString(7, fNumber);
                    ps.setString(8, fFruit);
                    int i = ps.executeUpdate();

                    if(i!=0) {
                        out.println("<b>Successfully Registered!</b>");
                        RequestDispatcher rd=request.getRequestDispatcher("home.html");
                        rd.include(request,response);  
                    } else {
                        out.println("<body bgcolor='lightskyblue'>");
                        out.println("<center><h2><font color='red'>Registration failed!</h2></center>");
                        out.println("</body>");
                        RequestDispatcher rd=request.getRequestDispatcher("/StdReg.html");
                        rd.include(request,response);    
                    }
                }
            }
        } catch(Exception e){
            System.out.println(e);
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

