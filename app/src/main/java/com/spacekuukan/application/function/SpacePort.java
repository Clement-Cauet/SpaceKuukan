package com.spacekuukan.application.function;

public class SpacePort {
    private int id;
    private int level;
    private int nb_starship;

    public SpacePort(int id, int level) {

        this.id     = id;
        this.level  = level;

    }

    public void upgradeSpacePort() {
        this.level++;
    }
}
