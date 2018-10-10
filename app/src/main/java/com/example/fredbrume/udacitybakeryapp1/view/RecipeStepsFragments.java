package com.example.fredbrume.udacitybakeryapp1.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fredbrume.udacitybakeryapp1.R;
import com.example.fredbrume.udacitybakeryapp1.adapters.RecipeStepsAdapter;
import com.example.fredbrume.udacitybakeryapp1.model.Steps;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fredbrume on 10/6/17.
 */

public class RecipeStepsFragments extends Fragment implements RecipeStepsAdapter.onStepClickListener {

    @BindView(R.id.step_list)
    RecyclerView recyclerViewSteps;

    private static List<Steps> steps;
    public GetStepDetailsListener getStepDetailsListener;

    public RecipeStepsFragments() {

    }

    public interface GetStepDetailsListener {
        void getStepDetails(Steps steps);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            getStepDetailsListener = (GetStepDetailsListener) context;

        } catch (ClassCastException exception) {

            throw new ClassCastException(context.toString()
                    + " must implement GetStepDetailsListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getStepDetailsListener = null;
    }

    public static RecipeStepsFragments getInstance(List<Steps> steps) {

        RecipeStepsFragments.steps = steps;
        RecipeStepsFragments fragment = new RecipeStepsFragments();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_steps, container, false);

        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewSteps.setLayoutManager(linearLayoutManager);

        RecipeStepsAdapter recipeIngredientAdapter = new RecipeStepsAdapter(getContext(), steps, this);
        recyclerViewSteps.setAdapter(recipeIngredientAdapter);

        recyclerViewSteps.setHasFixedSize(true);
        recyclerViewSteps.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

        return view;
    }

    @Override
    public void onClick(Steps step) {

        getStepDetailsListener.getStepDetails(step);

    }
}
