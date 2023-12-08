package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ResultModel;
import model.ResultTableDTO;

public class ResultServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        System.out.println("SessionId: " + session.getId());

        if (session == null) {
            request.getRequestDispatcher("loginView.jsp").forward(request, response);

        } else {

            ResultModel model = getOrCreateSessionModel(request);
            try {
                model.fetchResultsFromDB();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ArrayList<ResultTableDTO> historyResult = model.getResultHistoryList();

            request.setAttribute("list2", historyResult);

            request.getRequestDispatcher("historyResultView.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Retrieves an existing session model or creates a new one if none exists.
     *
     * @param request the HttpServletRequest object representing the HTTP request
     * @return the ResultModel object representing the session model
     */
    private ResultModel getOrCreateSessionModel(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        ResultModel model = (ResultModel) session.getAttribute("resultModel");
        if (model == null) {
            model = new ResultModel();
            session.setAttribute("resultModel", model);
        }
        return model;
    }
}
