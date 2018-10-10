package com.example.fredbrume.udacitybakeryapp1.util;

import android.content.Context;

import com.example.fredbrume.udacitybakeryapp1.model.Ingredient;
import com.example.fredbrume.udacitybakeryapp1.model.Recipe;
import com.example.fredbrume.udacitybakeryapp1.model.Steps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fredbrume on 10/2/17.
 */

public class RecipeJsonAssetUtil {

    private final static String RECIPE_JSON_FILENAME = "recipe.json";
    public final static String STEPS_KEY="steps";
    public final static String RECIPE_KEY = "recipe";
    public final static String INGREDIENT_KEY = "ingredients";


    public static String loadJsonStringFromAsset(Context context) {

        String recipeJson = null;

        try {

            InputStream is = context.getAssets().open(RECIPE_JSON_FILENAME);
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);

            is.close();

            recipeJson = new String(buffer, "UTF-8");

            return recipeJson;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Recipe[] recipeJsonToList(String recipeJson) {

        final GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(Ingredient.class, new IngredientDeserializer());
        gsonBuilder.registerTypeAdapter(Steps.class, new StepsDeserializer());

        final Gson gson = gsonBuilder.create();

        Recipe[] recipe = gson.fromJson(recipeJson, Recipe[].class);

        return recipe;
    }
}
