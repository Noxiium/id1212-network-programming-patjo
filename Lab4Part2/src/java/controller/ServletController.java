package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Model;
//kommentar
@WebServlet(name = "ServletController", urlPatterns = {"/ServletController", "/"})
public class ServletController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Retrieve the model associated with the current session,
        // or a new one if none existed.
        Model model = getOrCreateSessionModel(request);
        System.out.println("Contr: doGet");
        System.out.println("Session ID: " +  request.getSession().getId());
        System.out.println("________________________________________________________");
        
        //Forward request to loginView.jsp
        request.getRequestDispatcher("loginView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Retrieve the model associated with the current session,
        // or a new one if none existed.
        Model model = getOrCreateSessionModel(request);
        System.out.println("Contr: doPost");
        System.out.println("Session ID: " +  request.getSession().getId());

        //Get parameters from inputfields in loginView
        String userMail = request.getParameter("usermail");
        request.setAttribute("usermail", userMail);

        String userPassword = request.getParameter("password");
        request.setAttribute("password", userPassword);
        
        try{
        if(!model.handleLoginInPostRequest(userMail, userPassword)){
            request.getRequestDispatcher("wrongPasswordView.jsp").forward(request, response);
        }
        else{
            // TODO Call model, get QUIZZES from DB.
            // Send the quizzes as attributes to VIEW.
            request.getRequestDispatcher("selectSubjectView.jsp").forward(request, response);
        }

        } catch (Exception e){
            e.printStackTrace();
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

//
// Kod för att skriva till databas.
//Class.forName("org.apache.derby.jdbc.ClientDriver");        
//Connection conn = (Connection) DriverManager.getConnection("jdbc:derby://localhost:1527/Derby", "patjo", "patjo");      
//Statement statement = conn.createStatement();           
//statement.executeUpdate("INSERT INTO APP.USERS (USERNAME, PASSWORD) VALUES('"+ userInput +"', 'password')");
