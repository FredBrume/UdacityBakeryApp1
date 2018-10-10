package com.example.fredbrume.udacitybakeryapp1.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.example.fredbrume.udacitybakeryapp1.R;
import com.example.fredbrume.udacitybakeryapp1.adapters.RecipeAdapter;
import com.example.fredbrume.udacitybakeryapp1.model.Recipe;
import com.example.fredbrume.udacitybakeryapp1.util.HideShowScrollListener;
import com.example.fredbrume.udacitybakeryapp1.util.RecipeJsonAssetUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RecipeAdapter.RecipeClickHandler {

    private LoaderManager loaderManager;
    public static final int LOADER_ID = 0;
    private RecipeAdapter recipeAdapter;
    public static final String RECIPE_INTENT_KEY="recipe";


    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.main_recipe_list) RecyclerView recipeList;
    @BindView(R.id.toolbar_bottom)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        LayoutInflater mInflater=LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_toolbar_menu_layout, null);
        toolbar.addView(mCustomView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        recipeList.setLayoutManager(gridLayoutManager);

        recipeAdapter = new RecipeAdapter(this);
        recipeList.setAdapter(recipeAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);


        loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, new FetchRecipeTask());

        recipeList.addOnScrollListener(new HideShowScrollListener() {
            @Override
            public void onHide() {
                toolbar.animate().translationY(+toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
            }

            @Override
            public void onShow() {

                toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

            }
        });


    }

    public int calculateNoOfColumns() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }

    @Override
    public void onRefresh() {

        loaderManager.restartLoader(LOADER_ID, null, new FetchRecipeTask());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 500);

    }

    @Override
    public void OnClickRecipeThumbnail(Recipe recipe) {

        Intent intent =new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RECIPE_INTENT_KEY,recipe);

        startActivity(intent);
    }

    public class FetchRecipeTask implements LoaderManager.LoaderCallbacks<Recipe[]> {

        @SuppressLint("StaticFieldLeak")
        @Override
        public Loader<Recipe[]> onCreateLoader(int id, Bundle args) {

            return new AsyncTaskLoader<Recipe[]>(getBaseContext()) {

                Recipe[] recipes = null;

                @Override
                protected void onStartLoading() {
                    super.onStartLoading();

                    if (recipes != null) {
                        deliverResult(recipes);
                    } else {
                        forceLoad();
                    }
                }

                @Override
                public void deliverResult(Recipe[] data) {
                    super.deliverResult(data);
                    recipes = data;
                }

                @Override
                public Recipe[] loadInBackground() {

                    try {

                        String recipeJsonFromAsset = RecipeJsonAssetUtil.loadJsonStringFromAsset(getBaseContext());

                        Recipe[] recipeArray = RecipeJsonAssetUtil.recipeJsonToList(recipeJsonFromAsset);

                        return recipeArray;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<Recipe[]> loader, Recipe[] data) {

            recipeAdapter.setLayoutAdapter(data);
        }

        @Override
        public void onLoaderReset(Loader<Recipe[]> loader) {

            recipeAdapter.setLayoutAdapter(null);
        }
    }

}
