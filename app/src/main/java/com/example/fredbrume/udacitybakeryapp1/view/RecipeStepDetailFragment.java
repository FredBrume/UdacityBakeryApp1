package com.example.fredbrume.udacitybakeryapp1.view;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fredbrume.udacitybakeryapp1.R;
import com.example.fredbrume.udacitybakeryapp1.model.Steps;
import com.example.fredbrume.udacitybakeryapp1.util.RecipeJsonAssetUtil;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by fredbrume on 10/9/17.
 */

public class RecipeStepDetailFragment extends Fragment {

    private SimpleExoPlayer mExoPlayer;
    private long playerPosition;

    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;

    @BindView(R.id.step_short_discription)
    TextView shortDiscription;

    @BindView(R.id.step_id)
    TextView stepId;

    @BindView(R.id.long_discription)
    TextView longDiscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);

        ButterKnife.bind(this, view);

        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.video_default));

        Bundle bundleSteps = getArguments();


        if (bundleSteps != null) {

            Steps steps=bundleSteps.getParcelable(RecipeJsonAssetUtil.STEPS_KEY);

            stepId.setText(steps.getId());
            shortDiscription.setText(steps.getShortDescription());
            longDiscription.setText(steps.getDescription());
            initializePlayer(Uri.parse(steps.getVideoURL()));
        }

        return view;
    }

    private void initializePlayer(Uri mediaUri) {

        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), getAppicationName(getContext()));
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    public String getAppicationName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(context.getApplicationInfo().packageName, 0);
        } catch (final PackageManager.NameNotFoundException e) {

        }

        return (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : "Unknown");
    }

    private void releasePlayer() {

        if (mExoPlayer != null) {

            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private void pauseVideoAndAudio() {
        if (mExoPlayer != null) {

            playerPosition = mExoPlayer.getCurrentPosition();
        }
    }

    private void resumeVideoAndAudio() {

        if (mExoPlayer != null) {
            mExoPlayer.seekTo(playerPosition);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        resumeVideoAndAudio();

    }

    @Override
    public void onStop() {
        super.onStop();

        if (mExoPlayer != null) {
            pauseVideoAndAudio();
            mExoPlayer.stop();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseVideoAndAudio();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
