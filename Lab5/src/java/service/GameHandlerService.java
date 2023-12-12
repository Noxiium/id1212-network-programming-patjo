package service;

import java.util.ArrayList;
import java.util.List;
import model.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import repository.GameHandlerRepository;

/**
 *
 * @author Indiana Johan
 */
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
    
    public void initializeQuiz(int quizId){
        this.questionList = getAllQuestionsFromDB(quizId);
        this.questionIndex = 0;
        this.score = 0;
    }
    
    public QuestionDTO getNextQuestion()throws IndexOutOfBoundsException{
        QuestionDTO question = questionList.get(questionIndex);
        questionIndex++;
        return question;
        
    }
    
    public void checkUserAnswer(List<String> userAnswer){
        for(int i = 0; i < 3; i++){      
            if(!(userAnswer.get(i).equals(questionList.get(questionIndex-1).getCorrectAnswerIndexes()[i])))
                return;
        }
        this.score += 2;
    }
    
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

    public void sendResultToUser(String username, int score) {
        MailSender mailSender = new MailSender();
        mailSender.sendMail(username, score);
    }

}
