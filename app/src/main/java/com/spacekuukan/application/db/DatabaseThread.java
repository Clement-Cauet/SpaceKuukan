package com.spacekuukan.application.db;

import android.content.Context;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class DatabaseThread extends Thread {

    private static DatabaseThread instance;

    private DatabaseAccess databaseAccess;

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
    }

    @Override
    public void run() {
        ArrayList selectPlanetData = databaseAccess.selectPlanetData(1);

        credit_value.setText((CharSequence) selectPlanetData.get(5));
        hydrogen_value.setText((CharSequence) selectPlanetData.get(6));
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
