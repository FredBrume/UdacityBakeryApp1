package com.example.fredbrume.udacitybakeryapp1.view;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fredbrume.udacitybakeryapp1.R;
import com.example.fredbrume.udacitybakeryapp1.data.RecipeContentProviderHelper;
import com.example.fredbrume.udacitybakeryapp1.model.Recipe;
import com.example.fredbrume.udacitybakeryapp1.model.Steps;
import com.example.fredbrume.udacitybakeryapp1.util.RecipeJsonAssetUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeStepsFragments.GetStepDetailsListener {

    public static String RECIPE_PARCEL_KEY = "recipe";
    public static String RECIPE_PANE_KEY = "recipe";
    private Recipe recipe;

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    private boolean twoPane = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        ButterKnife.bind(this);


        if (findViewById(R.id.recipe_step_detail_layout) != null) {

            twoPane = true;

            RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.recipe_step_detail_container, fragment).commit();

        }

        if (savedInstanceState == null) {

            MasterListRecipeFragment fragment = new MasterListRecipeFragment();
            Intent intent = getIntent();
            Bundle bundleRecipe = new Bundle();

            if (intent.hasExtra(MainActivity.RECIPE_INTENT_KEY)) {

                recipe = intent.getParcelableExtra(MainActivity.RECIPE_INTENT_KEY);

                bundleRecipe.putBoolean(RECIPE_PANE_KEY, twoPane);
                bundleRecipe.putParcelable(RECIPE_PARCEL_KEY, recipe);
                fragment.setArguments(bundleRecipe);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.recipe_master_list_fragment, fragment).commit();

                if (RecipeContentProviderHelper.recipeIDListFromDB(this, recipe) == true) {

                    floatingActionButton.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_favorite_clicked));

                }

            }
        }

    }

    @OnClick(R.id.fab)
    void saveRecipe(View view) {

        if (view.getId() == R.id.fab) {

            if (RecipeContentProviderHelper.recipeIDListFromDB(this, recipe) == true) {

               if(RecipeContentProviderHelper.deleteRecipe(this, recipe) == true){

                   floatingActionButton.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_favorite_unclicked));
                   Snackbar.make(view, getResources().getString(R.string.removed_to_favorite_), Snackbar.LENGTH_LONG)
                           .setAction("Action", null).show();

               }
            } else {
                if (RecipeContentProviderHelper.insertRecipe(this, recipe) == true) ;

                floatingActionButton.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_favorite_clicked));
                Snackbar.make(view, getResources().getString(R.string.added_to_favorite_clicked), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }


        }

    }



    @Override
    public void getStepDetails(Steps steps) {

        if (twoPane == false) {

            Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, steps);

            startActivity(intent);

        } else {

            RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();

            Bundle stepBundle = new Bundle();

            stepBundle.putParcelable(RecipeJsonAssetUtil.STEPS_KEY, steps);

            fragment.setArguments(stepBundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.recipe_step_detail_container, fragment).commit();
        }

    }
}
