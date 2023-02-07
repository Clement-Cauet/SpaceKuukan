package com.spacekuukan.application.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.spacekuukan.application.R;
import com.spacekuukan.application.db.DatabaseAccess;
import com.spacekuukan.application.thread.DatabaseThread;
import com.spacekuukan.application.function.InstanceFunction;
import com.spacekuukan.application.function.Planet;
import com.spacekuukan.application.function.Starship;
import com.spacekuukan.application.thread.StarshipThread;

import java.util.ArrayList;

public class PopupPlanet extends Dialog {

    private InstanceFunction instanceFunction;
    private DatabaseAccess databaseAccess;
    private DatabaseThread databaseThread;

    private LinearLayout popup_planet;

    private int id;

    public PopupPlanet(@NonNull Context context, int id) {
        super(context);

        setContentView(R.layout.popup_planet);

        this.instanceFunction = InstanceFunction.getInstance();
        this.databaseAccess = DatabaseAccess.getInstance(getContext());
        this.databaseThread = DatabaseThread.getInstance(databaseAccess, null, null);

        this.popup_planet = findViewById(R.id.popup_planet);

        this.id = id;

    }

    public void popupPlanetInfo() {

        Planet planet = (Planet) instanceFunction.getPlanetList().get(id - 1);

        LinearLayout popup_planet_layout = new LinearLayout(getContext());
        popup_planet_layout.setLayoutParams(new LinearLayout.LayoutParams(800, LinearLayout.LayoutParams.WRAP_CONTENT));
        popup_planet_layout.setOrientation(LinearLayout.VERTICAL);
        popup_planet_layout.setPadding(20, 20, 20, 20);
        popup_planet.addView(popup_planet_layout);

        /* - Title Popup - */
        LinearLayout popup_planet_title_layout = new LinearLayout(getContext());
        popup_planet_title_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        popup_planet_title_layout.setOrientation(LinearLayout.VERTICAL);
        popup_planet_layout.addView(popup_planet_title_layout);

        TextView popup_planet_title_name = new TextView(getContext());
        popup_planet_title_name.setText(planet.getName());
        popup_planet_title_name.setTextSize(22);
        popup_planet_title_name.setTypeface(popup_planet_title_name.getResources().getFont(R.font.quadrangle));
        popup_planet_title_layout.addView(popup_planet_title_name);

        /* - Resource Popup - */
        LinearLayout popup_planet_resource_layout = new LinearLayout(getContext());
        popup_planet_resource_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        popup_planet_resource_layout.setOrientation(LinearLayout.HORIZONTAL);
        popup_planet_resource_layout.setWeightSum(2);
        popup_planet_layout.addView(popup_planet_resource_layout);

        LinearLayout popup_planet_resource_credit_layout = new LinearLayout(getContext());
        popup_planet_resource_credit_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        popup_planet_resource_credit_layout.setOrientation(LinearLayout.HORIZONTAL);
        popup_planet_resource_credit_layout.setGravity(Gravity.CENTER);
        popup_planet_resource_layout.addView(popup_planet_resource_credit_layout);

        ImageView popup_planet_resource_credit_image = new ImageView(getContext());
        popup_planet_resource_credit_image.setLayoutParams(new LinearLayout.LayoutParams(50, LinearLayout.LayoutParams.WRAP_CONTENT));
        popup_planet_resource_credit_image.setImageResource(R.mipmap.credit);
        popup_planet_resource_credit_layout.addView(popup_planet_resource_credit_image);

        TextView popup_planet_resource_credit_text = new TextView(getContext());
        popup_planet_resource_credit_text.setTextSize(14);
        popup_planet_resource_credit_text.setTypeface(popup_planet_resource_credit_text.getResources().getFont(R.font.quadrangle));
        popup_planet_resource_credit_layout.addView(popup_planet_resource_credit_text);

        LinearLayout popup_planet_resource_hydrogen_layout = new LinearLayout(getContext());
        popup_planet_resource_hydrogen_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        popup_planet_resource_hydrogen_layout.setOrientation(LinearLayout.HORIZONTAL);
        popup_planet_resource_hydrogen_layout.setGravity(Gravity.CENTER);
        popup_planet_resource_layout.addView(popup_planet_resource_hydrogen_layout);

        ImageView popup_planet_resource_hydrogen_image = new ImageView(getContext());
        popup_planet_resource_hydrogen_image.setLayoutParams(new LinearLayout.LayoutParams(50, LinearLayout.LayoutParams.WRAP_CONTENT));
        popup_planet_resource_hydrogen_image.setImageResource(R.mipmap.hydrogen);
        popup_planet_resource_hydrogen_layout.addView(popup_planet_resource_hydrogen_image);

        TextView popup_planet_resource_hydrogen_text = new TextView(getContext());
        popup_planet_resource_hydrogen_text.setTextSize(14);
        popup_planet_resource_hydrogen_text.setTypeface(popup_planet_resource_hydrogen_text.getResources().getFont(R.font.quadrangle));
        popup_planet_resource_hydrogen_layout.addView(popup_planet_resource_hydrogen_text);

        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                popup_planet_resource_credit_text.setText(String.valueOf(planet.getCredit()));
                popup_planet_resource_hydrogen_text.setText(String.valueOf(planet.getHydrogen()));
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);

