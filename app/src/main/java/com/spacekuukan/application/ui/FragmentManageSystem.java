package com.spacekuukan.application.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.spacekuukan.application.R;
import com.spacekuukan.application.databinding.FragmentManageSystemBinding;
import com.spacekuukan.application.db.DatabaseAccess;
import com.spacekuukan.application.function.Spaceport;
import com.spacekuukan.application.function.Starship;
import com.spacekuukan.application.thread.DatabaseThread;
import com.spacekuukan.application.function.InstanceFunction;
import com.spacekuukan.application.function.ManageSystem;

import java.util.ArrayList;

public class FragmentManageSystem extends Fragment {

    private InstanceFunction instanceFunction;
    private ManageSystem manageSystem;
    private DatabaseAccess databaseAccess;
    private DatabaseThread databaseThread;

    private Spaceport spaceport;
    private Starship starship;

    private FragmentManageSystemBinding binding;

    private LinearLayout manage_layout, menu_manage_layout;
    private TableLayout manage_stat_table_layout;
    private TextView station_title_value;

    private static final int SPACEPORT = 1, STARSHIP = 2;
    private int id, category;
    private boolean manage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentManageSystemBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        this.instanceFunction = InstanceFunction.getInstance();
        this.manageSystem = new ManageSystem(getContext());
        this.databaseAccess = DatabaseAccess.getInstance(getContext());
        this.databaseThread = DatabaseThread.getInstance(databaseAccess, null, null);

        this.id         = (int) instanceFunction.getManageSystemArgument().get(0);
        this.category   = (int) instanceFunction.getManageSystemArgument().get(1);
        this.manage     = (boolean) instanceFunction.getManageSystemArgument().get(2);

        manage_layout = view.findViewById(R.id.manage_layout);

        ArrayList selectPlanetData = databaseAccess.selectPlanetData(1);
        ArrayList selectStarshipData = databaseAccess.selectStarshipData(id);
        ArrayList selectSpaceportData = databaseAccess.selectSpaceportData(id);

        createMenuManageLayout();

