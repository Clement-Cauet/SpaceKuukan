package com.spacekuukan.application.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private Context context;

    public static final String  DATABASE_NAME       = "space_kuukan.db";
    public static final int     DATABASE_VERSION    = 1;


    //Constructor DatabaseOpenHelper class
    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //Create the database if she don't exist
    @Override
    public void onCreate(SQLiteDatabase db) {
        execSQLFile(db, "database/create.txt");
        System.out.println("Database: Create");
    }

    //Upgrade the database if the number of version has changed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("Database: Update");
    }

    private void execSQLFile(SQLiteDatabase db, String file) {
        String request = "", text = null;
        InputStream inputStream = null;
        Pattern pattern = Pattern.compile(";");
        Matcher matcher;
        try {
            inputStream = context.getAssets().open(file);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                try {
                    while ((text = bufferedReader.readLine()) != null) {
                        request += text;
                        matcher = pattern.matcher(request);
                        if(matcher.find()) {
                            db.execSQL(request);
                            request = "";
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
