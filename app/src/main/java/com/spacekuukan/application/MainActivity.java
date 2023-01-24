package com.spacekuukan.application;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.spacekuukan.application.databinding.ActivityMainBinding;
import com.spacekuukan.application.db.DatabaseAccess;
import com.spacekuukan.application.db.DatabaseThread;
import com.spacekuukan.application.function.BackgroundMusicThread;
import com.spacekuukan.application.function.InstanceFunction;

public class MainActivity extends AppCompatActivity {

    private InstanceFunction instanceFunction;
    private DatabaseAccess databaseAccess;
    private DatabaseThread databaseThread;

    private ActivityMainBinding binding;

    private BackgroundMusicThread backgroundMusicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.instanceFunction = InstanceFunction.getInstance();

        this.databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        databaseThread = DatabaseThread.getInstance(databaseAccess, findViewById(R.id.credit_value), findViewById(R.id.hydrogen_value));
        databaseThread.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_space, R.id.navigation_base).build();
        instanceFunction.setNavController(Navigation.findNavController(this, R.id.nav_host_fragment_activity_main));
        NavigationUI.setupActionBarWithNavController(this, instanceFunction.getNavController(), appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, instanceFunction.getNavController());
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundMusicService = new BackgroundMusicThread(getApplicationContext());
        backgroundMusicService.start();
    }

}