package com.spacekuukan.application.function;

import java.util.ArrayList;

public class Spaceport {

    private int id, level, cost, buy;
    private String name, nickname;

    private ArrayList starshipStation;

    public Spaceport(int id, String name, String nickname, int level, int cost, int buy) {

        this.id         = id;
        this.name       = name;
        this.nickname   = nickname;
        this.level      = level;
        this.cost       = cost;
        this.buy        = buy;

        this.starshipStation = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public int getCost() {
        return cost;
    }

    public void setBuy(int buy) {
        this.buy = buy;
    }

    public int getBuy() {
        return buy;
    }

    public ArrayList getStarshipStation() {
        return starshipStation;
    }

    public int getNbStarshipStation() {
        return starshipStation.size();
    }

    public boolean verifyStation() {
        if(getNbStarshipStation() < getLevel() && getBuy() == 1) {
            return true;
        } else {
            return false;
        }
    }
}
