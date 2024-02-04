import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/RegisterForm"})
public class RegisterForm extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {            
            String name = req.getParameter("name");
            String email1 = req.getParameter("email1");
            String number = req.getParameter("number");
            String address = req.getParameter("address");
            String dob = req.getParameter("dob");
            String password = req.getParameter("password");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String db_host = "localhost";
                String db_user = "root";
                String db_pass = "";
                String db_name = "malim";

                try (Connection con = DriverManager.getConnection("jdbc:mysql://" + db_host + ":3306/" + db_name,
                        db_user, db_pass);
                        PreparedStatement ps = con.prepareStatement("INSERT INTO regis (name, email, number, address, dob, password) VALUES (?, ?, ?, ?, ?, ?)")) {
                    ps.setString(1, name);
                    ps.setString(2, email1);
                    ps.setString(3, number);
                    ps.setString(4, address);
                    ps.setString(5, dob);
                    ps.setString(6, password);

                    int count = ps.executeUpdate();
                    if (count > 0) {
                        resp.setContentType("text/html");
                        out.print("<div class=\"alert alert-success alert-box\" role=\"alert\"id=\"successAlert\">Admin registered successfully </div>");

                        RequestDispatcher rd = req.getRequestDispatcher("/login.html");
                        rd.include(req, resp);
                    } else {
                        resp.setContentType("text/html");
                        out.print("<div class=\"alert alert-danger alert-box col-6\" role=\"alert\"id=\"errorAlert\"Admin not registered successfully </div>");

                        RequestDispatcher rd = req.getRequestDispatcher("/login.html");
                        rd.include(req, resp);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                resp.setContentType("text/html");
                out.print("<div class=\"alert alert-danger\" role=\"alert\">Exception occurred:" + e.getMessage() + "</div>");

                RequestDispatcher rd = req.getRequestDispatcher("/login.html");
                rd.include(req, resp);
            }
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
