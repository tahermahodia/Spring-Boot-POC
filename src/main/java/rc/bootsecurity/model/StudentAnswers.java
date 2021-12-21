package rc.bootsecurity.model;

public class StudentAnswers {
    private Integer testId;
    private Integer studentId;
    private String[] questionIds;
    private String[] answerIds;

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String[] getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(String[] questionIds) {
        this.questionIds = questionIds;
    }

    public String[] getAnswerIds() {
        return answerIds;
    }

    public void setAnswerIds(String[] answerIds) {
        this.answerIds = answerIds;
    }
}
