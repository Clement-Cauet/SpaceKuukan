package com.spacekuukan.application.thread;

import android.widget.TextView;

import com.spacekuukan.application.db.DatabaseAccess;
import com.spacekuukan.application.function.Base;

import java.util.ArrayList;

public class DatabaseThread extends Thread {

    private static DatabaseThread instance;

    private DatabaseAccess databaseAccess;

    private Base base;

    private TextView credit_value, hydrogen_value;

    //Return instance DatabaseThread class
    public static DatabaseThread getInstance(DatabaseAccess databaseAccess, TextView credit_value, TextView hydrogen_value) {
        if(instance == null)
            instance = new DatabaseThread(databaseAccess, credit_value, hydrogen_value);
        return instance;
    }

    public DatabaseThread(DatabaseAccess databaseAccess, TextView credit_value, TextView hydrogen_value) {
        this.databaseAccess = databaseAccess;
        this.credit_value = credit_value;
        this.hydrogen_value = hydrogen_value;

        ArrayList selectPlanetData = databaseAccess.selectPlanetData(1);
        this.base = Base.getInstance(Integer.valueOf((String) selectPlanetData.get(0)), selectPlanetData.get(1).toString(), Integer.valueOf((String) selectPlanetData.get(2)), Integer.valueOf((String) selectPlanetData.get(3)), Integer.valueOf((String) selectPlanetData.get(4)), Integer.valueOf((String) selectPlanetData.get(5)));
    }

    @Override
    public void run() {

        credit_value.setText(Integer.toString(base.getCredit()));
        hydrogen_value.setText(Integer.toString(base.getHydrogen()));

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
