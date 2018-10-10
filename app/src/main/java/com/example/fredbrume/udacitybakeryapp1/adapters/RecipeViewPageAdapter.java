package com.example.fredbrume.udacitybakeryapp1.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.fredbrume.udacitybakeryapp1.model.Ingredient;
import com.example.fredbrume.udacitybakeryapp1.model.Recipe;
import com.example.fredbrume.udacitybakeryapp1.model.Steps;
import com.example.fredbrume.udacitybakeryapp1.util.RecipeJsonAssetUtil;
import com.example.fredbrume.udacitybakeryapp1.view.MasterListRecipeFragment;
import com.example.fredbrume.udacitybakeryapp1.view.RecipeIngredientsFragments;
import com.example.fredbrume.udacitybakeryapp1.view.RecipeStepsFragments;

import java.util.List;
import java.util.Map;

/**
 * Created by fredbrume on 5/13/17.
 */

public class RecipeViewPageAdapter extends FragmentStatePagerAdapter {

    Context context;
    private Recipe recipe;
    private List<Steps> listSteps;
    private static final String[] titles = {"Ingredients", "Steps"};

    public RecipeViewPageAdapter(Context context, List ingredientAndStepsList, FragmentManager fm) {
        super(fm);

        this.context = context;

        this.recipe= (Recipe) ingredientAndStepsList.get(0);
        this.listSteps= (List<Steps>) ingredientAndStepsList.get(1);

    }

    @Override
    public Fragment getItem(int position) {


        switch (position) {
            case 0:

                if (recipe != null) {

                    RecipeIngredientsFragments recipeIngredientsFragments = new RecipeIngredientsFragments();
                    recipeIngredientsFragments.getInstance(recipe);

                    return recipeIngredientsFragments;
                }

                return null;

            case 1:

                if (listSteps != null) {

                    RecipeStepsFragments recipeStepsFragments=new RecipeStepsFragments();
                    recipeStepsFragments.getInstance(listSteps);

                    return recipeStepsFragments;
                }

                return null;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titles[position];
    }
}
