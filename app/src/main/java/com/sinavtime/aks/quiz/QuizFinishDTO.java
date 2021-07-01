package com.sinavtime.aks.quiz;

import java.io.Serializable;

public class QuizFinishDTO implements Serializable {
    private Boolean quizFinish = Boolean.FALSE;
    private int totalScore=0;
    private int totalTrueCount=0;
    private int totalFalseCount=0;


    public QuizFinishDTO() {
    }

    public Boolean getQuizFinish() {
        return quizFinish;
    }

    public void setQuizFinish(Boolean quizFinish) {
        this.quizFinish = quizFinish;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalTrueCount() {
        return totalTrueCount;
    }

    public void setTotalTrueCount(int totalTrueCount) {
        this.totalTrueCount = totalTrueCount;
    }

    public int getTotalFalseCount() {
        return totalFalseCount;
    }

    public void setTotalFalseCount(int totalFalseCount) {
        this.totalFalseCount = totalFalseCount;
    }
}
