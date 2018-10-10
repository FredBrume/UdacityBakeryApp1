package com.example.fredbrume.udacitybakeryapp1.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;

/**
 * Created by fredbrume on 10/11/17.
 */

public class RecipeContentProvider extends ContentProvider {

    private static final int RECIPE = 100;
    private static final int RECIPE_WITH_ID = 101;
    private static final int INGREDIENT = 200;
    private static final int INGREDIENT_WITH_ID = 201;
    private static final int STEP = 300;
    private static final int STEP_WITH_ID = 301;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    BakingDbHelper bakingDbHelper;


    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(BakingContract.AUTHORITY, BakingContract.PATH_RECIPE, RECIPE);
        uriMatcher.addURI(BakingContract.AUTHORITY, BakingContract.PATH_RECIPE + "/#", RECIPE_WITH_ID);

        uriMatcher.addURI(BakingContract.AUTHORITY, BakingContract.PATH_INGREDIENT, INGREDIENT);
        uriMatcher.addURI(BakingContract.AUTHORITY, BakingContract.PATH_INGREDIENT + "/#", INGREDIENT_WITH_ID);

        uriMatcher.addURI(BakingContract.AUTHORITY, BakingContract.PATH_STEP, STEP);
        uriMatcher.addURI(BakingContract.AUTHORITY, BakingContract.PATH_STEP + "/#", STEP_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {

        Context context = getContext();
        bakingDbHelper = new BakingDbHelper(context);

        return true;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        final SQLiteDatabase db = bakingDbHelper.getWritableDatabase();
        int rowsInserted = 0;

        switch (sUriMatcher.match(uri)) {

            case INGREDIENT:

                db.beginTransaction();

                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(BakingContract.IngredientEntry.TABLE_NAME, null, value);

                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                System.out.println("Total number of Ingredient inserted: " + rowsInserted);

                return rowsInserted;

            case STEP:

                db.beginTransaction();

                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(BakingContract.StepEntry.TABLE_NAME, null, value);

                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                System.out.println("Totak number of steps inserted: " + rowsInserted);

                return rowsInserted;

            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase sqLiteDatabase = bakingDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        String recipe_id = uri.getLastPathSegment();

        Cursor cursor = null;

        switch (match) {

            case RECIPE:

                String recipeQuery = "SELECT * FROM " + BakingContract.RecipeEntry.TABLE_NAME + ";";

                cursor = sqLiteDatabase.rawQuery(recipeQuery, null);

                break;

            case INGREDIENT_WITH_ID:

                String ingredientQuery = "SELECT * FROM " + BakingContract.IngredientEntry.TABLE_NAME
                        + " WHERE " + BakingContract.IngredientEntry.COLUMN_RECIPE_ID + " = " + recipe_id + ";";

                cursor = sqLiteDatabase.rawQuery(ingredientQuery, null);

                break;

            case STEP_WITH_ID:

                String stepQuery = "SELECT * FROM " + BakingContract.StepEntry.TABLE_NAME
                        + " WHERE " + BakingContract.StepEntry.COLUMN_RECIPE_ID + " = " + recipe_id + ";";

                cursor = sqLiteDatabase.rawQuery(stepQuery, null);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        Uri returnUri;
        final SQLiteDatabase sqLiteDatabase = bakingDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        switch (match) {

            case RECIPE:

                long id = sqLiteDatabase.insert(BakingContract.RecipeEntry.TABLE_NAME, null, values);

                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(BakingContract.RecipeEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = bakingDbHelper.getWritableDatabase();

        int favoriteDeleted=0;
        String recipe_id = uri.getLastPathSegment();
        int match = sUriMatcher.match(uri);

        switch (match) {

            case RECIPE_WITH_ID:

                favoriteDeleted = db.delete(BakingContract.RecipeEntry.TABLE_NAME,
                        BakingContract.RecipeEntry.COLUMN_RECIPE_ID + "=?", new String[]{String.valueOf(recipe_id)});

                break;

            case INGREDIENT_WITH_ID:

                favoriteDeleted = db.delete(BakingContract.IngredientEntry.TABLE_NAME,
                        BakingContract.IngredientEntry.COLUMN_RECIPE_ID + "=?", new String[]{String.valueOf(recipe_id)});

                break;

            case STEP_WITH_ID:

                favoriteDeleted = db.delete(BakingContract.StepEntry.TABLE_NAME,
                        BakingContract.StepEntry.COLUMN_RECIPE_ID + "=?", new String[]{String.valueOf(recipe_id)});

                break;

            default:
                throw new android.database.SQLException("Failed to delete row into " + uri);
        }

        if (favoriteDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }


        return favoriteDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
