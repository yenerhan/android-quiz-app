package com.sinavtime.aks.model.quiz;

import java.io.Serializable;

public class QuizDTO implements Serializable {
    private Long quizId;
    private String quiz_name;
    private QestionClobDTO qestionClobDTO;
    private String comment;
    private Integer totalQuestion=0;
    private Integer totalScore=0;
    private Integer totalTrueCount=0;
    private Integer totalFalseCount=0;

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public String getQuiz_name() {
        return quiz_name;
    }

    public void setQuiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }

    public QestionClobDTO getQestionClobDTO() {
        return qestionClobDTO;
    }

    public void setQestionClobDTO(QestionClobDTO qestionClobDTO) {
        this.qestionClobDTO = qestionClobDTO;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public Integer getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(Integer totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getTotalTrueCount() {
        return totalTrueCount;
    }

    public void setTotalTrueCount(Integer totalTrueCount) {
        this.totalTrueCount = totalTrueCount;
    }

    public Integer getTotalFalseCount() {
        return totalFalseCount;
    }

    public void setTotalFalseCount(Integer totalFalseCount) {
        this.totalFalseCount = totalFalseCount;
    }
}
