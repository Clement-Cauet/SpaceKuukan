package com.spacekuukan.application.function;

public class Base extends Planet {

    public static Base instanceBase;

    public InstanceFunction instanceFunction;

    public static synchronized Base getInstance(int id, String name, int latitude, int longitude, int credit, int hydrogen) {
        if (instanceBase == null)
            instanceBase = new Base(id, name, latitude, longitude, credit, hydrogen);
        return instanceBase;
    }

    public Base(int id, String name, int latitude, int longitude, int credit, int hydrogen) {
        super(id, name, latitude, longitude, credit, hydrogen);

    }


}
