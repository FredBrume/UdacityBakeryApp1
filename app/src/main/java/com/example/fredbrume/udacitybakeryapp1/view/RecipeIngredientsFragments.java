package com.example.fredbrume.udacitybakeryapp1.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.fredbrume.udacitybakeryapp1.R;
import com.example.fredbrume.udacitybakeryapp1.adapters.RecipeIngredientAdapter;
import com.example.fredbrume.udacitybakeryapp1.model.Ingredient;
import com.example.fredbrume.udacitybakeryapp1.model.Recipe;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fredbrume on 10/6/17.
 */

public class RecipeIngredientsFragments extends Fragment {

    private static Recipe recipe;

    @BindView(R.id.ingredients_list)
    RecyclerView recyclerViewIngredients;

    @BindView(R.id.simpleSeekBar)
    SeekBar seekBarServings;

    @BindView(R.id.servings)
    TextView servings;

    private RecipeIngredientAdapter recipeIngredientAdapter;


    public RecipeIngredientsFragments() {

    }

    public static RecipeIngredientsFragments getInstance(Recipe recipe) {

        RecipeIngredientsFragments.recipe = recipe;

        RecipeIngredientsFragments fragment = new RecipeIngredientsFragments();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);

        ButterKnife.bind(this, view);

        servings.setText(String.valueOf(recipe.servings));
        seekBarServings.setProgress(recipe.servings);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewIngredients.setLayoutManager(linearLayoutManager);

        recipeIngredientAdapter = new RecipeIngredientAdapter(getContext());
        recyclerViewIngredients.setAdapter(recipeIngredientAdapter);

        recipeIngredientAdapter.setLayout(recipe.ingredients);

        updateIngredientListOnServingsBarScroll();

        recyclerViewIngredients.setHasFixedSize(true);
        recyclerViewIngredients.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

        return view;
    }

    public void updateIngredientListOnServingsBarScroll() {
        seekBarServings.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                recipeIngredientAdapter.setLayout(recipe.ingredients, (progress/10));
                servings.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recipeIngredientAdapter.setLayout(null);
    }
}
