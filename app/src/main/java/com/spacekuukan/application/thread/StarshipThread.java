package com.spacekuukan.application.thread;

import android.content.Context;
import android.os.CountDownTimer;

import com.spacekuukan.application.db.DatabaseAccess;
import com.spacekuukan.application.function.InstanceFunction;
import com.spacekuukan.application.function.Planet;
import com.spacekuukan.application.function.Starship;

import java.util.ArrayList;

public class StarshipThread {

    private InstanceFunction instanceFunction;
    private DatabaseAccess databaseAccess;
    private DatabaseThread databaseThread;

    private Planet base, planet;
    private Starship starship;

    private int tick;

    public StarshipThread(DatabaseAccess databaseAccess, DatabaseThread databaseThread) {

        this.instanceFunction = InstanceFunction.getInstance();
        this.databaseAccess = databaseAccess;
        this.databaseThread = databaseThread;

        this.base = (Planet) instanceFunction.getPlanetList().get(0);

    }

    public void run() {

        ArrayList planetList = instanceFunction.getPlanetList();
        ArrayList starshipList = instanceFunction.getStarshipList();
        for (int j = 0; j < planetList.size(); j++) {
            Planet planet = (Planet) planetList.get(j);

            if (planet.getId() != base.getId()) {

                for (int i = 0; i < starshipList.size(); i++) {
                    Starship starship = (Starship) starshipList.get(i);

                    if (starship.getPlanet() != base.getId()) {
                        int latitude = planet.getLatitude() - 200;
                        int longitude = planet.getLongitude() - 500;
                        float distance = (float) Math.sqrt(Math.pow(latitude, 2) + Math.pow(longitude, 2));

                        float time = distance / (starship.getSpeed());

                        if (starship.getPlanet() == planet.getId()) {

                            if (starship.getCountDownTimer() == null) {
                                tick = 0;
                                CountDownTimer countDownTimer = new CountDownTimer((long) (time * 1000), 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                        if (planet.getCredit() > 0 || planet.getHydrogen() > 0)
                                            starship.setTime((int) time - tick);

                                        tick++;

                                    }

                                    @Override
                                    public void onFinish() {

                                        int credit_planet = (planet.getCredit() > starship.getMining_rate()) ? planet.getCredit() - starship.getMining_rate() : planet.getCredit();
                                        int hydrogen_planet = (planet.getHydrogen() > starship.getMining_rate()) ? planet.getHydrogen() - starship.getMining_rate() : planet.getHydrogen();

                                        int credit_base;
                                        int hydrogen_base;

                                        if (credit_planet > 0 || hydrogen_planet > 0) {

                                            if (credit_planet < starship.getMining_rate() && credit_planet != 0) {

                                                databaseAccess.updateCreditPlanet(planet.getId(), 0, hydrogen_planet);

                                                planet.setCredit(0);
                                                planet.setHydrogen(hydrogen_planet);
                                                credit_base = base.getCredit() + credit_planet;
                                                hydrogen_base = base.getHydrogen() + starship.getMining_rate();

                                            } else if (hydrogen_planet < starship.getMining_rate() && hydrogen_planet != 0) {

                                                databaseAccess.updateCreditPlanet(planet.getId(), credit_planet, 0);

                                                planet.setCredit(credit_planet);
                                                planet.setHydrogen(0);
                                                credit_base = base.getCredit() + starship.getMining_rate();
                                                hydrogen_base = base.getHydrogen() + hydrogen_planet;

                                            } else {

                                                databaseAccess.updateCreditPlanet(planet.getId(), credit_planet, hydrogen_planet);

                                                planet.setCredit(credit_planet);
                                                planet.setHydrogen(hydrogen_planet);
                                                credit_base = base.getCredit() + starship.getMining_rate();
                                                hydrogen_base = base.getHydrogen() + starship.getMining_rate();

                                            }

                                            base.setCredit(credit_base);
                                            base.setHydrogen(hydrogen_base);
                                            databaseAccess.updateCreditPlanet(base.getId(), credit_base, hydrogen_base);

                                            databaseThread.run();
                                            starship.setCountDownTimer(null);

                                            if (planet.getCredit() > 0 || planet.getHydrogen() > 0) {
                                                run();
                                            }
                                        }
                                    }

                                };
                                countDownTimer.start();
                                starship.setCountDownTimer(countDownTimer);
                            }

                        }
                    }

                }

            }

        }

    }

}
