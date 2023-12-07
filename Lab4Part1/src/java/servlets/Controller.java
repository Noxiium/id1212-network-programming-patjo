package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Model;

public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the model associated with the current session,
        // or a new one if none existed.
        Model model = getOrCreateSessionModel(request);
        System.out.println("Contr: doGet");
        System.out.println("Session ID: " +  request.getSession().getId());
        System.out.println("Contr: model.getRandomNumber = " +model.getRandomNumer());
        System.out.println("________________________________________________________");
        
        int numberOfGuessesMade = model.getNumberOfGuesses();
        request.setAttribute("numberOfGuessesMade", numberOfGuessesMade);
        //Forward request to index.jsp
        request.getRequestDispatcher("gameView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Retrieve the model associated with the current session,
        // or a new one if none existed.
        Model model = getOrCreateSessionModel(request);
        System.out.println("Contr: doPost");
        System.out.println("Session ID: " +  request.getSession().getId());
        System.out.println("Contr: model.getRandomNumber = " +model.getRandomNumer());
        System.out.println("________________________________________________________");
        String userInput = request.getParameter("guess");
        request.setAttribute("guess", userInput);

        // Retrieve the number of guesses made from the Model associated with the current session,
        // and set it as an attribute in the request for display in the JSP
        boolean isCorrectAnswer = model.checkAnswer(Integer.parseInt(userInput));
        int numberOfGuessesMade = model.getNumberOfGuesses();
        request.setAttribute("numberOfGuessesMade", numberOfGuessesMade);
        
        if(isCorrectAnswer){
            model.restart();
            request.getRequestDispatcher("endOfGameView.jsp").forward(request, response);

        } else{

            request.getRequestDispatcher("gameView.jsp").forward(request, response);
        }
    }

    /**
     * Returns the model associated with the current session, or create a new
     * one if none existed.
     *
     * @param request
     * @return model
     */
    private Model getOrCreateSessionModel(HttpServletRequest request) {

        System.out.println("Contr: getOrCreateSessionModel");
        // Get or create a session for the current client 
        HttpSession session = request.getSession(true);

        // Retrieve the model associated with the current session,
        // or create a new one if none existed.
        Model model = (Model) session.getAttribute("model");
        if (model == null) {
            model = new Model();
            session.setAttribute("model", model);
        }
        System.out.println("Session ID: " +  request.getSession().getId());
        System.out.println("________________________________________________________");
        return model;
    }
}
