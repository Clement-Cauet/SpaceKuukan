package com.spacekuukan.application.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor cursor = null;

    //Return instance DatabaseAccess class
    public static DatabaseAccess getInstance(Context context) {
        if(instance == null)
            instance = new DatabaseAccess(context);
        return instance;
    }

    //Constructor DatabaseAccess class
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    //Open the database
    public void open() {
        this.db = openHelper.getWritableDatabase();
        System.out.println("Database: Opened");
    }

    //Close the database
    public void close() {
        this.db.close();
        System.out.println("Database: Closed");
    }

    //Select the list of Planet in database
    public String[][] selectPlanet() {

        String query = "select * from planet";

        cursor = db.rawQuery(query, new String[]{});

        String[][] result = new String[6][100];

        for(int j = 0; cursor.moveToNext(); j++) {
            for(int i = 0; i < 6; i++) {
                result[i][j] = cursor.getString(i);
            }
        }

        return result;

    }

    //Select Planet data in the database
    public String[] selectPlanetData(int id) {

        String query = "select * from planet where id = "+ id;

        cursor = db.rawQuery(query, new String[]{});

        String[] result = new String[6];
        cursor.moveToNext();
        result[0] = cursor.getString(0);
        result[1] = cursor.getString(1);
        result[2] = cursor.getString(2);
        result[3] = cursor.getString(3);
        result[4] = cursor.getString(4);
        result[5] = cursor.getString(5);

        return result;

    }

    //Insert a new Planet in the database
    public void insertPlanet(String name, String power, String resource, String latitude, String longitude) {

        String[] insert_key = { "name", "power", "resource", "latitude", "longitude" };

        String[] insert_value = { name, power, resource, latitude, longitude };

        ContentValues values = new ContentValues();

        for(int i = 0; i < 5; i++) {
            values.put(insert_key[i], insert_value[i]);
        }

        try {
            db.insert("planet", null, values);
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    //Update data Planet in the database
    public void updatePlanet(int id, String name, String power, String resource, String latitude, String longitude) {

        String[] insert_key = { "name", "power", "resource", "latitude", "longitude" };

        String[] update_value = { name, power, resource, latitude, longitude };

        String query = "id = "+ id;
        ContentValues values = new ContentValues();

        for(int i = 0; i < 22; i++) {
            values.put(insert_key[i], update_value[i]);
        }

        db.update("planet", values, query, null);

    }

    //Delete a Planet in the database
    public void deletePlanet(int id) {

        String query = "delete from planet where id = "+ id;

        db.execSQL(query);

    }

    //Select the list of Spaceship in database
    public String[][] selectSpaceship() {

        String query = "select * from spaceship";

        cursor = db.rawQuery(query, new String[]{});

        String[][] result = new String[9][100];

        for(int j = 0; cursor.moveToNext(); j++) {
            for(int i = 0; i < 6; i++) {
                result[i][j] = cursor.getString(i);
            }
        }

        return result;

    }

    //Select Spaceship data in the database
    public String[] selectSpaceshipData(int id) {

        String query = "select * from planet where id = "+ id;

        cursor = db.rawQuery(query, new String[]{});

        String[] result = new String[9];
        cursor.moveToNext();
        result[0] = cursor.getString(0);
        result[1] = cursor.getString(1);
        result[2] = cursor.getString(2);
        result[3] = cursor.getString(3);
        result[4] = cursor.getString(4);
        result[5] = cursor.getString(5);
        result[6] = cursor.getString(6);
        result[7] = cursor.getString(7);
        result[8] = cursor.getString(8);

        return result;

    }

    //Insert a new Spaceship in the database
    public void insertSpaceship(String name, String level, String speed, String cost, String power, String mining_rate, String latitude, String longitude) {

        String[] insert_key = { "name", "level", "speed", "cost", "power", "mining_rate", "latitude", "longitude" };

        String[] insert_value = { name, level, speed, cost, power, mining_rate, latitude, longitude };

        ContentValues values = new ContentValues();

        for(int i = 0; i < 8; i++) {
            values.put(insert_key[i], insert_value[i]);
        }

        try {
            db.insert("planet", null, values);
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    //Update data Spaceship in the database
    public void updateSpaceship(int id, String name, String level, String speed, String cost, String power, String mining_rate, String latitude, String longitude) {

        String[] insert_key = { "name", "level", "speed", "cost", "power", "mining_rate", "latitude", "longitude" };

        String[] update_value = { name, level, speed, cost, power, mining_rate, latitude, longitude };

        String query = "id = "+ id;
        ContentValues values = new ContentValues();

        for(int i = 0; i < 22; i++) {
            values.put(insert_key[i], update_value[i]);
        }

        db.update("planet", values, query, null);

    }

    //Delete a Spaceship in the database
    public void deleteSpaceship(int id) {

        String query = "delete from spaceship where id = "+ id;

        db.execSQL(query);

    }

}
