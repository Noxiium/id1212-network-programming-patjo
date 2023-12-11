/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author PC
 */
public class ResultTableDTO {
    private String userName;
    private String quizSubject;
    private String totalScore;
    
    public ResultTableDTO(String userName, String quizSubject, String totalScore){
        this.userName = userName;
        this.quizSubject = quizSubject;
        this.totalScore = totalScore;
    }
    
    public String getUserName(){return this.userName;}
    public String getquizSubject() {return this.quizSubject;}
    public String getTotalScore() {return this.totalScore;}
}
