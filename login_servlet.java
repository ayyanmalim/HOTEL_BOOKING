import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HP
 */
@WebServlet(urlPatterns = {"/login_servlet"})
public class login_servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            String email = req.getParameter("email");
            String pass = req.getParameter("pass");

           try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String db_host = "localhost";
                String db_user = "root";
                String db_pass = "";
                String db_name = "malim";

                try (Connection con = DriverManager.getConnection("jdbc:mysql://" + db_host + ":3306/" + db_name,
                        db_user, db_pass);
                        PreparedStatement ps = con.prepareStatement("SELECT * FROM regis WHERE email=? AND password=?")) {
                    ps.setString(1, email);
                    ps.setString(2, pass);

                    ResultSet count = ps.executeQuery();
                    if (count.next()) {
                        resp.setContentType("text/html");
                        out.print("<div class=\"alert alert-success alert-box\" role=\"alert\"id=\"successAlert\">Admin login successfully </div>");

                        RequestDispatcher rd = req.getRequestDispatcher("/adminPanel.html");
                        rd.include(req, resp);
                    } else {
                        resp.setContentType("text/html");
                        out.print("<div class=\"alert alert-danger alert-box\" role=\"alert\"id=\"errorAlert\">Email and Password didn't match</div>");

                        RequestDispatcher rd = req.getRequestDispatcher("/login.html");
                        rd.include(req, resp);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                resp.setContentType("text/html");
                out.print("<h3>Exception occurred:" + e.getMessage() + "</h3>");

                RequestDispatcher rd = req.getRequestDispatcher("/login.html");
                rd.include(req, resp);
            }
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
