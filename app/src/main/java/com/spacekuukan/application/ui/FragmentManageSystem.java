package com.spacekuukan.application.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.spacekuukan.application.R;
import com.spacekuukan.application.databinding.FragmentBaseBinding;
import com.spacekuukan.application.databinding.FragmentManageSystemBinding;
import com.spacekuukan.application.db.DatabaseAccess;
import com.spacekuukan.application.db.DatabaseThread;
import com.spacekuukan.application.function.InstanceFunction;
import com.spacekuukan.application.function.ManageSystem;

import java.util.ArrayList;

public class FragmentManageSystem extends Fragment {

    private InstanceFunction instanceFunction;
    private ManageSystem manageSystem;
    private DatabaseAccess databaseAccess;
    private DatabaseThread databaseThread;

    private FragmentManageSystemBinding binding;

    private LinearLayout manage_layout;

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

        createMenuManageLayout(selectPlanetData, selectStarshipData);

        switch (category) {
            case SPACEPORT:
                manageSpaceport(selectStarshipData);
                break;
            case STARSHIP:
                manageStarship(selectStarshipData);
                if(manage)
                    createLevelLayout(selectPlanetData, selectStarshipData);
                break;
        }

        return view;

    }

    private void createManageMenu() {

    }

    //Creation Menu Manage layout
    protected void createMenuManageLayout(ArrayList selectPlanetData, ArrayList selectStarshipData) {

        LinearLayout menu_manage_layout = new LinearLayout(getContext());
        menu_manage_layout.setOrientation(LinearLayout.HORIZONTAL);
        menu_manage_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        manage_layout.addView(menu_manage_layout);

        createReturnButton(menu_manage_layout);

        if(manage)
            createSellButton(menu_manage_layout, selectStarshipData);
        else
            createBuyButton(menu_manage_layout, selectStarshipData);

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
    protected void createBuyButton(LinearLayout menu_manage_layout, ArrayList selectStarshipData) {

        Button buy_button = new Button(getContext());
        buy_button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,50));
        buy_button.setBackground(buy_button.getResources().getDrawable(R.color.transparent));
        buy_button.setTypeface(buy_button.getResources().getFont(R.font.quadrangle));
        buy_button.setText(buy_button.getResources().getString(R.string.buy_button));
        menu_manage_layout.addView(buy_button);

        //Extract values to add in the database
        buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addTask();
                instanceFunction.getNavController().popBackStack();
            }
        });

    }

    //Creation Delete button
    protected void createSellButton(LinearLayout menu_manage_layout, ArrayList selectStarshipData) {

        Button sell_button = new Button(getContext());
        sell_button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 50));
        sell_button.setBackground(sell_button.getResources().getDrawable(R.color.transparent));
        sell_button.setTypeface(sell_button.getResources().getFont(R.font.quadrangle));
        sell_button.setText(sell_button.getResources().getString(R.string.sell_button));
        menu_manage_layout.addView(sell_button);

        //Display a confirmation popup to delete in the database
        sell_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manageSystem.sellStarship(selectStarshipData))
                    instanceFunction.getNavController().popBackStack();
            }
        });

    }

    private void manageSpaceport(ArrayList selectStarshipData) {

    }

    private void manageStarship(ArrayList selectStarshipData) {

        LinearLayout nickname_layout = new LinearLayout(getContext());
        nickname_layout.setOrientation(LinearLayout.HORIZONTAL);
        nickname_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        manage_layout.addView(nickname_layout);

        EditText nickname_edit = new EditText(getContext());
        nickname_edit.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        nickname_edit.setPadding(10, 10, 10, 10);
        nickname_edit.setSingleLine();
        if(selectStarshipData.get(2) != null)
            nickname_edit.setText(selectStarshipData.get(2).toString());
        else
            nickname_edit.setHint(selectStarshipData.get(1).toString());
        nickname_edit.setTextSize(14);
        nickname_edit.setTypeface(nickname_edit.getResources().getFont(R.font.quadrangle));
        nickname_layout.addView(nickname_edit);

        TableLayout stat_table = new TableLayout(getContext());
        stat_table.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        manage_layout.addView(stat_table);

        String[] stat = {"Power", "Mining Rate", "Speed"};

        for(int i = 6; i < 9; i++) {
            TableRow stat_row = new TableRow(getContext());
            stat_row.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            stat_table.addView(stat_row);

            TextView stat_text = new TextView(getContext());
            stat_text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            stat_text.setText(stat[i - 6]);
            stat_text.setTypeface(stat_text.getResources().getFont(R.font.quadrangle));
            stat_row.addView(stat_text);

            TextView stat_value = new TextView(getContext());
            stat_value.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            stat_value.setText(String.valueOf(Integer.valueOf((String) selectStarshipData.get(i)) * Integer.valueOf((String) selectStarshipData.get(4))));
            stat_value.setTypeface(stat_value.getResources().getFont(R.font.quadrangle));
            stat_row.addView(stat_value);
        }

        nickname_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    databaseAccess.updateStarship(Integer.valueOf((String) selectStarshipData.get(0)), nickname_edit.getText().toString());
                }
            }
        });

    }

    private void createLevelLayout(ArrayList selectPlanetData, ArrayList selectStarshipData) {

        LinearLayout level_layout = new LinearLayout(getContext());
        level_layout.setOrientation(LinearLayout.HORIZONTAL);
        level_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        manage_layout.addView(level_layout);

        TextView level_text = new TextView(getContext());
        level_text.setText(level_text.getResources().getString(R.string.level_label));
        level_text.setTypeface(level_text.getResources().getFont(R.font.quadrangle));
        level_layout.addView(level_text);

        EditText level_edit = new EditText(getContext());
        level_edit.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        level_edit.setPadding(10, 10, 10, 10);
        level_edit.setEnabled(false);
        level_edit.setText(selectStarshipData.get(4).toString());
        level_edit.setTextSize(14);
        level_edit.setTypeface(level_edit.getResources().getFont(R.font.quadrangle));
        level_layout.addView(level_edit);

        ImageButton level_button = new ImageButton(getContext());
        level_edit.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        level_button.setImageResource(R.drawable.ic_add_button);
        level_layout.addView(level_button);

        level_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int credit = Integer.valueOf((String) selectPlanetData.get(5)) - (Integer.valueOf((String) selectStarshipData.get(5)) / 2) * Integer.valueOf((String) selectStarshipData.get(4));
                if(credit >= 0) {
                    databaseAccess.updateCreditPlanet(1, credit);
                    databaseAccess.updateStarship(Integer.valueOf((String) selectStarshipData.get(0)), Integer.valueOf((String) selectStarshipData.get(4)) + 1);
                    databaseThread.run();


                }
            }
        });

    }

    private void createManageLayout() {

    }
}
