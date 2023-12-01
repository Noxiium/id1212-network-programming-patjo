package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;

@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Get or create a session for the current client 
        HttpSession session = request.getSession(true);

        //Retrieve the model associated with the current session,
        //or create a new one if none existed.
        /* Model model = (Model) session.getAttribute("model");
        if (model == null) {
            model = new Model();
            session.setAttribute("model", model);
        }
         */
        //Forward request to index.jsp
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userInput = request.getParameter("guess");
        request.setAttribute("guess", userInput);

        

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conn = (Connection) DriverManager.getConnection("jdbc:derby://localhost:1527/Derby", "patjo", "patjo");

            //String query = "INSERT INTO APP.USERS(USERNAME, PASSWORD) VALUES (?,?)";

            //PreparedStatement preparedStatement = conn.prepareStatement(query);
            //preparedStatement.setString(1, "hej");    // NAME
            //preparedStatement.setString(2, "password"); // PASSWORD
            //preparedStatement.executeUpdate();
       

            //preparedStatement.close();
            
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO APP.USERS (USERNAME, PASSWORD) VALUES('"+ userInput +"', 'password')");
            //statement.executeUpdate("INSERT INTO APP.USERS (USERNAME, PASSWORD) VALUES('"+userInput+"', 'test')");
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("hello");
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);

    }
}
