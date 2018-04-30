package com.udacity.sandwichclub;

import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // Show action bar
        getSupportActionBar().show();

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich); //sets text views

        //Show sandwich image
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //Populate the description text
        TextView description_tv = findViewById(R.id.description_tv);
        description_tv.setText(sandwich.getDescription());

        //Populate the origin text
        TextView origin_label_tv = findViewById(R.id.origin_label_tv) ;
        TextView origin_tv = findViewById(R.id.origin_tv);
        String origin = sandwich.getPlaceOfOrigin();
        //only show the textviews if there is origin info avail
        if (origin != null && origin.length() > 0 ) {
            origin_tv.setText(origin);
        } else {
            origin_tv.setVisibility(View.GONE); //Nothing to show, so make the label go away
            origin_label_tv.setVisibility(View.GONE); //Nothing to show, so make the space go away
        }

        //Populate the ingredients text
        TextView ingredients_tv = findViewById(R.id.ingredients_tv);
        List<String> ingredients_list = new ArrayList<String>();
        ingredients_list = sandwich.getIngredients();
        String ingredients = "";
        //Convert array list to a string with commas in-between
        for (int i = 0; i <ingredients_list.size(); i++ ) {
            if (i != 0) { ingredients += ", " ; }
            ingredients += ingredients_list.get(i);
        }
        ingredients_tv.setText(ingredients);

        //Populate the "Also Known As" text
        TextView aka_tv_label = findViewById(R.id.aka_tv_label);
        TextView aka_tv = findViewById(R.id.also_known_tv);
        List<String> aka_list = new ArrayList<String>();
        aka_list = sandwich.getAlsoKnownAs();
        // Only show the following if the aka_list is not empty
        if (aka_list.size() > 0) {
            String aka = "";
            //Convert array list to a string with commas in-between
            for (int i = 0; i < aka_list.size(); i++) {
                if (i != 0) {
                    aka += ", ";
                }
                aka += aka_list.get(i);
            }
            aka_tv.setText(aka);
        } else {
            aka_tv_label.setVisibility(View.GONE); //Nothing to show, so make the label go away
            aka_tv.setVisibility(View.GONE); //Nothing to show, so make the space go away
        }

    }
}
