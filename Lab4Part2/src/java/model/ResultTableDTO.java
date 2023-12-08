package model;

public class ResultTableDTO {
    private String userName;
    private String quizSubject;
    private String totalScore;

    public ResultTableDTO(String userName, String quizSubject, String totalScore) {
        this.userName = userName;
        this.quizSubject = quizSubject;
        this.totalScore = totalScore;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getquizSubject() {
        return this.quizSubject;
    }

    public String getTotalScore() {
        return this.totalScore;
    }
}
