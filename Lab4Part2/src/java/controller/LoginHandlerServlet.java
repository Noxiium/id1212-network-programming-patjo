package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.SubjectDTO;
import model.UserModel;

public class LoginHandlerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Forward request to loginView.jsp
        request.getRequestDispatcher("loginView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Retrieve the model associated with the current session,
        // or a new one if none existed.
        UserModel model = getOrCreateSessionModel(request);

        //Get parameters from inputfields in loginView
        String userMail = request.getParameter("usermail");
        request.setAttribute("usermail", userMail);

        String userPassword = request.getParameter("password");
        request.setAttribute("password", userPassword);
        
        try{
           int userID = model.handleLoginInPostRequest(userMail, userPassword);
            if(userID == -1){
                request.getRequestDispatcher("wrongPasswordView.jsp").forward(request, response);
            }
            else{
                String ID = String.valueOf(userID);
                HttpSession session = request.getSession(false);
                session.setAttribute("userId", ID);

                ArrayList<SubjectDTO> subjectList = model.getQuizSubjectFromDB();
                session.setAttribute("list", subjectList);
        
                request.getRequestDispatcher("mainView.jsp").forward(request, response);
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
    private UserModel getOrCreateSessionModel(HttpServletRequest request) {

        // Get or create a session for the current client 
        HttpSession session = request.getSession(true);

        // Retrieve the model associated with the current session,
        // or create a new one if none existed.
        UserModel model = (UserModel) session.getAttribute("model");
        if (model == null) {
            model = new UserModel();
            session.setAttribute("model", model);
        }
        return model;
    }
}
