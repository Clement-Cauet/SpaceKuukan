package com.spacekuukan.application.function;

import android.os.CountDownTimer;

import com.spacekuukan.application.R;

public class Starship {

    protected int id, level, speed, cost, mining_rate, buy, planet, port, time;
    protected String name, nickname;

    protected CountDownTimer countDownTimer;

    public Starship(int id, String name, String nickname, int level, int cost, int mining_rate, int speed, int buy, int planet, int port) {

        this.id             = id;
        this.name           = name;
        this.nickname       = nickname;
        this.level          = level;
        this.cost           = cost;
        this.mining_rate    = mining_rate;
        this.speed          = speed;
        this.buy            = buy;
        this.planet         = planet;
        this.port           = port;

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

    public int getMining_rate() {
        return mining_rate * getLevel();
    }

    public int getSpeed() {
        return speed * getLevel();
    }

    public void setBuy(int buy) {
        this.buy = buy;
    }

    public int getBuy() {
        return buy;
    }

    public void setPlanet(int planet) {
        this.planet = planet;
    }

    public int getPlanet() {
        return planet;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setCountDownTimer(CountDownTimer countDownTimer) {
        this.countDownTimer = countDownTimer;
    }

    public CountDownTimer getCountDownTimer() {
        return countDownTimer;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public int[] getImageStarshipId() {
        return new int[]{R.mipmap.starship1, R.mipmap.starship2, R.mipmap.starship3, R.mipmap.starship4, R.mipmap.starship5, R.mipmap.starship6, R.mipmap.starship7, R.mipmap.starship8};
    }

}
