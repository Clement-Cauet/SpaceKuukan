package com.spacekuukan.application.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.spacekuukan.application.R;
import com.spacekuukan.application.databinding.FragmentSpaceBinding;
import com.spacekuukan.application.db.DatabaseAccess;
import com.spacekuukan.application.function.InstanceFunction;
import com.spacekuukan.application.function.Planet;

import java.util.ArrayList;

public class FragmentSpace extends Fragment {

    private FragmentSpaceBinding binding;

    private InstanceFunction instanceFunction;
    private DatabaseAccess databaseAccess;

    private RelativeLayout space_layout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSpaceBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        this.instanceFunction = InstanceFunction.getInstance();
        this.databaseAccess = DatabaseAccess.getInstance(getContext());

        space_layout = view.findViewById(R.id.space_layout);

        createSpaceLayout();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void createSpaceLayout() {

        ArrayList planetList = instanceFunction.getPlanetList();
        for(int i = 0; i < planetList.size(); i++) {
            Planet planet = (Planet) planetList.get(i);

            ImageView image_planet = new ImageView(getContext());
            image_planet.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            image_planet.setImageResource(planet.getImagePlanetId()[i]);
            RelativeLayout.LayoutParams params_image_planet_layout;
            params_image_planet_layout = (RelativeLayout.LayoutParams) image_planet.getLayoutParams();
            params_image_planet_layout.topMargin = planet.getLatitude();
            params_image_planet_layout.leftMargin = planet.getLongitude();
            space_layout.addView(image_planet);

            image_planet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (planet.getId() != 1) {
                        PopupPlanet popupPlanet = new PopupPlanet(getContext(), planet.getId());
                        popupPlanet.popupPlanetInfo();
                    } else {
                        instanceFunction.getNavController().navigate(R.id.navigation_base);
                    }

                }
            });

        }

    }

}