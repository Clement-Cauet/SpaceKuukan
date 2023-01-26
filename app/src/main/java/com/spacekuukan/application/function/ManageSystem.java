package com.spacekuukan.application.function;

import android.content.Context;
import android.view.View;

import com.spacekuukan.application.db.DatabaseAccess;
import com.spacekuukan.application.db.DatabaseThread;

import java.util.ArrayList;

public class ManageSystem {

    private DatabaseAccess databaseAccess;
    private DatabaseThread databaseThread;

    private ArrayList selectPlanetData;

    public ManageSystem(Context context) {

        this.databaseAccess = DatabaseAccess.getInstance(context);
        this.databaseThread = DatabaseThread.getInstance(databaseAccess, null, null);

        this.selectPlanetData = databaseAccess.selectPlanetData(1);

    }

    public boolean buySpaceport(ArrayList selectSpaceportData) {

        int hydrogen = Integer.valueOf((String) selectPlanetData.get(6)) - Integer.valueOf((String) selectSpaceportData.get(4));

        if(hydrogen >= 0) {

            databaseAccess.updateCreditPlanet(1, Integer.valueOf((String) selectPlanetData.get(5)), hydrogen);
            databaseThread.run();

            databaseAccess.updateSpaceport(Integer.valueOf((String) selectSpaceportData.get(0)),null,true);

            return true;

        }

        return false;

    }

    public boolean sellSpaceport(ArrayList selectSpaceportData) {

        int hydrogen = Integer.valueOf((String) selectPlanetData.get(6)) + Integer.valueOf((String) selectSpaceportData.get(4)) / 2;

        if(databaseAccess.selectCountSpaceport(true) > 1) {

            databaseAccess.updateCreditPlanet(1, Integer.valueOf((String) selectPlanetData.get(5)), hydrogen);
            databaseThread.run();

            databaseAccess.updateSpaceport(Integer.valueOf((String) selectSpaceportData.get(0)),null,false);

            return true;

        }

        return false;

    }

    public boolean upgradeSpaceport(ArrayList selectSpaceportData) {

        int hydrogen = Integer.valueOf((String) selectPlanetData.get(6)) - (Integer.valueOf((String) selectSpaceportData.get(4)) / 2) * Integer.valueOf((String) selectSpaceportData.get(3));

        if(hydrogen >= 0) {

            databaseAccess.updateCreditPlanet(1, Integer.valueOf((String) selectPlanetData.get(5)), hydrogen);
            databaseThread.run();

            databaseAccess.updateSpaceport(Integer.valueOf((String) selectSpaceportData.get(0)), Integer.valueOf((String) selectSpaceportData.get(3)) + 1);

            return true;

        }

        return false;

    }

    public boolean buyStarship(ArrayList selectStarshipData) {

        int credit = Integer.valueOf((String) selectPlanetData.get(5)) - Integer.valueOf((String) selectStarshipData.get(5));

        if(credit >= 0) {

            databaseAccess.updateCreditPlanet(1, credit, Integer.valueOf((String) selectPlanetData.get(6)));
            databaseThread.run();

            databaseAccess.updateStarship(Integer.valueOf((String) selectStarshipData.get(0)),null, true);

            return true;

        }

        return false;

    }

    public boolean sellStarship(ArrayList selectStarshipData) {

        int credit = Integer.valueOf((String) selectPlanetData.get(5)) + Integer.valueOf((String) selectStarshipData.get(5)) / 2;

        if(databaseAccess.selectCountStarship(true) > 1) {

            databaseAccess.updateCreditPlanet(1, credit, Integer.valueOf((String) selectPlanetData.get(6)));
            databaseThread.run();

            databaseAccess.updateStarship(Integer.valueOf((String) selectStarshipData.get(0)),null,false);

            return true;

        }

        return false;

    }

    public boolean upgradeStarship(ArrayList selectStarshipData) {

        int credit = Integer.valueOf((String) selectPlanetData.get(5)) - (Integer.valueOf((String) selectStarshipData.get(5)) / 2) * Integer.valueOf((String) selectStarshipData.get(4));

        if(credit >= 0) {

            databaseAccess.updateCreditPlanet(1, credit, Integer.valueOf((String) selectStarshipData.get(6)));
            databaseThread.run();

            databaseAccess.updateStarship(Integer.valueOf((String) selectStarshipData.get(0)), Integer.valueOf((String) selectStarshipData.get(4)) + 1);

            return true;

        }

        return false;

    }

}
