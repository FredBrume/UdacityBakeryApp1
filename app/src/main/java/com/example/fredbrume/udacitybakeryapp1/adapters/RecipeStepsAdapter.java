package com.example.fredbrume.udacitybakeryapp1.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fredbrume.udacitybakeryapp1.R;
import com.example.fredbrume.udacitybakeryapp1.model.Steps;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fredbrume on 10/3/17.
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.StepsViewHolder> {

    private List<Steps> steps;
    private Context context;
    public final static String STEP_RESPONSE_ID = "id";
    public final static String STEP_RESPONSE_SHORT_DISCRIPTION = "shortDescription";
    public final static String STEP_RESPONSE_DISCRIPTION = "description";
    public final static String STEP_RESPONSE_VIDEOURL = "videoURL";


    public onStepClickListener onStepClickListener;


    public interface onStepClickListener {
        void onClick(Steps steps);
    }

    public RecipeStepsAdapter(Context context, List<Steps> steps, onStepClickListener onStepClickListener) {

        this.onStepClickListener = onStepClickListener;
        this.context = context;
        this.steps = steps;
    }


    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final int viewLayout = R.layout.recipe_steps_list_item;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(viewLayout, parent, false);

        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {

        Steps step = steps.get(position);

        holder.step_id.setText(step.getId());

        holder.step_short_discription.setText(step.getShortDescription());

        if (!step.getVideoURL().toString().isEmpty()) {
            holder.video.setImageResource(R.mipmap.ic_video);
        } else {
            holder.video.setImageResource(R.mipmap.ic_no_video);
        }

    }

    @Override
    public int getItemCount() {
        return steps == null ? 0 : steps.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.step_id)
        TextView step_id;
        @BindView(R.id.step_short_discription)
        TextView step_short_discription;
        @BindView(R.id.video)
        ImageView video;

        public StepsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            Steps step =steps.get(position);

            onStepClickListener.onClick(step);
        }
    }
}
