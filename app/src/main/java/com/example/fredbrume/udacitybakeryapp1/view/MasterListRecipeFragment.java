package com.example.fredbrume.udacitybakeryapp1.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.fredbrume.udacitybakeryapp1.R;
import com.example.fredbrume.udacitybakeryapp1.adapters.RecipeViewPageAdapter;
import com.example.fredbrume.udacitybakeryapp1.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fredbrume on 10/5/17.
 */

public class MasterListRecipeFragment extends Fragment {

    @BindView(R.id.recipe_backdrop_img)
    ImageView recipeBackdrop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.event_view_pager)
    ViewPager viewPager;
    @BindView(R.id.materialTabHost)
    TabLayout tabHost;

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    RecipeViewPageAdapter adapter;

    public MasterListRecipeFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = null;

        Bundle recipeBundle = getArguments();

        if (recipeBundle != null) {

            view = inflater.inflate(R.layout.fragment_master_detail_recipe_layout, container, false);

            setHasOptionsMenu(true);

            ButterKnife.bind(this, view);

            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);

            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbarAppearance();



            Recipe recipe = recipeBundle.getParcelable(RecipeDetailActivity.RECIPE_PARCEL_KEY);

            Picasso.with(view.getContext()).load(recipe.image).into(recipeBackdrop);
            collapsingToolbarLayout.setTitle((recipe.name));

            Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
            recipeBackdrop.setAnimation(anim);
            anim.start();

            List ingredientAndStepsList = new ArrayList();

            ingredientAndStepsList.add(recipe);
            ingredientAndStepsList.add(recipe.steps);

            adapter = new RecipeViewPageAdapter(getContext(), ingredientAndStepsList, getFragmentManager());
            viewPager.setAdapter(adapter);

            tabHost.setupWithViewPager(viewPager);

        }

        return view;
    }


    private void toolbarAppearance() {

        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
    }
}
