package model;

public class QuestionDTO {
    private String text;
    private String[] options;
    private String[] correctAnswerIndexes;

    public QuestionDTO(String text, String options, String correctAnswerIndex) {
        this.text = text;
        this.options = convertDBRepresentationToStringArray(options);
        this.correctAnswerIndexes = convertDBRepresentationToStringArray(correctAnswerIndex);
        changeCorrectAnswerFormat();
    }

    private String[] convertDBRepresentationToStringArray(String DBString) {
        String[] StringArray = DBString.split("/");
        return StringArray;
    }

    private void changeCorrectAnswerFormat() {
        for (int i = 0; i < 3; i++) {
            if (this.correctAnswerIndexes[i].equals("1")) {
                this.correctAnswerIndexes[i] = "true";
            } else {
                this.correctAnswerIndexes[i] = "null";
            }
        }
    }

    public String getText() {
        return this.text;
    }

    public String[] getOptions() {
        return this.options;
    }

    public String[] getCorrectAnswerIndexes() {
        return this.correctAnswerIndexes;
    }
}
