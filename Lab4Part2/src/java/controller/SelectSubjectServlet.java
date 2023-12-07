/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
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
public class SelectSubjectServlet extends HttpServlet {
    private String selectedSubjectID;
    private String selectedSubjectText;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
            
        HttpSession session = request.getSession(false);
        
            if(session == null){
                request.getRequestDispatcher("loginView.jsp").forward(request, response);
                
            } else{

                this.selectedSubjectID = request.getParameter("selectedSubject");
                this.selectedSubjectText = request.getParameter("subjectText_" + selectedSubjectID);

                GameSessionModel model = getOrCreateSessionModel(request);
                
                try {
                    model.fetchQuestionsIDFromDB(selectedSubjectID);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/QuestionServlet");
                    dispatcher.forward(request, response);
                    
                } catch (Exception e){
                    e.printStackTrace();
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

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
