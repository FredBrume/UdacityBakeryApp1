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

import com.example.fredbrume.udacitybakeryapp1.data.BakingContract;
import com.example.fredbrume.udacitybakeryapp1.data.BakingDbHelper;

/**
 * Created by fredbrume on 10/12/17.
 */

public class StepContentProvider extends ContentProvider {

    private static final int STEP = 100;
    private static final int STEP_WITH_ID = 101;
    private BakingDbHelper bakingDbHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();


    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(BakingContract.AUTHORITY, BakingContract.PATH_STEP, STEP);
        uriMatcher.addURI(BakingContract.AUTHORITY, BakingContract.PATH_STEP + "#/", STEP_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {

        Context context = getContext();
        bakingDbHelper = new BakingDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase sqLiteDatabase = bakingDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor cursor = null;

        switch (match) {

            case STEP_WITH_ID:

                String step_id = uri.getLastPathSegment();

                String query = "SELECT * FROM " + BakingContract.StepEntry.TABLE_NAME + "s INNER JOIN "
                        + BakingContract.RecipeEntry.TABLE_NAME + "r ON s." + BakingContract.StepEntry._ID + "= r."
                        + BakingContract.RecipeEntry._ID + "WHERE " + BakingContract.StepEntry._ID +
                        "=" + step_id + ";";

                sqLiteDatabase.rawQuery(query, null);
                break;
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

        int match = sUriMatcher.match(uri);
        Uri returnUri;
        SQLiteDatabase sqLiteDatabase = bakingDbHelper.getWritableDatabase();

        switch (match) {
            case STEP:

                long id = sqLiteDatabase.insert(BakingContract.StepEntry.TABLE_NAME, null, values);

                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(BakingContract.StepEntry.CONTENT_URI, id);
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
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
