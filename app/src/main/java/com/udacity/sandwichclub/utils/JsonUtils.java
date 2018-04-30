package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();

        try {
            JSONObject jsonObject = new JSONObject(json);
            Log.d("JSON data", json);
            //set the mainName
            String mainName = jsonObject.getJSONObject("name").optString("mainName");
            sandwich.setMainName(mainName);
            //set place of origin
            String placeOfOrigin = jsonObject.optString("placeOfOrigin");
            sandwich.setPlaceOfOrigin(placeOfOrigin);
            //set description
            String description = jsonObject.optString("description");
            sandwich.setDescription(description);
            //set image
            String image = jsonObject.optString("image");
            sandwich.setImage(image);
            //set ingredients list
            JSONArray ingredientsArray = jsonObject.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<String>();
            for (int i = 0; i <ingredientsArray.length(); i++ ) {
               ingredients.add(ingredientsArray.get(i).toString());
            }
            sandwich.setIngredients(ingredients);
            //set "Also Known As" list
            JSONArray akaArray = jsonObject.getJSONObject("name").getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<String>();
            for (int i = 0; i <akaArray.length(); i++ ) {
                alsoKnownAs.add(akaArray.get(i).toString());
            }
            sandwich.setAlsoKnownAs(alsoKnownAs);

       } catch (JSONException e) {
           Log.e("Sandwich JsonUtils", "JSON exception" ,e);
           return null;
       }



    return sandwich ;
    }
}
