package com.spacekuukan.application.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.ArrayMap;

import java.util.ArrayList;

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
    public ArrayList selectPlanet() {

        String query = "select * from planet";

        cursor = db.rawQuery(query, new String[]{});

        ArrayList<ArrayList> result = new ArrayList<>();

        while(cursor.moveToNext()) {
            ArrayList<String> list = new ArrayList<>();
            for(int i = 0; i < 6; i++){
                list.add(cursor.getString(i));
            }
            result.add(list);
        }

        return result;

    }

    //Select Planet data in the database
    public ArrayList selectPlanetData(int id) {

        String query = "select * from planet where id = "+ id;

        cursor = db.rawQuery(query, new String[]{});

        ArrayList<String> result = new ArrayList<>();

        while(cursor.moveToNext()) {
            for(int i = 0; i < 7; i++){
                result.add(cursor.getString(i));
            }
        }

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

    //Update data Starship in the database
    public void updateCreditPlanet(int id, int credit) {

        String[] key = { "credit" };

        int[] value = { credit };

        String query = "id = "+ id;
        ContentValues values = new ContentValues();

        for(int i = 0; i < key.length; i++) {
            values.put(key[i], value[i]);
        }

        db.update("planet", values, query, null);

    }

    //Delete a Planet in the database
    public void deletePlanet(int id) {

        String query = "delete from planet where id = "+ id;

        db.execSQL(query);

    }

    //Select the list of Starship in database
    public ArrayList selectStarship() {

        String query = "select * from starship";

        cursor = db.rawQuery(query, new String[]{});

        ArrayList<ArrayList> result = new ArrayList<>();

        while(cursor.moveToNext()) {
            ArrayList<String> list = new ArrayList<>();
            for(int i = 0; i < 12; i++){
                list.add(cursor.getString(i));
            }
            result.add(list);
        }

        return result;

    }

    //Select the list of Shop Starship in database
    public ArrayList selectStarship(int type, boolean shop) {

        String query = "select * from starship where type = " + type + " and buy = " + shop;

        cursor = db.rawQuery(query, new String[]{});

        ArrayList<ArrayList> result = new ArrayList<>();

        while(cursor.moveToNext()) {
            ArrayList<String> list = new ArrayList<>();
            for(int i = 0; i < 12; i++){
                list.add(cursor.getString(i));
            }
            result.add(list);
        }

        return result;

    }

    //Select the list of Starship in database
    public int selectCountStarship(boolean buy) {

        String query = "select count(*) from starship where buy = " + buy;

        cursor = db.rawQuery(query, new String[]{});

        cursor.moveToNext();
        return cursor.getInt(0);

    }

    //Select Starship data in the database
    public ArrayList selectStarshipData(int id) {

        String query = "select * from starship where id = "+ id;

        cursor = db.rawQuery(query, new String[]{});

        ArrayList<String> result = new ArrayList<>();

        if(cursor.moveToNext()) {
            for(int i = 0; i < 12; i++) {
                result.add(cursor.getString(i));
            }
        }

        return result;

    }

    //Insert a new Starship in the database
    public void insertStarship(String name, String level, String speed, String cost, String power, String mining_rate, String latitude, String longitude) {

        String[] key = { "name", "level", "speed", "cost", "power", "mining_rate", "latitude", "longitude" };

        String[] value = { name, level, speed, cost, power, mining_rate, latitude, longitude };

        ContentValues values = new ContentValues();

        for(int i = 0; i < key.length; i++) {
            values.put(key[i], value[i]);
        }

        try {
            db.insert("starship", null, values);
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    //Update data Starship in the database
    public void updateStarship(int id, String nickname) {

        String[] key = { "nickname" };

        String[] value = { nickname };

        String query = "id = "+ id;
        ContentValues values = new ContentValues();

        for(int i = 0; i < key.length; i++) {
            values.put(key[i], value[i]);
        }

        db.update("starship", values, query, null);

    }

    //Update data Starship in the database
    public void updateStarship(int id, int level) {

        String[] key = { "level" };

        int[] value = { level };

        String query = "id = "+ id;
        ContentValues values = new ContentValues();

        for(int i = 0; i < key.length; i++) {
            values.put(key[i], value[i]);
        }

        db.update("starship", values, query, null);

    }

    //Update data Starship in the database
    public void updateStarship(int id, boolean buy) {

        String[] key = { "buy" };

        boolean[] value = { buy };

        String query = "id = "+ id;
        ContentValues values = new ContentValues();

        for(int i = 0; i < key.length; i++) {
            values.put(key[i], value[i]);
        }

        db.update("starship", values, query, null);

    }

    //Update data Starship in the database
    public void updateStarship(int id, String nickname, boolean buy) {

        String query = "id = "+ id;

        ContentValues values = new ContentValues();
        values.put("nickname", nickname);
        values.put("buy", buy);

        db.update("starship", values, query, null);

    }

    //Update data Starship in the database
    public void updateStarship(int id, String name, String level, String speed, String cost, String power, String mining_rate, String latitude, String longitude, String buy) {

        String[] key = { "name", "level", "speed", "cost", "power", "mining_rate", "latitude", "longitude", "buy" };

        String[] value = { name, level, speed, cost, power, mining_rate, latitude, longitude, buy };

        String query = "id = "+ id;
        ContentValues values = new ContentValues();

        for(int i = 0; i < key.length; i++) {
            values.put(key[i], value[i]);
        }

        db.update("starship", values, query, null);

    }

    //Delete a Starship in the database
    public void deleteStarship(int id) {

        String query = "delete from starship where id = "+ id;

        db.execSQL(query);

    }

}
