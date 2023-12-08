package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.GameSessionModel;

public class QuestionServlet extends HttpServlet {

    /**
     * Overrides the doGet method of the HttpServlet class. This method is called by
     * the server when a GET request is received.
     *
     * @param request  the HttpServletRequest object containing the request
     *                 information
     * @param response the HttpServletResponse object used to send the response
     * @throws ServletException if there is a servlet-related problem
     * @throws IOException      if there is an I/O problem
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null) {
            forwardToLoginPage(request, response);
        } else {
            GameSessionModel model = getOrCreateSessionModel(request);
            String source = (String) request.getAttribute("source");

            if (!"receiveFirstQuestion".equals(source)) {
                String[] answers = getAnswersFromRequest(request);
                model.checkUserAnswer(answers);
            }

            if (model.questionsID.isEmpty()) {
                handleSessionCompletion(request, response, model, session);
            } else {
                handleNextQuestion(request, response, model);
            }
        }
    }

    private void forwardToLoginPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("loginView.jsp").forward(request, response);
    }

    private String[] getAnswersFromRequest(HttpServletRequest request) {
        String[] answers = new String[3];
        answers[0] = (request.getParameter("option1") != null) ? request.getParameter("option1") : "null";
        answers[1] = (request.getParameter("option2") != null) ? request.getParameter("option2") : "null";
        answers[2] = (request.getParameter("option3") != null) ? request.getParameter("option3") : "null";
        return answers;
    }

    private void handleSessionCompletion(HttpServletRequest request, HttpServletResponse response,
            GameSessionModel model, HttpSession session) throws ServletException, IOException {
        String userId = (String) session.getAttribute("userId");
        try {
            model.updateResultInDB(userId);
            request.setAttribute("totalScore", model.getTotalScore());
            model.resetTotalScore();
            request.getRequestDispatcher("resultView.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleNextQuestion(HttpServletRequest request, HttpServletResponse response, GameSessionModel model)
            throws ServletException, IOException {
        try {
            model.fetchNextQuestionsFromDB();
            String text = model.currQuestionDTO.getText();
            String[] options = model.currQuestionDTO.getOptions();

            request.setAttribute("text", text);
            request.setAttribute("option1", options[0]);
            request.setAttribute("option2", options[1]);
            request.setAttribute("option3", options[2]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("questionView.jsp").forward(request, response);
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
