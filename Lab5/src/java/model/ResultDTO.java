/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Indiana Johan
 */
public class ResultDTO {
    private String userName;
    private String quizSubject;
    private int score;
    
    
    public ResultDTO(String userName, String quizSubject, int score){
        this.userName = userName;
        this.quizSubject = quizSubject;
        this.score = score;
    }
    
    public String getUserName(){return this.userName;}
    public String getquizSubject() {return this.quizSubject;}
    public int getScore() {return this.score;}
    
    @Override
    public String toString(){
        return "Username: " + this.userName + "\nSubject: " + this.quizSubject + "\nScore: " + this.score + "\n------------------\n"; 
    }
}
