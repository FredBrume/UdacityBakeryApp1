package com.example.fredbrume.udacitybakeryapp1.util;

import com.example.fredbrume.udacitybakeryapp1.model.Ingredient;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by fredbrume on 10/24/17.
 */

public class IngredientDeserializer implements JsonDeserializer {

    private final static String INGREDIENT_JSON_KEY = "ingredient";
    private final static String QUANTITY_JSON_KEY = "quantity";
    private final static String MEASURE_JSON_KEY = "measure";


    @Override
    public Ingredient deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        final JsonObject jsonObject = json.getAsJsonObject();

        final Ingredient ingredient = new Ingredient(jsonObject.get(QUANTITY_JSON_KEY).getAsString(),
                jsonObject.get(MEASURE_JSON_KEY).getAsString(), jsonObject.get(INGREDIENT_JSON_KEY).getAsString());

        return ingredient;
    }
}
