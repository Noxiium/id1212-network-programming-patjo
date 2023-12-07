package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ResultModel;

/**
 *
 * @author PC
 */
public class ResultServlet extends HttpServlet {

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

        if (session == null) {
            request.getRequestDispatcher("loginView.jsp").forward(request, response);

        } else {
            
            ResultModel model = getOrCreateSessionModel(request);
            ArrayList<Object> historyResult = model.fetchResultsFromDB();
           
            request.setAttribute("list", historyResult);
            
            request.getRequestDispatcher("historyResultView.jsp").forward(request, response);
        }
    }

    
    
     private ResultModel getOrCreateSessionModel(HttpServletRequest request) {

        // Get or create a session for the current client 
        HttpSession session = request.getSession(true);

        // Retrieve the model associated with the current session,
        ResultModel model = (ResultModel) session.getAttribute("resultModel");
        if (model == null) {
            model = new ResultModel();
            session.setAttribute("resultModel", model);
        }
        return model;
    }
}
