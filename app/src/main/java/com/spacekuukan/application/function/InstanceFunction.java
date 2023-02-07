package com.spacekuukan.application.function;

import androidx.navigation.NavController;

import com.spacekuukan.application.R;

import java.util.ArrayList;
import java.util.List;

public class InstanceFunction {

    public static InstanceFunction instanceFunction;

    public NavController navController;

    public List<Integer> manageSystemArgument;
    public ArrayList<Starship> starshipList;
    public ArrayList<Spaceport> spaceportList;
    public ArrayList<Planet> planetList;

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

    public void setPlanetList(ArrayList planetList) {
        this.planetList = planetList;
    }

    public ArrayList getPlanetList() { return planetList; }

    public void setStarshipList(ArrayList starshipList) {
        this.starshipList = starshipList;
    }

    public ArrayList getStarshipList() { return starshipList; }

    public void setSpaceportList(ArrayList spaceportList) {
        this.spaceportList = spaceportList;
    }

    public ArrayList getSpaceportList() {
        return spaceportList;
    }

}
