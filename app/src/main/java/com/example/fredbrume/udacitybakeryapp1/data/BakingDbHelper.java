package com.example.fredbrume.udacitybakeryapp1.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fredbrume on 10/11/17.
 */

public class BakingDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "recipeDb.db";
    private static final int DATABASE_VERSION = 1;

    public BakingDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_TABLE_RECIPE = "CREATE TABLE " + BakingContract.RecipeEntry.TABLE_NAME + "(" +

                BakingContract.RecipeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BakingContract.RecipeEntry.COLUMN_RECIPE_ID + " TEXT NOT NULL, " +
                BakingContract.RecipeEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                BakingContract.RecipeEntry.COUUMN_SERVINGS + " TEXT NOT NULL, " +
                BakingContract.RecipeEntry.COLUMN_IMAGE + " TEXT NOT NULL ); ";


        final String CREATE_TABLE_INGREDIENT = "CREATE TABLE " + BakingContract.IngredientEntry.TABLE_NAME + "(" +

                BakingContract.IngredientEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BakingContract.IngredientEntry.COLUMN_QUANTITY + " TEXT NOT NULL, " +
                BakingContract.IngredientEntry.COLUMN_MEASURE + " TEXT NOT NULL, " +
                BakingContract.IngredientEntry.COLUMN_INGREDIENT + " TEXT NOT NULL," +
                BakingContract.IngredientEntry.COLUMN_RECIPE_ID + " INTEGER NOT NULL );";


        final String CREATE_TABLE_STEP = "CREATE TABLE " + BakingContract.StepEntry.TABLE_NAME + "(" +

                BakingContract.StepEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BakingContract.StepEntry.COLUMN_ID + " TEXT NOT NULL, " +
                BakingContract.StepEntry.COLUMN_SHORT_DISCRIPTION + " TEXT NOT NULL, " +
                BakingContract.StepEntry.COLUMN_DISCRIPTION + " TEXT NOT NULL, " +
                BakingContract.StepEntry.COLUMN_VIDEOURL + " TEXT, " +
                BakingContract.StepEntry.COLUMN_THUMBNAIL + " TEXT, " +
                BakingContract.IngredientEntry.COLUMN_RECIPE_ID + " INTEGER NOT NULL );";


        db.execSQL(CREATE_TABLE_RECIPE);
        db.execSQL(CREATE_TABLE_INGREDIENT);
        db.execSQL(CREATE_TABLE_STEP);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + BakingContract.RecipeEntry.TABLE_NAME);
        onCreate(db);
    }
}
