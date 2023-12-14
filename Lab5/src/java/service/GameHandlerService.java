package service;

import java.util.ArrayList;
import java.util.List;
import model.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import repository.GameHandlerRepository;

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GameHandlerService {
    private GameHandlerRepository gameHandlerRepository;
    private ArrayList<QuestionDTO> questionList;
    private int questionIndex;
    private int score;
    
    @Autowired
    public GameHandlerService(GameHandlerRepository questionRepository) {
        this.gameHandlerRepository = questionRepository;
    }
    
    /**
     * Initializes the quiz with the given quiz ID.
     *
     * @param  quizId  the ID of the quiz to be initialized
     */
    public void initializeQuiz(int quizId){
        this.questionList = getAllQuestionsFromDB(quizId);
        this.questionIndex = 0;
        this.score = 0;
    }
    
    /**
     * Retrieves the next question from the question list.
     *
     * @throws IndexOutOfBoundsException if the question index is out of bounds
     * @return the next question
     */
    public QuestionDTO getNextQuestion()throws IndexOutOfBoundsException{
        QuestionDTO question = questionList.get(questionIndex);
        questionIndex++;
        return question;
        
    }
    
    /**
     * Checks the user's answer against the correct answer for a question.
     *
     * @param  userAnswer  a list of strings representing the user's answer
     * @return             void
     */
    public void checkUserAnswer(List<String> userAnswer){
        for(int i = 0; i < 3; i++){      
            if(!(userAnswer.get(i).equals(questionList.get(questionIndex-1).getCorrectAnswerIndexes()[i])))
                return;
        }
        this.score += 2;
    }
    
    /**
     * Inserts the result of a quiz into the database.
     *
     * @param  userId   the ID of the user
     * @param  quizId   the ID of the quiz
     * @param  score    the score achieved in the quiz
     */
    public void insertResultIntoDB(int userId, int quizId, int score){
        gameHandlerRepository.insertResultIntoDB(userId, quizId, score);
    }

    /**
    * Retrieves all the results from the database.
    *
    * @return the list of all results from the database
    */
    private ArrayList<QuestionDTO> getAllQuestionsFromDB(int quizId){
        this.questionList = gameHandlerRepository.getAllQuestionsFromDB(quizId);
        return questionList;
    }
    
    public int getScore(){
        return this.score;
    }

    /**
     * Send the result to the user via email.
     *
     * @param  username  the username of the user
     * @param  score     the score to be sent
     * @return           none
     */
    public void sendResultToUser(String username, int score) {
        MailSender mailSender = new MailSender();
        mailSender.sendMail(username, score);
    }

}
