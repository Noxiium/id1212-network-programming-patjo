/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.GameSessionModel;

/**
 *
 * @author Indiana Johan
 */
public class QuestionServlet extends HttpServlet {
    private boolean isFirstCall = true;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);

        if(session == null){
            request.getRequestDispatcher("loginView.jsp").forward(request, response);
        
        } 
        else{
            GameSessionModel model = getOrCreateSessionModel(request);

            if(!isFirstCall){
                String[] answers = new String[3];
                answers[0] = (request.getParameter("option1") != null) ? request.getParameter("option1") : "null";
                answers[1] = (request.getParameter("option2") != null) ? request.getParameter("option2") : "null";
                answers[2] = (request.getParameter("option3") != null) ? request.getParameter("option3") : "null";
                model.checkUserAnswer(answers);
            }

            if(model.questionsID.isEmpty()){
                String userId = (String)session.getAttribute("userId");
                model.updateResultInDB(userId);
                request.setAttribute("totalScore", model.getTotalScore());
                request.getRequestDispatcher("resultView.jsp").forward(request, response);
            }
            else{
                try{
                    model.fetchNextQuestionsFromDB();
                    String text = model.currQuestionDTO.getText();
                    String[] options = model.currQuestionDTO.getOptions();

                    request.setAttribute("text", text);
                    request.setAttribute("option1", options[0]);
                    request.setAttribute("option2", options[1]);
                    request.setAttribute("option3", options[2]);

                } catch (Exception e){
                    e.printStackTrace();
                }
                request.getRequestDispatcher("questionView.jsp").forward(request, response);
            }
            isFirstCall = false;
        }
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
        
    }

    /**
     * Returns the model associated with the current session, or create a new
     * one if none existed.
     *
     * @param request
     * @return model
     */
    private GameSessionModel getOrCreateSessionModel(HttpServletRequest request) {

        // Get or create a session for the current client 
        HttpSession session = request.getSession(true);

        // Retrieve the model associated with the current session,
        GameSessionModel model = (GameSessionModel) session.getAttribute("gameSessionModel");
        if (model == null) {
            model = new GameSessionModel();
            session.setAttribute("gameSessionModel", model);
        }
        return model;
    }
}
