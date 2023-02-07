package com.spacekuukan.application.function;

import com.spacekuukan.application.R;

public class Planet {

    protected int id, latitude, longitude, credit, hydrogen;
    protected String name;

    public Planet(int id, String name, int latitude, int longitude, int credit, int hydrogen) {

        this.id         = id;
        this.name       = name;
        this.latitude   = latitude;
        this.longitude  = longitude;
        this.credit     = credit;
        this.hydrogen   = hydrogen;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void setHydrogen(int hydrogen) {
        this.hydrogen = hydrogen;
    }

    public int getCredit() {
        return credit;
    }

    public int getHydrogen() {
        return hydrogen;
    }

    public int[] getImagePlanetId() {
        return new int[]{R.mipmap.planet3, R.mipmap.planet1, R.mipmap.planet2, R.mipmap.planet4, R.mipmap.planet5, R.mipmap.planet6, R.mipmap.planet8, R.mipmap.planet9};
    }

}
