package com.example.fredbrume.udacitybakeryapp1.service;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.fredbrume.udacitybakeryapp1.R;
import com.example.fredbrume.udacitybakeryapp1.data.RecipeContentProviderHelper;
import com.example.fredbrume.udacitybakeryapp1.model.Ingredient;
import com.example.fredbrume.udacitybakeryapp1.model.Recipe;
import com.example.fredbrume.udacitybakeryapp1.util.RecipeJsonAssetUtil;


import java.util.List;

/**
 * Created by fredbrume on 10/15/17.
 */

public class BakingAppWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new BakingAppremoteviewsFactory(this.getApplicationContext());
    }
}

class BakingAppremoteviewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    List<Ingredient> ingredients;

    public BakingAppremoteviewsFactory(Context context) {

        this.mContext = context;

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        Recipe recipe = RecipeContentProviderHelper.lastRecipeFromDB(mContext);

        ingredients = recipe.ingredients;

    }

    @Override
    public void onDestroy() {
        if (ingredients != null) ingredients.clear();
    }

    @Override
    public int getCount() {
        return ingredients == null ? 0 : ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteViews = null;

        if (ingredients != null) {

            Ingredient ingredient = ingredients.get(position);

            remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_recipe_ingredient_list_item);

            remoteViews.setTextViewText(R.id.quantity, ingredient.getQuantity());
            remoteViews.setTextViewText(R.id.measure, ingredient.getMeasure());
            remoteViews.setTextViewText(R.id.ingredient, ingredient.getIngredient());
        }


        return remoteViews;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
