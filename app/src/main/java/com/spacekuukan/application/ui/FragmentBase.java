package com.spacekuukan.application.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.spacekuukan.application.R;
import com.spacekuukan.application.databinding.FragmentBaseBinding;
import com.spacekuukan.application.db.DatabaseAccess;
import com.spacekuukan.application.db.DatabaseThread;
import com.spacekuukan.application.function.Base;
import com.spacekuukan.application.function.InstanceFunction;
import com.spacekuukan.application.function.ManageSystem;

import java.util.ArrayList;
import java.util.List;

public class FragmentBase extends Fragment {

    private Base base;
    private InstanceFunction instanceFunction;
    private DatabaseAccess databaseAccess;
    private ManageSystem manageSystem;

    private FragmentBaseBinding binding;

    private Spinner spinner;
    private LinearLayout base_page;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBaseBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        this.instanceFunction = InstanceFunction.getInstance();
        this.databaseAccess = DatabaseAccess.getInstance(getContext());
        this.manageSystem = new ManageSystem(getContext());


        spinner     = view.findViewById(R.id.spinner_base_menu);
        base_page   = view.findViewById(R.id.base_page);

        //base.getInstance();

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.base_menu_spinner, R.layout.spinner_layout);
        spinner.setBackgroundResource(R.drawable.spinner_background);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                    spacePortLayout();
                else
                    starshipLayout();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void spacePortLayout() {

    //createBaseLayout("Space Port");


    }

    private void starshipLayout() {

        base_page.removeAllViews();

        String[] title = {"", "Harvester", "Striker"};
        for(int i = 1; i < title.length; i++) {
            createBaseLayout(i, title[i]);
        }

    }

    private void createBaseLayout(int type, String title) {

        LinearLayout base_layout = new LinearLayout(getContext());
        base_layout.setOrientation(LinearLayout.VERTICAL);
        base_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        base_layout.setBackgroundColor(base_layout.getResources().getColor(R.color.white));
        LinearLayout.LayoutParams params_category_layout;
        params_category_layout = (LinearLayout.LayoutParams) base_layout.getLayoutParams();
        params_category_layout.setMargins(0, 0, 0, 50);
        base_layout.setLayoutParams(params_category_layout);
        base_page.addView(base_layout);

        LinearLayout title_layout = new LinearLayout(getContext());
        title_layout.setOrientation(LinearLayout.HORIZONTAL);
        title_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        title_layout.setPadding(30, 30, 30, 30);
        base_layout.addView(title_layout);

        TextView title_text = new TextView(getContext());
        title_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 5));
        LinearLayout.LayoutParams params_title_text;
        params_title_text = (LinearLayout.LayoutParams) title_text.getLayoutParams();
        params_title_text.gravity = Gravity.CENTER;
        title_text.setLayoutParams(params_title_text);
        title_text.setText(title);
        title_text.setTextSize(18);
        title_text.setTypeface(title_text.getResources().getFont(R.font.quadrangle));
        title_layout.addView(title_text);

        ImageView arrow_button = new ImageView(getContext());
        arrow_button.setLayoutParams(new LinearLayout.LayoutParams(75, 75, 1));
        arrow_button.setImageResource(R.drawable.ic_down_arrow);
        title_layout.addView(arrow_button);

        LinearLayout base_content = new LinearLayout(getContext());
        base_content.setOrientation(LinearLayout.VERTICAL);
        base_content.setPadding(10,0, 10, 0);
        base_content.setBackgroundColor(base_content.getResources().getColor(R.color.white));
        base_content.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        base_layout.addView(base_content);

        ArrayList selectStarship = databaseAccess.selectStarship(type, true);
        for(int item = 0; item < selectStarship.size(); item++) {
            createBaseItemLayout(base_content, selectStarship, item);
        }

        if(base_content.getChildCount() > 0) {
            arrow_button.setRotation(0);
            base_content.setVisibility(View.VISIBLE);
        }else{
            arrow_button.setRotation(90);
            base_content.setVisibility(View.GONE);
        }

        arrow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(base_content.getVisibility() == View.VISIBLE){
                    arrow_button.animate().rotation(90).start();
                    base_content.setVisibility(View.GONE);
                }else{
                    arrow_button.animate().rotation(0).start();
                    base_content.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void createBaseItemLayout(LinearLayout base_content, ArrayList selectStarship, int item) {

        ArrayList starship = (ArrayList) selectStarship.get(item);

        LinearLayout base_item = new LinearLayout(getContext());
        base_item.setOrientation(LinearLayout.VERTICAL);
        base_item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        base_item.setBackgroundColor(base_item.getResources().getColor(R.color.white));
        LinearLayout.LayoutParams params_category_layout;
        params_category_layout = (LinearLayout.LayoutParams) base_item.getLayoutParams();
        params_category_layout.setMargins(0, 0, 0, 50);
        base_item.setLayoutParams(params_category_layout);
        base_content.addView(base_item);

        LinearLayout title_item = new LinearLayout(getContext());
        title_item.setOrientation(LinearLayout.HORIZONTAL);
        title_item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        title_item.setPadding(30, 30, 30, 30);
        title_item.setId((Integer.valueOf((String) starship.get(0))));
        title_item.setWeightSum(8);
        base_item.addView(title_item);

        TextView title_text = new TextView(getContext());
        title_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 5));
        LinearLayout.LayoutParams params_title_text;
        params_title_text = (LinearLayout.LayoutParams) title_text.getLayoutParams();
        params_title_text.gravity = Gravity.CENTER;
        title_text.setLayoutParams(params_title_text);
        if(starship.get(2) != null)
            title_text.setText(starship.get(2).toString());
        else
            title_text.setText(starship.get(1).toString());
        title_text.setTextSize(14);
        title_text.setTypeface(title_text.getResources().getFont(R.font.quadrangle));
        title_item.addView(title_text);

        LinearLayout sell_text_layout = new LinearLayout(getContext());
        sell_text_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2));
        title_item.addView(sell_text_layout);

        TextView sell_text = new TextView(getContext());
        sell_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams params_sell_text;
        params_sell_text = (LinearLayout.LayoutParams) title_text.getLayoutParams();
        params_sell_text.gravity = Gravity.CENTER;
        sell_text.setLayoutParams(params_title_text);
        sell_text.setText(R.string.sell_button);
        sell_text.setTextSize(14);
        sell_text.setTypeface(title_text.getResources().getFont(R.font.quadrangle));
        sell_text_layout.addView(sell_text);

        title_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList manageSystemArgument = new ArrayList<>();
                manageSystemArgument.add(title_item.getId());
                manageSystemArgument.add(2);
                manageSystemArgument.add(true);
                instanceFunction.setManageSystemArgument(manageSystemArgument);
                instanceFunction.getNavController().navigate(R.id.fragmentManageSystem);
            }
        });

        sell_text_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manageSystem.sellStarship(starship))
                    base_item.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}