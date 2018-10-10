package com.example.fredbrume.udacitybakeryapp1.data;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.fredbrume.udacitybakeryapp1.model.Ingredient;
import com.example.fredbrume.udacitybakeryapp1.model.Recipe;
import com.example.fredbrume.udacitybakeryapp1.model.Steps;
import com.example.fredbrume.udacitybakeryapp1.util.RecipeJsonAssetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fredbrume on 10/13/17.
 */

public class RecipeContentProviderHelper {


    public static boolean insertRecipe(Context context, Recipe recipe) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(BakingContract.RecipeEntry.COLUMN_RECIPE_ID, recipe.id);
        contentValues.put(BakingContract.RecipeEntry.COLUMN_NAME, recipe.name);
        contentValues.put(BakingContract.RecipeEntry.COUUMN_SERVINGS, recipe.servings);
        contentValues.put(BakingContract.RecipeEntry.COLUMN_IMAGE, recipe.image);

        Uri uri = context.getContentResolver().insert(BakingContract.RecipeEntry.CONTENT_URI, contentValues);

        if (uri != null) {

            System.out.println("RECIPE NAME: " + lastRecipeFromDB(context).name);
            System.out.println("RECIPE ID: " + recipe.id);

            insertIngredients(context, recipe.ingredients, recipe.id);
            insertSteps(context, recipe.steps, recipe.id);

            return true;
        }

        return false;
    }

    private static void insertIngredients(Context context, List<Ingredient> ingredientList, int recipe_Id) {

        ContentValues[] contentValuesArray = new ContentValues[ingredientList.size()];

        for (int i = 0; i < ingredientList.size(); ++i) {

            Ingredient ingredient = ingredientList.get(i);

            ContentValues contentValues = new ContentValues();
            contentValues.put(BakingContract.IngredientEntry.COLUMN_QUANTITY, ingredient.getQuantity());
            contentValues.put(BakingContract.IngredientEntry.COLUMN_MEASURE, ingredient.getMeasure());
            contentValues.put(BakingContract.IngredientEntry.COLUMN_INGREDIENT, ingredient.getIngredient());
            contentValues.put(BakingContract.IngredientEntry.COLUMN_RECIPE_ID, recipe_Id);

            contentValuesArray[i] = contentValues;
        }

        context.getContentResolver().bulkInsert(BakingContract.IngredientEntry.CONTENT_URI, contentValuesArray);

    }

    private static void insertSteps(Context context, List<Steps> steps, int recipe_id) {

        ContentValues[] contentValuesArray = new ContentValues[steps.size()];

        for (int i = 0; i < steps.size(); ++i) {

            Steps step = steps.get(i);

            ContentValues contentValues = new ContentValues();
            contentValues.put(BakingContract.StepEntry.COLUMN_ID, step.getId());
            contentValues.put(BakingContract.StepEntry.COLUMN_SHORT_DISCRIPTION, step.getShortDescription());
            contentValues.put(BakingContract.StepEntry.COLUMN_DISCRIPTION, step.getDescription());
            contentValues.put(BakingContract.StepEntry.COLUMN_VIDEOURL, step.getVideoURL());
            contentValues.put(BakingContract.StepEntry.COLUMN_THUMBNAIL, step.getThumbnailURL());
            contentValues.put(BakingContract.StepEntry.COLUMN_THUMBNAIL, step.getThumbnailURL());
            contentValues.put(BakingContract.StepEntry.COLUMN_RECIPE_ID, recipe_id);

            contentValuesArray[i] = contentValues;
        }

        context.getContentResolver().bulkInsert(BakingContract.StepEntry.CONTENT_URI, contentValuesArray);

    }

    public static boolean recipeIDListFromDB(Context context, Recipe recipe) {

        ArrayList<Integer> arrayListRecipeId = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(BakingContract.RecipeEntry.CONTENT_URI,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {

                arrayListRecipeId.add(cursor.getInt(cursor.getColumnIndex(BakingContract.RecipeEntry.COLUMN_RECIPE_ID)));

            } while (cursor.moveToNext());
        }

        for (int recipeId : arrayListRecipeId) {
            if (recipeId == recipe.id) {
                return true;
            }
        }

        return false;
    }

    public static Recipe lastRecipeFromDB(Context context) {

        Recipe recipe = new Recipe();

        Cursor recipeCursor = context.getContentResolver().query(BakingContract.RecipeEntry.CONTENT_URI,
                null, null, null, null);

        if (recipeCursor.moveToLast()) {

            recipe.id = recipeCursor.getInt(recipeCursor.getColumnIndex(BakingContract.RecipeEntry.COLUMN_RECIPE_ID));
            recipe.name = recipeCursor.getString(recipeCursor.getColumnIndex(BakingContract.RecipeEntry.COLUMN_NAME));
            recipe.image= recipeCursor.getString(recipeCursor.getColumnIndex(BakingContract.RecipeEntry.COLUMN_IMAGE));
        }

        recipe.ingredients = ingredientFromDB(context, recipe.id);
        recipe.steps = stepsFromDB(context, recipe.id);

        return recipe;
    }

    public static List<Ingredient> ingredientFromDB(Context context, int recipeId) {

        Uri contentUri = ContentUris.withAppendedId(BakingContract.IngredientEntry.CONTENT_URI, recipeId);
        List<Ingredient> ingredientList = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String quanity = cursor.getString(cursor.getColumnIndex(BakingContract.IngredientEntry.COLUMN_QUANTITY));
                String measure = cursor.getString(cursor.getColumnIndex(BakingContract.IngredientEntry.COLUMN_MEASURE));
                String ingredient = cursor.getString(cursor.getColumnIndex(BakingContract.IngredientEntry.COLUMN_INGREDIENT));

                Ingredient ingredientModel = new Ingredient(quanity, measure, ingredient);

                ingredientList.add(ingredientModel);

            } while (cursor.moveToNext());
        }

        return ingredientList;
    }

    public static List<Steps> stepsFromDB(Context context, int recipeId) {

        Uri contentUri = ContentUris.withAppendedId(BakingContract.StepEntry.CONTENT_URI, recipeId);

        List<Steps> stepsList = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);

        if (cursor.moveToFirst()) {

            do {
                String id = cursor.getString(cursor.getColumnIndex(BakingContract.StepEntry.COLUMN_ID));
                String shortDiscription = cursor.getString(cursor.getColumnIndex(BakingContract.StepEntry.COLUMN_SHORT_DISCRIPTION));
                String discription = cursor.getString(cursor.getColumnIndex(BakingContract.StepEntry.COLUMN_DISCRIPTION));
                String videoUrl = cursor.getString(cursor.getColumnIndex(BakingContract.StepEntry.COLUMN_VIDEOURL));
                String thumbnail = cursor.getString(cursor.getColumnIndex(BakingContract.StepEntry.COLUMN_THUMBNAIL));

                Steps step = new Steps(id, shortDiscription, discription, videoUrl, thumbnail);

                stepsList.add(step);

            } while (cursor.moveToNext());
        }

        return stepsList;
    }


    public static boolean deleteRecipe(Context context, Recipe recipe) {

        try {
            context.getContentResolver().delete(ContentUris.withAppendedId(
                    BakingContract.StepEntry.CONTENT_URI, recipe.id), null, null);

            context.getContentResolver().delete(ContentUris.withAppendedId(
                    BakingContract.IngredientEntry.CONTENT_URI, recipe.id), null, null);


            context.getContentResolver().delete(ContentUris.withAppendedId(
                    BakingContract.RecipeEntry.CONTENT_URI, recipe.id), null, null);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
