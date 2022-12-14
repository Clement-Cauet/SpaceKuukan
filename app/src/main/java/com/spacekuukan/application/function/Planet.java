package com.spacekuukan.application.function;

public class Planet {

    protected int id;
    protected String name;
    protected int power;
    protected int resource;
    protected int latitude;
    protected int longitude;

    public Planet(int id, String name, int power, int resource, int latitude, int longitude) {

        this.id         = id;
        this.name       = name;
        this.power      = power;
        this.resource   = resource;
        this.latitude   = latitude;
        this.longitude  = longitude;

    }
}
