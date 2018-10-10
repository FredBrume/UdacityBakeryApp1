package com.example.fredbrume.udacitybakeryapp1.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fredbrume.udacitybakeryapp1.R;
import com.example.fredbrume.udacitybakeryapp1.model.Recipe;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fredbrume on 10/3/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Recipe[] recipes;
    private Context context;
    private RecipeClickHandler recipeClickHandler;

    public interface RecipeClickHandler {

        void OnClickRecipeThumbnail(Recipe recipe);
    }

    public RecipeAdapter(RecipeClickHandler recipeClickHandler) {

        this.recipeClickHandler = recipeClickHandler;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final int viewLayout = R.layout.recipe_list_item;
        context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(viewLayout, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {

        Recipe recipe = recipes[position];

        holder.recipeName.setText(recipe.name);
        holder.servings.setText(String.valueOf(recipe.servings));

        if (recipe.image != null && !recipe.image.isEmpty())
            Picasso.with(context).load(recipe.image).into(holder.recipeThumbnail);

    }

    @Override
    public int getItemCount() {
        return recipes == null ? 0 : recipes.length;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_thumbnail)
        ImageView recipeThumbnail;
        @BindView(R.id.recipe_title)
        TextView recipeName;
        @BindView(R.id.servings_count)
        TextView servings;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int rowPosition = getAdapterPosition();
            Recipe recipe = recipes[rowPosition];

            recipeClickHandler.OnClickRecipeThumbnail(recipe);
        }
    }

    public void setLayoutAdapter(Recipe[] recipeDataArray) {
        this.recipes = recipeDataArray;
        notifyDataSetChanged();
    }
}
