package model;

public class SubjectDTO {
    public String subjectText;
    public int subjectID;

    public SubjectDTO(String subjectText, int subjectID){
        this.subjectText = subjectText;
        this.subjectID = subjectID;
    }

    public String getSubjectText(){
        return this.subjectText;
    }

    public int getSubjectID(){
        return this.subjectID;
    }
}
