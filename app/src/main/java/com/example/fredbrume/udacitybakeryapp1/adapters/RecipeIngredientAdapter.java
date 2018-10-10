package com.example.fredbrume.udacitybakeryapp1.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fredbrume.udacitybakeryapp1.R;
import com.example.fredbrume.udacitybakeryapp1.model.Ingredient;
import com.example.fredbrume.udacitybakeryapp1.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fredbrume on 10/3/17.
 */

public class RecipeIngredientAdapter extends RecyclerView.Adapter<RecipeIngredientAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredients;

    private int seekbarPosition = 0;
    private Context context;


    public RecipeIngredientAdapter(Context context) {

        this.context = context;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final int viewLayout = R.layout.recipe_ingredient_list_item;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(viewLayout, parent, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {

        Ingredient ingredient = ingredients.get(position);

        holder.measure.setText(ingredient.getMeasure());
        holder.ingredient.setText(ingredient.getIngredient());

        if (seekbarPosition != 0) {

            holder.quantity.setText(String.valueOf(seekbarPosition * Double.valueOf(ingredient.getQuantity())));
        }else {
            holder.quantity.setText(String.valueOf(ingredient.getQuantity()));
        }

    }

    @Override
    public int getItemCount() {
        return ingredients == null ? 0 : ingredients.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.quantity)
        TextView quantity;
        @BindView(R.id.measure)
        TextView measure;
        @BindView(R.id.ingredient)
        TextView ingredient;


        public IngredientViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public void setLayout(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    //overloading the setLayout method for Seekbar drag
    public void setLayout(List<Ingredient> ingredients, int seekbarPosition) {
        this.ingredients = ingredients;
        this.seekbarPosition = seekbarPosition;
        notifyDataSetChanged();
    }
}
