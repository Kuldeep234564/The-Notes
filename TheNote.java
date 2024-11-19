package theNote;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "TheNote", urlPatterns = {"/TheNote"})
public class TheNote extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/user_registration";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "Kuld7458#";

    public TheNote() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");
        String dob = request.getParameter("dob"); // Capture Date of Birth

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (!password.equals(confirmPassword)) {
            out.println("<html><body>");
            out.println("<h2>Passwords do not match!</h2>");
            out.println("<a href='register.html'>Try Again</a>");
            out.println("</body></html>");
            return;
        }

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);

            // Updated SQL to include Date of Birth
            String sql = "INSERT INTO users (Name, Email, Password, Date_Of_Birth) VALUES (?, ?, ?, ?)";
            statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, dob); // Set Date of Birth

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                response.sendRedirect("login.html");
            } else {
                out.println("<html><body>");
                out.println("<h2>Registration Failed</h2>");
                out.println("<a href='register.html'>Try Again</a>");
                out.println("</body></html>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<h2>Database Connection Error: " + e.getMessage() + "</h2>");
            out.println("<a href='register.html'>Try Again</a>");
            out.println("</body></html>");
        } finally {
            try {
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
