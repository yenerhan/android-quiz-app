package com.sinavtime.aks.quiz;

public enum QuizType {
    QUESTION(1, "Question"),
    IMAGE(2, "Image"),
    END(3,"End");

    private final int id;
    private final String ad;

    QuizType(int id, String ad) {
        this.id = id;
        this.ad = ad;
    }

    public int getId() {
        return id;
    }

    public String getAd() {
        return ad;
    }

    public static QuizType getQuizType(int id) {
        if (id==1) {
            return QUESTION;
        } else if (id==2) {
            return IMAGE;
        } else {
            return (id==3) ? END : null;
        }
    }
}
