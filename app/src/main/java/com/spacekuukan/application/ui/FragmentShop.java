package com.spacekuukan.application.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.spacekuukan.application.R;
import com.spacekuukan.application.databinding.FragmentShopBinding;
import com.spacekuukan.application.db.DatabaseAccess;
import com.spacekuukan.application.db.DatabaseThread;
import com.spacekuukan.application.function.InstanceFunction;
import com.spacekuukan.application.function.ManageSystem;

import java.util.ArrayList;

public class FragmentShop extends Fragment {

    private InstanceFunction instanceFunction;
    private DatabaseAccess databaseAccess;
    private DatabaseThread databaseThread;
    private ManageSystem manageSystem;

    private FragmentShopBinding binding;

    private Spinner spinner;
    private LinearLayout shop_page;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentShopBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        this.instanceFunction = InstanceFunction.getInstance();
        this.databaseAccess = DatabaseAccess.getInstance(getContext());
        this.databaseThread = DatabaseThread.getInstance(databaseAccess, null, null);
        this.manageSystem = new ManageSystem(getContext());

        spinner     = view.findViewById(R.id.spinner_shop_menu);
        shop_page   = view.findViewById(R.id.shop_page);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void spacePortLayout() {

        shop_page.removeAllViews();

        createShopSpaceportLayout("Space Port");

    }

    private void starshipLayout() {

        shop_page.removeAllViews();

        String[] title = {"", "Harvester", "Striker"};
        for(int i = 1; i < title.length; i++) {
            createShopStarshipLayout(i, title[i]);
        }

    }

