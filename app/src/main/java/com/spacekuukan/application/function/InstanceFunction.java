package com.spacekuukan.application.function;

import androidx.navigation.NavController;

import java.util.List;

public class InstanceFunction {

    public static InstanceFunction instanceFunction;

    public NavController navController;

    public List<Integer> manageSystemArgument;
    public List<Starship> starshipList;
    public List<SpacePort> spacePortList;

    public static synchronized InstanceFunction getInstance() {
        if (instanceFunction == null)
            instanceFunction = new InstanceFunction();
        return instanceFunction;
    }

    public InstanceFunction() {

    }

    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    public NavController getNavController() {
        return navController;
    }

    public void setManageSystemArgument(List manageSystemArgument) {
        this.manageSystemArgument = manageSystemArgument;
    }

    public List getManageSystemArgument() {
        return manageSystemArgument;
    }

}
