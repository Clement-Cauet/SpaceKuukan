package com.spacekuukan.application.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String  DATABASE_NAME       = "forgeme.db";
    public static final int     DATABASE_VERSION    = 1;


    //Constructor DatabaseOpenHelper class
    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create the database if she don't exist
    @Override
    public void onCreate(SQLiteDatabase db) {
        for(int i = 0; i < getCreateTable().length; i++) {
            String string = getCreateTable()[i];
            db.execSQL(string);
        }
        System.out.println("Database: Create");
    }

    //Upgrade the database if the number of version has changed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(int i = 0; i < getTableName().length; i++) {
            String string = getTableName()[i];
            db.execSQL("DROP TABLE IF EXISTS " + string);
        }
        System.out.println("Database: Update");

        onCreate(db);
    }

    //Return table name of the database
    public String[] getTableName() {

        String string[] = { "planet", "starship" };

        return string;
    }

    //Create all table for the database
    public String[] getCreateTable() {

        String string[] = { getCreatePlanetTable(), getCreateStarshipTable() };

        return string;
    }

    //Request to create the "planet" table
    public String getCreatePlanetTable() {
        return
                "CREATE TABLE 'planet' (" +
                    "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "'name' varchar(30) NOT NULL," +
                    "'power' int(11) NOT NULL," +
                    "'resource' int(11)," +
                    "'latitude' int(11)," +
                    "'longitude' int(11)," +
                ");";
    }

    //Request to create the "starship" table
    public String getCreateStarshipTable() {
        return
                "CREATE TABLE 'starship' (" +
                        "'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "'name' varchar(30) NOT NULL," +
                        "'level' int(11) NOT NULL," +
                        "'speed' int(11) NOT NULL," +
                        "'cost' int(11) NOT NULL," +
                        "'power' int(11)," +
                        "'mining_rate' int(11)," +
                        "'latitude' int(11)," +
                        "'longitude' int(11)," +
                        ");";
    }

}
