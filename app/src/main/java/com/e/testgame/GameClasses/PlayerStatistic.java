package com.e.testgame.GameClasses;

public class PlayerStatistic {


    private String score;
    private String name;

    public PlayerStatistic(String score, String name) {
        this.score = score;
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "|| Player: " + name + " ||" + " Score: " + score + " ||";



    }
}
