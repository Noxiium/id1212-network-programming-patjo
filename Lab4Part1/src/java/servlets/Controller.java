package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import model.Model;

@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the model associated with the current session,
        // or a new one if none existed.
        Model model = getOrCreateSessionModel(request);

        //Forward request to index.jsp
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the model associated with the current session,
        // or a new one if none existed.
        Model model = getOrCreateSessionModel(request);
        System.out.println(model.getRandomNumer());

        String userInput = request.getParameter("guess");
        request.setAttribute("guess", userInput);

        boolean isCorrectAnswer = model.checkAnswer(Integer.parseInt(userInput));

        // Retrieve the number of guesses made from the Model associated with the current session,
        // and set it as an attribute in the request for display in the JSP.
        int numberOfGuessesMade = model.getNumberOfGuesses();
        request.setAttribute("numberOfGuessesMade", numberOfGuessesMade);

        request.getRequestDispatcher("index.jsp").forward(request, response);

    }

    /**
     * Returns the model associated with the current session, or create a new
     * one if none existed.
     *
     * @param request
     * @return model
     */
    private Model getOrCreateSessionModel(HttpServletRequest request) {

        // Get or create a session for the current client 
        HttpSession session = request.getSession(true);

        // Retrieve the model associated with the current session,
        // or create a new one if none existed.
        Model model = (Model) session.getAttribute("model");
        if (model == null) {
            model = new Model();
            session.setAttribute("model", model);
        }
        return model;
    }
}

//
// Kod för att skriva till databas.
//Class.forName("org.apache.derby.jdbc.ClientDriver");        
//Connection conn = (Connection) DriverManager.getConnection("jdbc:derby://localhost:1527/Derby", "patjo", "patjo");      
//Statement statement = conn.createStatement();           
//statement.executeUpdate("INSERT INTO APP.USERS (USERNAME, PASSWORD) VALUES('"+ userInput +"', 'password')");
