package com.example.fredbrume.udacitybakeryapp1.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.fredbrume.udacitybakeryapp1.R;
import com.example.fredbrume.udacitybakeryapp1.model.Steps;
import com.example.fredbrume.udacitybakeryapp1.util.RecipeJsonAssetUtil;

public class RecipeStepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        if (savedInstanceState == null) {

            RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();

            Intent intent = getIntent();

            if (intent.hasExtra(Intent.EXTRA_TEXT)) {

                Steps steps= intent.getParcelableExtra(Intent.EXTRA_TEXT);


                Bundle stepBundle = new Bundle();

                stepBundle.putParcelable(RecipeJsonAssetUtil.STEPS_KEY, steps);

                fragment.setArguments(stepBundle);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.recipe_step_detail_container, fragment).commit();
            }


        }
    }
}
