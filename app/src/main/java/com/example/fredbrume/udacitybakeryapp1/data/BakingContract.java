package com.example.fredbrume.udacitybakeryapp1.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by fredbrume on 10/11/17.
 */

public class BakingContract {

    public static final String AUTHORITY = "com.example.fredbrume.udacitybakeryapp1";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_RECIPE = "recipe"; //the folder containing the database
    public static final String PATH_INGREDIENT = "ingredient";
    public static final String PATH_STEP = "step";


    public static final class RecipeEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE).build();

        public static final String TABLE_NAME = "recipe";
        public static final String COLUMN_RECIPE_ID = "recipe_id";
        public static final String COLUMN_NAME = "name";
        public static final String COUUMN_SERVINGS = "servings";
        public static final String COLUMN_IMAGE = "image";

    }

    public static final class IngredientEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENT).build();
        public static final String TABLE_NAME = "ingredient";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_MEASURE = "measure";
        public static final String COLUMN_INGREDIENT = "ingredient";
        public static final String COLUMN_RECIPE_ID = "recipe_id";

    }

    public static final class StepEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_STEP).build();
        public static final String TABLE_NAME = "step";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_SHORT_DISCRIPTION = "shortdescription";
        public static final String COLUMN_DISCRIPTION = "description";
        public static final String COLUMN_VIDEOURL = "videourl";
        public static final String COLUMN_THUMBNAIL = "thumbnaiurl";
        public static final String COLUMN_RECIPE_ID = "recipe_id";
    }
}
