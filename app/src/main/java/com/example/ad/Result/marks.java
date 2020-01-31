package com.example.ad.Result;

public class marks {

    private int score;
    private String moudule;

    public marks() {
    }

    public marks(int score, String moudule) {
        this.score = score;
        this.moudule = moudule;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getMoudule() {
        return moudule;
    }

    public void setMoudule(String moudule) {
        this.moudule = moudule;
    }
}
