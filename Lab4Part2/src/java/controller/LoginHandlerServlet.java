package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserModel;

public class LoginHandlerServlet extends HttpServlet {

    /**
     * Overrides the `doGet` method of the `HttpServlet` class.
     * Forwards the `HttpServletRequest` and `HttpServletResponse` objects to the
     * `loginView.jsp` page.
     *
     * @param request  HttpServletRequest object representing the client's request
     * @param response HttpServletResponse object representing the server's response
     * @throws ServletException if there is a servlet-related problem
     * @throws IOException      if there is an I/O problem
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("loginView.jsp").forward(request, response);
    }

    /**
     * Handles a POST request from a client.
     *
     * Retrieves the session model or creates a new one.
     * Processes login credentials and redirects based on the result.
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if the servlet encounters difficulty fulfilling the
     *                          request
     * @throws IOException      if an I/O error occurs while processing the request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserModel model = getOrCreateSessionModel(request);

        String userMail = request.getParameter("usermail");
        request.setAttribute("usermail", userMail);

        String userPassword = request.getParameter("password");
        request.setAttribute("password", userPassword);

        try {
            int userID = model.handleLoginInPostRequest(userMail, userPassword);
            if (userID == -1) {
                handleWrongPassword(request, response);
            } else {
                handleSuccessfulLogin(request, response, userID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleWrongPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("wrongPasswordView.jsp").forward(request, response);
    }

    private void handleSuccessfulLogin(HttpServletRequest request, HttpServletResponse response, int userID)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        session.setAttribute("userId", String.valueOf(userID));
        request.getRequestDispatcher("mainView.jsp").forward(request, response);
    }

    /**
     * Retrieves or creates a session model for the given HTTP request.
     *
     * @param request the HTTP request object
     * @return the session model associated with the request
     */
    private UserModel getOrCreateSessionModel(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        UserModel model = (UserModel) session.getAttribute("model");
        if (model == null) {
            model = new UserModel();
            session.setAttribute("model", model);
        }
        return model;
    }
}
