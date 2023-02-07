package com.spacekuukan.application.function;

import android.content.Context;

import com.spacekuukan.application.db.DatabaseAccess;
import com.spacekuukan.application.thread.DatabaseThread;

import java.util.ArrayList;

public class ManageSystem {

    private InstanceFunction instanceFunction;
    private DatabaseAccess databaseAccess;
    private DatabaseThread databaseThread;

    private Base base;

    public ManageSystem(Context context) {

        this.instanceFunction = InstanceFunction.getInstance();
        this.databaseAccess = DatabaseAccess.getInstance(context);
        this.databaseThread = DatabaseThread.getInstance(databaseAccess, null, null);

        ArrayList selectPlanetData = databaseAccess.selectPlanetData(1);
        this.base = Base.getInstance(Integer.valueOf((String) selectPlanetData.get(0)), selectPlanetData.get(1).toString(), Integer.valueOf((String) selectPlanetData.get(2)), Integer.valueOf((String) selectPlanetData.get(3)), Integer.valueOf((String) selectPlanetData.get(4)), Integer.valueOf((String) selectPlanetData.get(5)));

    }

    public boolean buySpaceport(int spaceport_id) {

        Spaceport spaceport = (Spaceport) instanceFunction.getSpaceportList().get(spaceport_id - 1);

        int hydrogen = base.getHydrogen() - spaceport.getCost();

        if(hydrogen >= 0) {

            base.setHydrogen(hydrogen);
            spaceport.setBuy(1);
            databaseAccess.updateCreditPlanet(base.getId(), base.getCredit(), hydrogen);
            databaseAccess.updateSpaceport(spaceport.getId(),null,1,true);
            databaseThread.run();

            return true;

        }

        return false;

    }

    public boolean sellSpaceport(int spaceport_id) {

        Spaceport spaceport = (Spaceport) instanceFunction.getSpaceportList().get(spaceport_id - 1);

        int hydrogen = base.getHydrogen() + spaceport.getCost() / 2;

        if(databaseAccess.selectCountSpaceport(true) > 1) {

            base.setHydrogen(hydrogen);
            spaceport.setLevel(1);
            spaceport.setBuy(0);
            databaseAccess.updateCreditPlanet(base.getId(), base.getCredit(), hydrogen);
            databaseAccess.updateSpaceport(spaceport.getId(),null,1,false);
            databaseThread.run();

            return true;

        }

        return false;

    }

    public boolean upgradeSpaceport(int spaceport_id) {

        Spaceport spaceport = (Spaceport) instanceFunction.getSpaceportList().get(spaceport_id - 1);

        int hydrogen = base.getHydrogen() - (spaceport.getCost() / 2) * spaceport.getLevel();

        if(hydrogen >= 0) {

            base.setHydrogen(hydrogen);
            spaceport.setLevel(spaceport.getLevel() + 1);
            databaseAccess.updateCreditPlanet(base.getId(), base.getCredit(), hydrogen);
            databaseAccess.updateSpaceport(spaceport.getId(), spaceport.getLevel());
            databaseThread.run();

            return true;

        }

        return false;

    }

    public boolean buyStarship(int starship_id, int port_id) {

        Starship starship = (Starship) instanceFunction.getStarshipList().get(starship_id - 1);

        int credit = base.getCredit() - starship.getCost();

        if(credit >= 0) {

            base.setCredit(credit);
            starship.setLevel(1);
            starship.setBuy(1);
            starship.setPlanet(1);
            starship.setPort(port_id);
            databaseAccess.updateCreditPlanet(base.getId(), credit, base.getHydrogen());
            databaseAccess.updateStarshipBuy(starship.getId(),null, 1,true, 1, port_id);
            databaseThread.run();

            return true;

        }

        return false;

    }

    public boolean sellStarship(int starshipId) {

        Starship starship = (Starship) instanceFunction.getStarshipList().get(starshipId - 1);

        int credit = base.getCredit() + starship.getCost() / 2;

        if(databaseAccess.selectCountStarship(true) > 1) {

            base.setCredit(credit);
            starship.setLevel(1);
            starship.setBuy(0);
            starship.setPlanet(-1);
            starship.setPort(-1);
            databaseAccess.updateCreditPlanet(base.getId(), credit, base.getHydrogen());
            databaseAccess.updateStarshipBuy(starship.getId(), null,1,false, -1, -1);
            databaseThread.run();

            return true;

        }

        return false;

    }

    public boolean upgradeStarship(int starshipId) {

        Starship starship = (Starship) instanceFunction.getStarshipList().get(starshipId - 1);

        int credit = base.getCredit() - (starship.getCost() / 2) * starship.getLevel();

        if(credit >= 0) {

            base.setCredit(credit);
            starship.setLevel(starship.getLevel() + 1);
            databaseAccess.updateCreditPlanet(base.getId(), credit, base.getHydrogen());
            databaseAccess.updateStarshipLevel(starship.getId(), starship.getLevel());
            databaseThread.run();

            return true;

        }

        return false;

    }

}
