package com.spacekuukan.application.function;

import android.content.Context;
import android.view.View;

import com.spacekuukan.application.db.DatabaseAccess;
import com.spacekuukan.application.db.DatabaseThread;

import java.util.ArrayList;

public class ManageSystem {

    private DatabaseAccess databaseAccess;
    private DatabaseThread databaseThread;

    public ManageSystem(Context context) {
        this.databaseAccess = DatabaseAccess.getInstance(context);
        this.databaseThread = DatabaseThread.getInstance(databaseAccess, null, null);
    }

    public boolean buyStarship(ArrayList starship) {

        ArrayList selectPlanetData = databaseAccess.selectPlanetData(1);

        int credit = Integer.valueOf((String) selectPlanetData.get(5)) - Integer.valueOf((String) starship.get(5));
        if(credit >= 0) {
            databaseAccess.updateCreditPlanet(1, credit);
            databaseThread.run();

            databaseAccess.updateStarship(Integer.valueOf((String) starship.get(0)), true);

            return true;
        }

        return false;

    }

    public boolean sellStarship(ArrayList starship) {

        ArrayList selectPlanetData = databaseAccess.selectPlanetData(1);

        int credit = Integer.valueOf((String) selectPlanetData.get(5)) + Integer.valueOf((String) starship.get(5)) / 2;
        if(databaseAccess.selectCountStarship(true) > 1) {
            databaseAccess.updateCreditPlanet(1, credit);
            databaseThread.run();

            databaseAccess.updateStarship(Integer.valueOf((String) starship.get(0)) ,null,false);

            return true;

        }

        return false;

    }

    public void customStarship() {

    }
}
