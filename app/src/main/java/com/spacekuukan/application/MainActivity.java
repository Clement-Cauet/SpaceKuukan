package com.spacekuukan.application;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.spacekuukan.application.databinding.ActivityMainBinding;
import com.spacekuukan.application.db.DatabaseAccess;
import com.spacekuukan.application.db.DatabaseThread;

public class MainActivity extends AppCompatActivity {

    private DatabaseAccess databaseAccess;
    private DatabaseThread databaseThread;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseThread = DatabaseThread.getInstance(databaseAccess, findViewById(R.id.credit_value), findViewById(R.id.hydrogen_value));
        databaseThread.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_space, R.id.navigation_base).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}