package com.spacekuukan.application.function;

public class Starship {

    protected int id;
    protected String name;
    protected int level;
    protected int speed;
    protected int cost;

    protected int power;
    protected int mining_rate;

    public Starship(int id, String name, int level, int speed, int cost, int power, int mining_rate) {

        this.id             = id;
        this.name           = name;
        this.level          = level;
        this.speed          = speed;
        this.cost           = cost;
        this.power          = power;
        this.mining_rate    = mining_rate;

    }
}