        /* - Add Popup - */
        LinearLayout popup_planet_add_layout = new LinearLayout(getContext());
        popup_planet_add_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        popup_planet_add_layout.setOrientation(LinearLayout.VERTICAL);
        popup_planet_add_layout.setGravity(Gravity.CENTER);
        popup_planet_layout.addView(popup_planet_add_layout);

        LinearLayout popup_planet_add_button_layout = new LinearLayout(getContext());
        popup_planet_add_button_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        popup_planet_add_button_layout.setOrientation(LinearLayout.HORIZONTAL);
        popup_planet_add_button_layout.setGravity(Gravity.CENTER);
        popup_planet_add_layout.addView(popup_planet_add_button_layout);

        TextView popup_planet_add_button_text = new TextView(getContext());
        popup_planet_add_button_text.setTextSize(14);
        popup_planet_add_button_text.setTypeface(popup_planet_add_button_text.getResources().getFont(R.font.quadrangle));
        popup_planet_add_button_text.setText("Add Travel");
        popup_planet_add_button_layout.addView(popup_planet_add_button_text);

        popup_planet_add_button_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popup_planet_add_button_layout.setClickable(false);

                FrameLayout frameLayout = new FrameLayout(getContext());
                frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
                frameLayout.setForeground(new ColorDrawable(frameLayout.getResources().getColor(R.color.transparent)));
                frameLayout.setForegroundGravity(Gravity.BOTTOM);
                ShapeDrawable foreground = new ShapeDrawable();
                foreground.setBounds(0, 0, frameLayout.getWidth(), 200);
                frameLayout.setForeground(foreground);
                popup_planet_add_layout.addView(frameLayout);

                ScrollView scrollView = new ScrollView(getContext());
                scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                frameLayout.addView(scrollView);

                LinearLayout scrollView_content_layout = new LinearLayout(getContext());
                scrollView_content_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                scrollView_content_layout.setOrientation(LinearLayout.VERTICAL);
                scrollView.addView(scrollView_content_layout);