        switch (category) {

            case SPACEPORT:

                spaceport = (Spaceport) instanceFunction.getSpaceportList().get(id - 1);
                manageSpaceport();

                if(manage) {

                    createLevelSpaceportLayout();
                    createStationSpaceportLayout();

                    Button sell_button = createSellButton();
                    sell_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (spaceport.getStarshipStation().size() == 0) {
                                if (manageSystem.sellSpaceport(spaceport.getId())) {
                                    instanceFunction.getNavController().popBackStack();
                                    Toast.makeText(getContext(), spaceport.getName() + " hase been sold", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "You must keep at least 1 spaceport", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), spaceport.getName() + " isn't empty", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {

                    Button buy_button = createBuyButton();
                    buy_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(manageSystem.buySpaceport(spaceport.getId())) {
                                instanceFunction.getNavController().popBackStack();
                                Toast.makeText(getContext(), spaceport.getName() + " hase been purchased", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

                break;

            case STARSHIP:

                starship = (Starship) instanceFunction.getStarshipList().get(id - 1);
                manageStarship();

                if(manage) {

                    createLevelStarshipLayout();
                    createPortStarshipLayout();

                    Button sell_button = createSellButton();
                    sell_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            spaceport = (Spaceport) instanceFunction.getSpaceportList().get(starship.getPort() - 1);
                            if(manageSystem.sellStarship(starship.getId())) {
                                spaceport.getStarshipStation().remove(starship);
                                instanceFunction.getNavController().popBackStack();
                                Toast.makeText(getContext(), starship.getName() + " hase been sold", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "You must keep at least 1 starship", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {

                    Button buy_button = createBuyButton();
                    buy_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList spaceportList = instanceFunction.getSpaceportList();
                            for (int i = 0; i < spaceportList.size(); i++) {

                                Spaceport spaceport = (Spaceport) spaceportList.get(i);

                                if (spaceport.verifyStation()) {
                                    if (manageSystem.buyStarship(starship.getId(), spaceport.getId())) {
                                        spaceport.getStarshipStation().add(starship);
                                        instanceFunction.getNavController().popBackStack();
                                        Toast.makeText(getContext(), starship.getName() + " hase been purchased", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getContext(), "All spaceport are full", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

                }

                break;

        }

        return view;

    }

    private void createManageMenu() {

    }

    //Creation Menu Manage layout
    protected void createMenuManageLayout() {

        menu_manage_layout = new LinearLayout(getContext());
        menu_manage_layout.setOrientation(LinearLayout.HORIZONTAL);
        menu_manage_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        manage_layout.addView(menu_manage_layout);

        createReturnButton(menu_manage_layout);

    }

    //Creation Return button
    protected void createReturnButton(LinearLayout menu_manage_layout) {
        Button return_button = new Button(getContext());
        return_button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 50));
        return_button.setBackground(return_button.getResources().getDrawable(R.color.transparent));
        return_button.setTypeface(return_button.getResources().getFont(R.font.quadrangle));
        return_button.setText(return_button.getResources().getString(R.string.return_button));
        menu_manage_layout.addView(return_button);

        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instanceFunction.getNavController().popBackStack();
            }
        });

    }

    //Creation Add button
    protected Button createBuyButton() {

        Button buy_button = new Button(getContext());
        buy_button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,50));
        buy_button.setBackground(buy_button.getResources().getDrawable(R.color.transparent));
        buy_button.setTypeface(buy_button.getResources().getFont(R.font.quadrangle));
        buy_button.setText(buy_button.getResources().getString(R.string.buy_button));
        menu_manage_layout.addView(buy_button);

        return buy_button;

    }

    //Creation Delete button
    protected Button createSellButton() {

        Button sell_button = new Button(getContext());
        sell_button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 50));
        sell_button.setBackground(sell_button.getResources().getDrawable(R.color.transparent));
        sell_button.setTypeface(sell_button.getResources().getFont(R.font.quadrangle));
        sell_button.setText(sell_button.getResources().getString(R.string.sell_button));
        menu_manage_layout.addView(sell_button);

        return sell_button;

    }

    private void manageSpaceport() {

        LinearLayout manage_content = new LinearLayout(getContext());
        manage_content.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        manage_content.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params_manage_content;
        params_manage_content = (LinearLayout.LayoutParams) manage_content.getLayoutParams();
        params_manage_content.setMargins(0, 0, 0, 50);
        manage_content.setWeightSum(3);
        manage_layout.addView(manage_content);

        LinearLayout manage_title_layout = new LinearLayout(getContext());
        manage_title_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        manage_title_layout.setOrientation(LinearLayout.VERTICAL);
        manage_title_layout.setGravity(Gravity.CENTER);
        manage_content.addView(manage_title_layout);

        if(manage) {
            EditText nickname_edit = new EditText(getContext());
            nickname_edit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            nickname_edit.setPadding(10, 10, 10, 10);
            nickname_edit.setSingleLine();
            if(spaceport.getNickname() != null)
                nickname_edit.setText(spaceport.getNickname());
            else
                nickname_edit.setHint(spaceport.getName());
            nickname_edit.setTextSize(14);
            nickname_edit.setTypeface(nickname_edit.getResources().getFont(R.font.quadrangle));
            manage_title_layout.addView(nickname_edit);

            nickname_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if (nickname_edit.getText().toString().trim().length() > 0) {
                            spaceport.setNickname(nickname_edit.getText().toString());
                            databaseAccess.updateSpaceport(spaceport.getId(), nickname_edit.getText().toString());
                        } else {
                            spaceport.setNickname(null);
                            databaseAccess.updateSpaceport(spaceport.getId(), null);
                        }
                    }
                }
            });
        } else {
            TextView nickname_text = new TextView(getContext());
            nickname_text.setText(spaceport.getName());
            nickname_text.setTypeface(nickname_text.getResources().getFont(R.font.quadrangle));
            manage_title_layout.addView(nickname_text);
        }

    }

    private void manageStarship() {

        LinearLayout manage_content = new LinearLayout(getContext());
        manage_content.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        manage_content.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params_manage_content;
        params_manage_content = (LinearLayout.LayoutParams) manage_content.getLayoutParams();
        params_manage_content.setMargins(0, 0, 0, 50);
        manage_content.setWeightSum(3);
        manage_layout.addView(manage_content);

        LinearLayout manage_title_layout = new LinearLayout(getContext());
        manage_title_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        manage_title_layout.setOrientation(LinearLayout.VERTICAL);
        manage_title_layout.setGravity(Gravity.CENTER);
        manage_content.addView(manage_title_layout);

        ImageView manage_title_starship_image = new ImageView(getContext());
        manage_title_starship_image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        manage_title_starship_image.setImageResource(starship.getImageStarshipId()[starship.getId() - 1]);
        manage_title_layout.addView(manage_title_starship_image);

        if(manage) {
            EditText manage_title_name_edit = new EditText(getContext());
            manage_title_name_edit.setLayoutParams(new LinearLayout.LayoutParams(300, LinearLayout.LayoutParams.WRAP_CONTENT));
            manage_title_name_edit.setPadding(10, 10, 10, 10);
            manage_title_name_edit.setSingleLine();
            if(starship.getNickname() != null)
                manage_title_name_edit.setText(starship.getNickname());
            else
                manage_title_name_edit.setHint(starship.getName());
            manage_title_name_edit.setTextSize(14);
            manage_title_name_edit.setTypeface(manage_title_name_edit.getResources().getFont(R.font.quadrangle));
            manage_title_layout.addView(manage_title_name_edit);

            manage_title_name_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if (manage_title_name_edit.getText().toString().trim().length() > 0) {
                            starship.setNickname(manage_title_name_edit.getText().toString());
                            databaseAccess.updateStarshipNickname(spaceport.getId(), manage_title_name_edit.getText().toString());
                        } else {
                            starship.setNickname(null);
                            databaseAccess.updateStarshipNickname(spaceport.getId(), null);
                        }
                    }
                }
            });
        } else {
            TextView nickname_text = new TextView(getContext());
            nickname_text.setText(starship.getName());
            nickname_text.setTypeface(nickname_text.getResources().getFont(R.font.quadrangle));
            manage_title_layout.addView(nickname_text);
        }

        LinearLayout manage_table_layout = new LinearLayout(getContext());
        manage_table_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2));
        manage_table_layout.setOrientation(LinearLayout.VERTICAL);
        manage_table_layout.setGravity(Gravity.CENTER);
        manage_content.addView(manage_table_layout);

        manage_stat_table_layout = new TableLayout(getContext());
        manage_stat_table_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        manage_table_layout.addView(manage_stat_table_layout);

        String[] stat_text = {"Mining Rate", "Speed"};
        int[] stat_value = {starship.getMining_rate(), starship.getSpeed()};

        for (int i = 0; i < stat_text.length; i++) {
            TableRow manage_stat_table_row = new TableRow(getContext());
            manage_stat_table_row.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            manage_stat_table_layout.addView(manage_stat_table_row);

            TextView manage_stat_table_row_text = new TextView(getContext());
            manage_stat_table_row_text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            manage_stat_table_row_text.setText(stat_text[i]);
            manage_stat_table_row_text.setTypeface(manage_stat_table_row_text.getResources().getFont(R.font.quadrangle));
            manage_stat_table_row_text.setPadding(20, 20, 20, 20);
            manage_stat_table_row.addView(manage_stat_table_row_text);

            TextView manage_stat_table_row_value = new TextView(getContext());
            manage_stat_table_row_value.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            manage_stat_table_row_value.setText(String.valueOf(stat_value[i] * starship.getLevel()));
            manage_stat_table_row_value.setTypeface(manage_stat_table_row_value.getResources().getFont(R.font.quadrangle));
            manage_stat_table_row_text.setPadding(20, 20, 20, 20);
            manage_stat_table_row.addView(manage_stat_table_row_value);
        }

    }

    private void createLevelSpaceportLayout() {

        LinearLayout level_layout = new LinearLayout(getContext());
        level_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        level_layout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params_level_layout;
        params_level_layout = (LinearLayout.LayoutParams) level_layout.getLayoutParams();
        params_level_layout.setMargins(0, 0, 0, 50);
        manage_layout.addView(level_layout);

        LinearLayout level_info_layout = new LinearLayout(getContext());
        level_info_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        level_info_layout.setOrientation(LinearLayout.HORIZONTAL);
        level_info_layout.setGravity(Gravity.CENTER);
        level_layout.addView(level_info_layout);

        TextView level_text = new TextView(getContext());
        level_text.setText(level_text.getResources().getString(R.string.level_label));
        level_text.setTypeface(level_text.getResources().getFont(R.font.quadrangle));
        level_info_layout.addView(level_text);

        EditText level_edit = new EditText(getContext());
        level_edit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        level_edit.setPadding(10, 10, 10, 10);
        level_edit.setEnabled(false);
        level_edit.setText((String.valueOf(spaceport.getLevel())));
        level_edit.setTextSize(14);
        level_edit.setTypeface(level_edit.getResources().getFont(R.font.quadrangle));
        level_info_layout.addView(level_edit);

        ImageView level_button = new ImageView(getContext());
        level_button.setLayoutParams(new LinearLayout.LayoutParams(75, 75));
        level_button.setImageResource(R.drawable.ic_add_button);
        level_info_layout.addView(level_button);

        LinearLayout level_cost_layout = new LinearLayout(getContext());
        level_cost_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        level_cost_layout.setOrientation(LinearLayout.HORIZONTAL);
        level_cost_layout.setGravity(Gravity.CENTER);
        level_layout.addView(level_cost_layout);

        ImageView credit_image = new ImageView(getContext());
        credit_image.setLayoutParams(new LinearLayout.LayoutParams(60, 60));
        credit_image.setImageResource(R.mipmap.hydrogen);
        level_cost_layout.addView(credit_image);

        TextView level_cost = new TextView(getContext());
        level_cost.setText(String.valueOf((spaceport.getCost() / 2) * spaceport.getLevel()));
        level_cost.setTypeface(level_cost.getResources().getFont(R.font.quadrangle));
        level_cost_layout.addView(level_cost);

        level_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manageSystem.upgradeSpaceport(spaceport.getId())) {
                    level_edit.setText(String.valueOf(spaceport.getLevel()));
                    level_cost.setText(String.valueOf((spaceport.getCost() / 2) * spaceport.getLevel()));
                    station_title_value.setText(spaceport.getNbStarshipStation() + "/" + spaceport.getLevel());
                }
            }
        });

    }

    private void createStationSpaceportLayout() {

        LinearLayout station_layout = new LinearLayout(getContext());
        station_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        station_layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params_station_layout;
        params_station_layout = (LinearLayout.LayoutParams) station_layout.getLayoutParams();
        params_station_layout.setMargins(0, 0, 0, 50);
        manage_layout.addView(station_layout);

        LinearLayout station_title_layout = new LinearLayout(getContext());
        station_title_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        station_title_layout.setOrientation(LinearLayout.HORIZONTAL);
        station_layout.addView(station_title_layout);

        TextView station_title_text = new TextView(getContext());
        station_title_text.setTypeface(station_title_text.getResources().getFont(R.font.quadrangle));
        station_title_text.setTextSize(14);
        station_title_text.setText(R.string.station_label);
        station_title_layout.addView(station_title_text);

        station_title_value = new TextView(getContext());
        station_title_value.setTypeface(station_title_value.getResources().getFont(R.font.quadrangle));
        station_title_value.setTextSize(14);
        station_title_value.setText(spaceport.getNbStarshipStation() + "/" + spaceport.getLevel());
        station_title_layout.addView(station_title_value);

        ScrollView scrollView = new ScrollView(getContext());
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200));
        scrollView.setPadding(20, 20, 20, 20);
        station_layout.addView(scrollView);

        LinearLayout station_content_layout = new LinearLayout(getContext());
        station_content_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        station_content_layout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(station_content_layout);

        ArrayList starshipStation = spaceport.getStarshipStation();
        for(int i = 0; i < starshipStation.size(); i++) {

            starship = (Starship) starshipStation.get(i);

            LinearLayout station_item_layout = new LinearLayout(getContext());
            station_item_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            station_item_layout.setOrientation(LinearLayout.HORIZONTAL);
            station_content_layout.addView(station_item_layout);

            ImageView station_item_image = new ImageView(getContext());
            station_item_image.setLayoutParams(new LinearLayout.LayoutParams(60, 60));
            station_item_image.setImageResource(starship.getImageStarshipId()[starship.getId() - 1]);
            station_item_layout.addView(station_item_image);

            TextView station_item_text = new TextView(getContext());
            station_item_text.setTypeface(station_item_text.getResources().getFont(R.font.quadrangle));
            station_item_text.setTextSize(14);
            station_item_text.setText(starship.getName());
            station_item_layout.addView(station_item_text);

        }

    }

    private void createLevelStarshipLayout() {

        LinearLayout level_layout = new LinearLayout(getContext());
        level_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        level_layout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params_level_layout;
        params_level_layout = (LinearLayout.LayoutParams) level_layout.getLayoutParams();
        params_level_layout.setMargins(0, 0, 0, 50);
        manage_layout.addView(level_layout);

        LinearLayout level_info_layout = new LinearLayout(getContext());
        level_info_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        level_info_layout.setOrientation(LinearLayout.HORIZONTAL);
        level_info_layout.setGravity(Gravity.CENTER);
        level_layout.addView(level_info_layout);

        TextView level_text = new TextView(getContext());
        level_text.setText(level_text.getResources().getString(R.string.level_label));
        level_text.setTypeface(level_text.getResources().getFont(R.font.quadrangle));
        level_info_layout.addView(level_text);

        EditText level_edit = new EditText(getContext());
        level_edit.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        level_edit.setPadding(10, 10, 10, 10);
        level_edit.setEnabled(false);
        level_edit.setText(String.valueOf(starship.getLevel()));
        level_edit.setTextSize(14);
        level_edit.setTypeface(level_edit.getResources().getFont(R.font.quadrangle));
        level_info_layout.addView(level_edit);

        ImageView level_button = new ImageView(getContext());
        level_button.setLayoutParams(new LinearLayout.LayoutParams(75, 75));
        level_button.setImageResource(R.drawable.ic_add_button);
        level_info_layout.addView(level_button);

        LinearLayout level_cost_layout = new LinearLayout(getContext());
        level_cost_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        level_cost_layout.setOrientation(LinearLayout.HORIZONTAL);
        level_cost_layout.setGravity(Gravity.CENTER);
        level_layout.addView(level_cost_layout);

        ImageView credit_image = new ImageView(getContext());
        credit_image.setLayoutParams(new LinearLayout.LayoutParams(60, 60));
        credit_image.setImageResource(R.mipmap.credit);
        level_cost_layout.addView(credit_image);

        TextView level_cost = new TextView(getContext());
        level_cost.setText(String.valueOf((starship.getCost() / 2) * starship.getLevel()));
        level_cost.setTypeface(level_cost.getResources().getFont(R.font.quadrangle));
        level_cost_layout.addView(level_cost);

        level_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manageSystem.upgradeStarship(starship.getId())) {
                    level_edit.setText(String.valueOf(starship.getLevel()));
                    level_cost.setText(String.valueOf((starship.getCost() / 2) * starship.getLevel()));

                    int[] stat_value = {starship.getMining_rate(), starship.getSpeed()};
                    for (int i = 0; i < manage_stat_table_layout.getChildCount(); i++) {
                        TableRow manage_stat_table_row = (TableRow) manage_stat_table_layout.getChildAt(i);
                        TextView manage_stat_table_row_value = (TextView) manage_stat_table_row.getChildAt(1);
                        manage_stat_table_row_value.setText(String.valueOf(stat_value[i] * starship.getLevel()));
                    }
                }
            }
        });

    }

    private void createPortStarshipLayout() {

        LinearLayout port_layout = new LinearLayout(getContext());
        port_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        port_layout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params_port_layout;
        params_port_layout = (LinearLayout.LayoutParams) port_layout.getLayoutParams();
        params_port_layout.setMargins(0, 0, 0, 50);
        manage_layout.addView(port_layout);

        ArrayList spinner_list = new ArrayList<>();
        for (int i = 0; i < instanceFunction.getSpaceportList().size(); i++) {
            spaceport = (Spaceport) instanceFunction.getSpaceportList().get(i);
            if (spaceport.getBuy() == 1) {
                if (spaceport.getNickname() != null)
                    spinner_list.add(spaceport.getNickname());
                else
                    spinner_list.add(spaceport.getName());
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.spinner_layout, spinner_list);
        Spinner port_spinner = new Spinner(getContext());
        port_spinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        port_spinner.setAdapter(adapter);
        port_spinner.setSelection(starship.getPort() - 1);
        port_spinner.setBackgroundResource(R.drawable.spinner_background);
        port_layout.addView(port_spinner);

        port_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Spaceport old_spaceport = (Spaceport) instanceFunction.getSpaceportList().get(starship.getPort() - 1);
                Spaceport new_spaceport = (Spaceport) instanceFunction.getSpaceportList().get(position);

                if (starship.getPort() != new_spaceport.getId()) {

                    if (new_spaceport.getNbStarshipStation() < new_spaceport.getLevel()) {

                        new_spaceport.getStarshipStation().add(starship);
                        old_spaceport.getStarshipStation().remove(starship);

                        starship.setPort(new_spaceport.getId());
                        databaseAccess.updateStarshipPort(starship.getId(), new_spaceport.getId());

                        Toast.makeText(getContext(), starship.getName() + " is now docked in " + new_spaceport.getName(), Toast.LENGTH_SHORT).show();

                    } else {

                        port_spinner.setSelection(starship.getPort() - 1);

                        Toast.makeText(getContext(), new_spaceport.getName() + " is full", Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}
