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
import java.sql.ResultSet;
import java.sql.SQLException;
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginSevlet"})


/**
 * implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String JDBC_URL = "jdbc:mysql://localhost:3306/user_registration";
	    private static final String JDBC_USERNAME = "root";
	    private static final String JDBC_PASSWORD = "Kuld7458#";

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  String email = request.getParameter("email");
	        String password = request.getParameter("password");
	        
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
	            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
	            PreparedStatement statement = conn.prepareStatement(sql);
	            statement.setString(1, email);
	            statement.setString(2, password);
	            ResultSet resultSet = statement.executeQuery();
	            
	            if (resultSet.next()) {
	                response.sendRedirect("Book.html");
	            } else {
	                response.setContentType("text/html");
	                PrintWriter out = response.getWriter();
	                out.println("<html><body>");
	                out.println("<h2>Login Failed</h2>");
	                out.println("<p>Invalid email or password. Please try again.</p>");
	                out.println("</body></html>");
	            }
	            
	            statement.close();
	            conn.close();
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	            response.getWriter().println("Database Connection Error: " + e.getMessage());
	        }
	    }
	
	}