    private void createShopSpaceportLayout(String title) {

        LinearLayout shop_layout = new LinearLayout(getContext());
        shop_layout.setOrientation(LinearLayout.VERTICAL);
        shop_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        shop_layout.setBackgroundColor(shop_layout.getResources().getColor(R.color.white));
        LinearLayout.LayoutParams params_category_layout;
        params_category_layout = (LinearLayout.LayoutParams) shop_layout.getLayoutParams();
        params_category_layout.setMargins(0, 0, 0, 50);
        shop_layout.setLayoutParams(params_category_layout);
        shop_page.addView(shop_layout);

        LinearLayout title_layout = new LinearLayout(getContext());
        title_layout.setOrientation(LinearLayout.HORIZONTAL);
        title_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        title_layout.setPadding(30, 30, 30, 30);
        shop_layout.addView(title_layout);

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
        shop_layout.addView(base_content);

        ArrayList selectSpaceport = databaseAccess.selectSpaceport(false);
        for(int item = 0; item < selectSpaceport.size(); item++) {
            createShopSpaceportItemLayout(base_content, selectSpaceport, item);
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

    private void createShopSpaceportItemLayout(LinearLayout base_content, ArrayList selectSpaceport, int item) {

        ArrayList spaceport = (ArrayList) selectSpaceport.get(item);

        LinearLayout shop_item = new LinearLayout(getContext());
        shop_item.setOrientation(LinearLayout.VERTICAL);
        shop_item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        shop_item.setBackgroundColor(shop_item.getResources().getColor(R.color.white));
        LinearLayout.LayoutParams params_category_layout;
        params_category_layout = (LinearLayout.LayoutParams) shop_item.getLayoutParams();
        params_category_layout.setMargins(0, 0, 0, 50);
        shop_item.setLayoutParams(params_category_layout);
        base_content.addView(shop_item);

        LinearLayout title_item = new LinearLayout(getContext());
        title_item.setOrientation(LinearLayout.HORIZONTAL);
        title_item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        title_item.setPadding(30, 30, 30, 30);
        title_item.setId((Integer.valueOf((String) spaceport.get(0))));
        title_item.setWeightSum(10);
        shop_item.addView(title_item);

        LinearLayout title_text_layout = new LinearLayout(getContext());
        title_text_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 5));
        title_item.addView(title_text_layout);

        TextView title_text = new TextView(getContext());
        title_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams params_title_text;
        params_title_text = (LinearLayout.LayoutParams) title_text.getLayoutParams();
        params_title_text.gravity = Gravity.CENTER;
        title_text.setLayoutParams(params_title_text);
        title_text.setText((CharSequence) spaceport.get(1));
        title_text.setTextSize(14);
        title_text.setTypeface(title_text.getResources().getFont(R.font.quadrangle));
        title_text_layout.addView(title_text);

        LinearLayout cost_text_layout = new LinearLayout(getContext());
        cost_text_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 3));
        title_item.addView(cost_text_layout);

        TextView cost_text = new TextView(getContext());
        title_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams params_cost_text;
        params_cost_text = (LinearLayout.LayoutParams) title_text.getLayoutParams();
        params_cost_text.gravity = Gravity.CENTER;
        cost_text.setLayoutParams(params_title_text);
        cost_text.setText((CharSequence) spaceport.get(4) + " H");
        cost_text.setTextSize(14);
        cost_text.setTypeface(cost_text.getResources().getFont(R.font.quadrangle));
        cost_text_layout.addView(cost_text);

        LinearLayout buy_text_layout = new LinearLayout(getContext());
        buy_text_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2));
        title_item.addView(buy_text_layout);

        TextView buy_text = new TextView(getContext());
        title_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams params_buy_text;
        params_buy_text = (LinearLayout.LayoutParams) title_text.getLayoutParams();
        params_buy_text.gravity = Gravity.CENTER;
        buy_text.setLayoutParams(params_title_text);
        buy_text.setText(R.string.buy_button);
        buy_text.setTextSize(14);
        buy_text.setTypeface(buy_text.getResources().getFont(R.font.quadrangle));
        buy_text_layout.addView(buy_text);

        title_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList manageSystemArgument = new ArrayList<>();
                manageSystemArgument.add(title_item.getId());
                manageSystemArgument.add(1);
                manageSystemArgument.add(false);
                instanceFunction.setManageSystemArgument(manageSystemArgument);
                instanceFunction.getNavController().navigate(R.id.fragmentManageSystem);
            }
        });

        buy_text_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manageSystem.buySpaceport(spaceport))
                    shop_item.setVisibility(View.GONE);
            }
        });

    }

    private void createShopStarshipLayout(int type, String title) {

        LinearLayout shop_layout = new LinearLayout(getContext());
        shop_layout.setOrientation(LinearLayout.VERTICAL);
        shop_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        shop_layout.setBackgroundColor(shop_layout.getResources().getColor(R.color.white));
        LinearLayout.LayoutParams params_category_layout;
        params_category_layout = (LinearLayout.LayoutParams) shop_layout.getLayoutParams();
        params_category_layout.setMargins(0, 0, 0, 50);
        shop_layout.setLayoutParams(params_category_layout);
        shop_page.addView(shop_layout);

        LinearLayout title_layout = new LinearLayout(getContext());
        title_layout.setOrientation(LinearLayout.HORIZONTAL);
        title_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        title_layout.setPadding(30, 30, 30, 30);
        shop_layout.addView(title_layout);

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
        shop_layout.addView(base_content);

        ArrayList selectStarship = databaseAccess.selectStarship(type, false);
        for(int item = 0; item < selectStarship.size(); item++) {
            createShopStarshipItemLayout(base_content, selectStarship, item);
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

    private void createShopStarshipItemLayout(LinearLayout base_content, ArrayList selectStarship, int item) {

        ArrayList starship = (ArrayList) selectStarship.get(item);

        LinearLayout shop_item = new LinearLayout(getContext());
        shop_item.setOrientation(LinearLayout.VERTICAL);
        shop_item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        shop_item.setBackgroundColor(shop_item.getResources().getColor(R.color.white));
        LinearLayout.LayoutParams params_category_layout;
        params_category_layout = (LinearLayout.LayoutParams) shop_item.getLayoutParams();
        params_category_layout.setMargins(0, 0, 0, 50);
        shop_item.setLayoutParams(params_category_layout);
        base_content.addView(shop_item);

        LinearLayout title_item = new LinearLayout(getContext());
        title_item.setOrientation(LinearLayout.HORIZONTAL);
        title_item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        title_item.setPadding(30, 30, 30, 30);
        title_item.setId((Integer.valueOf((String) starship.get(0))));
        title_item.setWeightSum(10);
        shop_item.addView(title_item);

        LinearLayout title_text_layout = new LinearLayout(getContext());
        title_text_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 5));
        title_item.addView(title_text_layout);

        TextView title_text = new TextView(getContext());
        title_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams params_title_text;
        params_title_text = (LinearLayout.LayoutParams) title_text.getLayoutParams();
        params_title_text.gravity = Gravity.CENTER;
        title_text.setLayoutParams(params_title_text);
        title_text.setText((CharSequence) starship.get(1));
        title_text.setTextSize(12);
        title_text.setTypeface(title_text.getResources().getFont(R.font.quadrangle));
        title_text_layout.addView(title_text);

        LinearLayout cost_text_layout = new LinearLayout(getContext());
        cost_text_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 3));
        title_item.addView(cost_text_layout);

        TextView cost_text = new TextView(getContext());
        title_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams params_cost_text;
        params_cost_text = (LinearLayout.LayoutParams) title_text.getLayoutParams();
        params_cost_text.gravity = Gravity.CENTER;
        cost_text.setLayoutParams(params_title_text);
        cost_text.setText((CharSequence) starship.get(5) + " $");
        cost_text.setTextSize(12);
        cost_text.setTypeface(cost_text.getResources().getFont(R.font.quadrangle));
        cost_text_layout.addView(cost_text);

        LinearLayout buy_text_layout = new LinearLayout(getContext());
        buy_text_layout.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2));
        title_item.addView(buy_text_layout);

        TextView buy_text = new TextView(getContext());
        title_text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams params_buy_text;
        params_buy_text = (LinearLayout.LayoutParams) title_text.getLayoutParams();
        params_buy_text.gravity = Gravity.CENTER;
        buy_text.setLayoutParams(params_title_text);
        buy_text.setText(R.string.buy_button);
        buy_text.setTextSize(12);
        buy_text.setTypeface(buy_text.getResources().getFont(R.font.quadrangle));
        buy_text_layout.addView(buy_text);

        title_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList manageSystemArgument = new ArrayList<>();
                manageSystemArgument.add(title_item.getId());
                manageSystemArgument.add(2);
                manageSystemArgument.add(false);
                instanceFunction.setManageSystemArgument(manageSystemArgument);
                instanceFunction.getNavController().navigate(R.id.fragmentManageSystem);
            }
        });

        buy_text_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manageSystem.buyStarship(starship))
                    shop_item.setVisibility(View.GONE);
            }
        });

    }
}
