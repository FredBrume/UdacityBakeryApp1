package com.example.fredbrume.udacitybakeryapp1.service;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.widget.RemoteViews;

import com.example.fredbrume.udacitybakeryapp1.R;
import com.example.fredbrume.udacitybakeryapp1.data.RecipeContentProviderHelper;
import com.example.fredbrume.udacitybakeryapp1.model.Recipe;
import com.example.fredbrume.udacitybakeryapp1.util.RecipeJsonAssetUtil;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {


    private  RemoteViews updateAppWidget(Context context, int appWidgetId) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);

        Recipe recipe = RecipeContentProviderHelper.lastRecipeFromDB(context);

        if (recipe != null) {

            remoteViews.setTextViewText(R.id.widget_recipe_title, recipe.name);
            Picasso.with(context)
                    .load(recipe.image)
                    .into(remoteViews, R.id.recipe_backdrop_img, new int[] {appWidgetId});


            Intent svcIntent = new Intent(context, BakingAppWidgetService.class);

            //setting adapter to listview of the widget
            remoteViews.setRemoteAdapter(appWidgetId, R.id.ingredients_widget_list,
                    svcIntent);
        }
       return remoteViews;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
           RemoteViews remoteViews= updateAppWidget(context, appWidgetId);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

