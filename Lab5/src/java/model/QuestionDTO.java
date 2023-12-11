/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Indiana Johan
 */
public class QuestionDTO {
    private String text;
    private String[] options;
    private String[] correctAnswerIndexes;

    public QuestionDTO(String text, String options, String correctAnswerIndex){
        this.text = text;
        this.options = convertDBRepresentationToStringArray(options);
        this.correctAnswerIndexes = convertDBRepresentationToStringArray(correctAnswerIndex);
        changeCorrectAnswerFormat();
    }


    private String[] convertDBRepresentationToStringArray(String DBString){
        String[] StringArray = DBString.split("/");
        return StringArray;
    }

    private void changeCorrectAnswerFormat(){
        for(int i = 0; i < 3; i++){
            if (this.correctAnswerIndexes[i].equals("1")){
                this.correctAnswerIndexes[i] = "true";
            } else {
                this.correctAnswerIndexes[i] = "null";
            }
        }
    }

    public String getText(){return this.text;}
    public String[] getOptions(){return this.options;}
    public String[] getCorrectAnswerIndexes(){return this.correctAnswerIndexes;}

    @Override
    public String toString(){
        return this.text + "\n" + 
        this.options[0] + "\n" + this.options[1] + "\n" + this.options[2] + "\n" + 
        this.correctAnswerIndexes[0] + "\n" + this.correctAnswerIndexes[1] + "\n" + this.correctAnswerIndexes[2] + "---------------------------------------\n";
    }
   
 }
