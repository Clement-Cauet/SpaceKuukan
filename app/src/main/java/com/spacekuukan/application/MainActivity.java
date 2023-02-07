package com.spacekuukan.application;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.spacekuukan.application.databinding.ActivityMainBinding;
import com.spacekuukan.application.db.DatabaseAccess;
import com.spacekuukan.application.function.Spaceport;
import com.spacekuukan.application.thread.DatabaseThread;
import com.spacekuukan.application.function.BackgroundMusicThread;
import com.spacekuukan.application.function.Base;
import com.spacekuukan.application.function.InstanceFunction;
import com.spacekuukan.application.function.Planet;
import com.spacekuukan.application.function.Starship;
import com.spacekuukan.application.thread.StarshipThread;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private InstanceFunction instanceFunction;
    private DatabaseAccess databaseAccess;
    private DatabaseThread databaseThread;
    private StarshipThread starshipThread;

    private ActivityMainBinding binding;

    private BackgroundMusicThread backgroundMusicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.instanceFunction = InstanceFunction.getInstance();

        this.databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        /* - Creation Planet - */
        ArrayList planetList = new ArrayList<>();
        ArrayList selectPlanet = databaseAccess.selectPlanet();
        for (int i = 0; i < selectPlanet.size(); i++) {
            ArrayList selectPlanetData = databaseAccess.selectPlanetData(i + 1);
            if (i != 0) {
                Planet planet = new Planet(Integer.valueOf((String) selectPlanetData.get(0)), selectPlanetData.get(1).toString(), Integer.valueOf((String) selectPlanetData.get(2)), Integer.valueOf((String) selectPlanetData.get(3)), Integer.valueOf((String) selectPlanetData.get(4)), Integer.valueOf((String) selectPlanetData.get(5)));
                planetList.add(planet);
            } else {
                Base base = Base.getInstance(Integer.valueOf((String) selectPlanetData.get(0)), selectPlanetData.get(1).toString(), Integer.valueOf((String) selectPlanetData.get(2)), Integer.valueOf((String) selectPlanetData.get(3)), Integer.valueOf((String) selectPlanetData.get(4)), Integer.valueOf((String) selectPlanetData.get(5)));
                planetList.add(base);
            }
        }
        instanceFunction.setPlanetList(planetList);

        /* - Creation Spaceport - */
        ArrayList spaceportList = new ArrayList<>();
        ArrayList selectSpaceport = databaseAccess.selectSpaceport();
        for (int i = 0; i < selectSpaceport.size(); i++) {
            ArrayList spaceport = databaseAccess.selectSpaceportData(i + 1);
            spaceportList.add(new Spaceport(Integer.valueOf((String) spaceport.get(0)), (String) spaceport.get(1), (String) spaceport.get(2), Integer.valueOf((String) spaceport.get(3)), Integer.valueOf((String) spaceport.get(4)), Integer.valueOf((String) spaceport.get(5))));
        }
        instanceFunction.setSpaceportList(spaceportList);

        /* - Creation Starship - */
        ArrayList starshipList = new ArrayList<>();
        ArrayList selectStarship = databaseAccess.selectStarship();
        for (int i = 0; i < selectStarship.size(); i++) {
            ArrayList starship_data = databaseAccess.selectStarshipData(i + 1);
            Starship starship = new Starship(Integer.valueOf((String) starship_data.get(0)), (String) starship_data.get(1), (String) starship_data.get(2), Integer.valueOf((String) starship_data.get(3)), Integer.valueOf((String) starship_data.get(4)), Integer.valueOf((String) starship_data.get(5)), Integer.valueOf((String) starship_data.get(6)), Integer.valueOf((String) starship_data.get(7)), Integer.valueOf((String) starship_data.get(8)), Integer.valueOf((String) starship_data.get(9)));
            starshipList.add(starship);
            if(starship.getPort() != -1) {
                spaceportList = instanceFunction.getSpaceportList();
                Spaceport spaceport = (Spaceport) spaceportList.get(starship.getPort() - 1);
                spaceport.getStarshipStation().add(starship);
            }
        }
        instanceFunction.setStarshipList(starshipList);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            databaseThread = DatabaseThread.getInstance(databaseAccess, findViewById(R.id.credit_value), findViewById(R.id.hydrogen_value));
            databaseThread.start();

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        starshipThread = new StarshipThread(databaseAccess, databaseThread);
        starshipThread.run();

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

    @Override
    protected void onPause() {
        super.onPause();
        backgroundMusicService.stopMainTheme();
    }

}