                ArrayList starshipList = instanceFunction.getStarshipList();
                for(int i = 0; i < starshipList.size(); i++) {

                    Starship starship = (Starship) starshipList.get(i);

                    if (starship.getBuy() == 1 && starship.getPlanet() == 1) {
                        LinearLayout scrollView_item_layout = new LinearLayout(getContext());
                        scrollView_item_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        scrollView_item_layout.setOrientation(LinearLayout.HORIZONTAL);
                        scrollView_content_layout.addView(scrollView_item_layout);

                        ImageView scrollView_image = new ImageView(getContext());
                        scrollView_image.setLayoutParams(new LinearLayout.LayoutParams(60, 60));
                        scrollView_image.setImageResource(starship.getImageStarshipId()[starship.getId() - 1]);
                        scrollView_item_layout.addView(scrollView_image);

                        TextView scrollView_title = new TextView(getContext());
                        scrollView_title.setTextSize(12);
                        scrollView_title.setTypeface(scrollView_title.getResources().getFont(R.font.quadrangle));
                        if(starship.getNickname() != null)
                            scrollView_title.setText(starship.getNickname());
                        else
                            scrollView_title.setText(starship.getName());
                        scrollView_item_layout.addView(scrollView_title);

                        scrollView_item_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                starship.setPlanet(planet.getId());
                                databaseAccess.updateStarshipPlanet(starship.getId(), planet.getId());

                                StarshipThread starshipThread = new StarshipThread(databaseAccess, databaseThread);
                                starshipThread.run();

                                scrollView.setVisibility(View.GONE);
                                popup_planet_add_button_layout.setClickable(true);

                                popupPlanetInfoContent(popup_planet_layout, planet, starship);
                            }
                        });
                    }
                }

            }
        });

        ArrayList starshipList = instanceFunction.getStarshipList();
        for(int i = 0; i < starshipList.size(); i++) {

            Starship starship = (Starship) starshipList.get(i);

            if (starship.getPlanet() == planet.getId()) {
                popupPlanetInfoContent(popup_planet_layout, planet, starship);
            }

        }

        show();

    }

    private void popupPlanetInfoContent(LinearLayout popup_planet_layout, Planet planet, Starship starship) {
        LinearLayout popup_planet_content_layout = new LinearLayout(getContext());
        popup_planet_content_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        popup_planet_content_layout.setOrientation(LinearLayout.VERTICAL);
        popup_planet_layout.addView(popup_planet_content_layout);

        LinearLayout popup_planet_item_layout = new LinearLayout(getContext());
        popup_planet_item_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        popup_planet_item_layout.setOrientation(LinearLayout.VERTICAL);
        popup_planet_content_layout.addView(popup_planet_item_layout);

        /* - Title Item - */
        LinearLayout popup_planet_item_title_layout = new LinearLayout(getContext());
        popup_planet_item_title_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        popup_planet_item_title_layout.setOrientation(LinearLayout.HORIZONTAL);
        popup_planet_item_layout.addView(popup_planet_item_title_layout);

        TextView popup_planet_item_info_title_text = new TextView(getContext());
        popup_planet_item_info_title_text.setTextSize(14);
        popup_planet_item_info_title_text.setTypeface(popup_planet_item_info_title_text.getResources().getFont(R.font.quadrangle));
        if(starship.getNickname() != null)
            popup_planet_item_info_title_text.setText(starship.getNickname());
        else
            popup_planet_item_info_title_text.setText(starship.getName());
        popup_planet_item_title_layout.addView(popup_planet_item_info_title_text);

        /* - Information Item - */
        LinearLayout popup_planet_item_info_layout = new LinearLayout(getContext());
        popup_planet_item_info_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        popup_planet_item_info_layout.setOrientation(LinearLayout.HORIZONTAL);
        popup_planet_item_info_layout.setWeightSum(3);
        popup_planet_item_layout.addView(popup_planet_item_info_layout);

        LinearLayout popup_planet_item_info_timer_layout = new LinearLayout(getContext());
        popup_planet_item_info_timer_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        popup_planet_item_info_timer_layout.setOrientation(LinearLayout.HORIZONTAL);
        popup_planet_item_info_timer_layout.setGravity(Gravity.RIGHT);
        popup_planet_item_info_layout.addView(popup_planet_item_info_timer_layout);

        TextView popup_planet_item_info_time_text = new TextView(getContext());
        popup_planet_item_info_time_text.setTextSize(10);
        popup_planet_item_info_time_text.setTypeface(popup_planet_item_info_title_text.getResources().getFont(R.font.quadrangle));
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                popup_planet_item_info_time_text.setText(starship.getTime() + " s");
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);
        popup_planet_item_info_timer_layout.addView(popup_planet_item_info_time_text);

        LinearLayout popup_planet_item_info_return_layout = new LinearLayout(getContext());
        popup_planet_item_info_return_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2));
        popup_planet_item_info_return_layout.setOrientation(LinearLayout.HORIZONTAL);
        popup_planet_item_info_return_layout.setGravity(Gravity.RIGHT);
        popup_planet_item_info_layout.addView(popup_planet_item_info_return_layout);

        TextView popup_planet_item_info_return_text = new TextView(getContext());
        popup_planet_item_info_return_text.setTextSize(10);
        popup_planet_item_info_return_text.setTypeface(popup_planet_item_info_title_text.getResources().getFont(R.font.quadrangle));
        popup_planet_item_info_return_text.setTextColor(popup_planet_item_info_return_text.getResources().getColor(R.color.purple_500));
        popup_planet_item_info_return_text.setText(R.string.return_button);
        popup_planet_item_info_return_layout.addView(popup_planet_item_info_return_text);

        /* - Travel Item - */
        LinearLayout popup_planet_item_travel_layout = new LinearLayout(getContext());
        popup_planet_item_travel_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        popup_planet_item_travel_layout.setOrientation(LinearLayout.HORIZONTAL);
        popup_planet_item_travel_layout.setWeightSum(12);
        popup_planet_item_layout.addView(popup_planet_item_travel_layout);

        LinearLayout base_layout = new LinearLayout(getContext());
        base_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3));
        base_layout.setOrientation(LinearLayout.VERTICAL);
        popup_planet_item_travel_layout.addView(base_layout);

        ImageView base_image = new ImageView(getContext());
        base_image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        base_image.setImageResource(planet.getImagePlanetId()[0]);
        base_layout.addView(base_image);

        LinearLayout starship_layout = new LinearLayout(getContext());
        starship_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 6));
        starship_layout.setOrientation(LinearLayout.VERTICAL);
        popup_planet_item_travel_layout.addView(starship_layout);

        ImageView starship_image = new ImageView(getContext());
        starship_image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        starship_image.setImageResource(starship.getImageStarshipId()[starship.getId() - 1]);
        starship_layout.addView(starship_image);

        LinearLayout planet_layout = new LinearLayout(getContext());
        planet_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3));
        planet_layout.setOrientation(LinearLayout.VERTICAL);
        popup_planet_item_travel_layout.addView(planet_layout);

        ImageView planet_image = new ImageView(getContext());
        planet_image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        planet_image.setImageResource(planet.getImagePlanetId()[id - 1]);
        planet_layout.addView(planet_image);

        popup_planet_item_info_return_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starship.setPlanet(1);
                databaseAccess.updateStarshipPlanet(starship.getId(), 1);
                starship.setCountDownTimer(null);
                popup_planet_content_layout.setVisibility(View.GONE);
            }
        });

    }

}
