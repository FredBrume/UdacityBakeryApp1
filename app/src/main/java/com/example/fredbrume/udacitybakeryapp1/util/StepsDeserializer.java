package com.example.fredbrume.udacitybakeryapp1.util;

import com.example.fredbrume.udacitybakeryapp1.model.Ingredient;
import com.example.fredbrume.udacitybakeryapp1.model.Steps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by fredbrume on 10/24/17.
 */

public class StepsDeserializer implements JsonDeserializer {

    private final static String STEP_JSON_ID = "id";
    private final static String STEP_JSON_SHORT_DISCRIPTION = "shortDescription";
    private final static String STEP_JSON_DISCRIPTION = "description";
    private final static String STEP_JSON_VIDEOURL = "videoURL";
    private final static String STEP_JSON_THUMBNAILL = "thumbnailURL";


    @Override
    public Steps deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        final JsonObject jsonObject = json.getAsJsonObject();

        final Steps steps = new Steps(jsonObject.get(STEP_JSON_ID).getAsString(),
                jsonObject.get(STEP_JSON_SHORT_DISCRIPTION).getAsString(), jsonObject.get(STEP_JSON_DISCRIPTION).getAsString(),
                jsonObject.get(STEP_JSON_VIDEOURL).getAsString(), jsonObject.get(STEP_JSON_THUMBNAILL).getAsString());

        return steps;
    }
}
