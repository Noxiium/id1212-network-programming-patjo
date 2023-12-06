package model;

public class QuestionDTO {
    private int ID;
    private String text;
    private String[] options;
    private String[] correctAnswerIndexes;

    public QuestionDTO(int ID, String text, String[] options, String correctAnswerIndex){
        this.ID = ID;
        this.text = text;
        this.options = options;
        this.correctAnswerIndexes = convertDBRepresentationCorrectAnswerToArray(correctAnswerIndex);
    }

    private String[] convertDBRepresentationCorrectAnswerToArray(String correctAnswer){
        String[] correctAnswerIndexes = correctAnswer.split("/");

        for(int i = 0; i < correctAnswerIndexes.length; i++){
            System.out.println(correctAnswerIndexes[i]);
        }
        return correctAnswerIndexes;
    }

    public int getID(){return this.ID;}
    public String getText(){return this.text;}
    public String[] getOptions(){return this.options;}
    public String[] getCorrectAnswerIndexes(){return this.correctAnswerIndexes;}
}
