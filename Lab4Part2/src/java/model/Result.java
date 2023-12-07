/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javax.persistence.*;

@Entity
@Table(name = "RESULTS")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "USER_ID")
    private int USER_ID;
    
    @Column(name = "QUIZ_ID")
    private int QUIZ_ID;
     
    private int SCORE;
    
    public Result(){
      
    }
    
    public void setUserId(int userId) {
        this.USER_ID = userId;
    }

    public void setQuizId(int quizId) {
        this.QUIZ_ID = quizId;
    }

    public void setScore(int score) {
        this.SCORE = score;
    }

}
