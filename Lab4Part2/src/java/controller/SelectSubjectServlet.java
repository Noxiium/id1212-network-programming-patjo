package controller;

import java.util.ArrayList;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.GameSessionModel;
import model.SubjectDTO;

public class SelectSubjectServlet extends HttpServlet {

    /**
     * Process the HTTP request and generate a response.
     *
     * @param request  the HttpServletRequest object representing the client's
     *                 request
     * @param response the HttpServletResponse object representing the server's
     *                 response
     * @throws ServletException if the request cannot be handled
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            request.getRequestDispatcher("loginView.jsp").forward(request, response);
        } else {
            String selectedSubjectID = request.getParameter("selectedSubject");
            String selectedSubjectText = request.getParameter("subjectText_" + selectedSubjectID);
            GameSessionModel model = getOrCreateSessionModel(request);

            try {
                if (selectedSubjectID == null) {
                    ArrayList<SubjectDTO> subjectList = model.getQuizSubjectsFromDB();
                    session.setAttribute("list", subjectList);
                    request.getRequestDispatcher("selectSubjectView.jsp").forward(request, response);
                } else {
                    model.fetchQuestionIDsFromDB(selectedSubjectID);
                    String source = request.getParameter("source");
                    request.setAttribute("source", source);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/QuestionServlet");
                    dispatcher.forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private GameSessionModel getOrCreateSessionModel(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        GameSessionModel model = (GameSessionModel) session.getAttribute("gameSessionModel");
        if (model == null) {
            model = new GameSessionModel();
            session.setAttribute("gameSessionModel", model);
        }
        return model;
    }

}

