package com.spacekuukan.application.function;

import java.util.List;

public class InstanceFunction {

    public static InstanceFunction instanceFunction;

    public List<Starship> starshipList;
    public List<SpacePort> spacePortList;

    public static synchronized InstanceFunction getInstance() {
        if (instanceFunction == null)
            instanceFunction = new InstanceFunction();
        return instanceFunction;
    }

    public InstanceFunction() {

    }



}
