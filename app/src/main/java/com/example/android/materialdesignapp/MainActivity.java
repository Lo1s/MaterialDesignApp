package com.example.android.materialdesignapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    public static final String ACTION_MATERIAL = "com.example.android.androidtraining.GettingStarted.action.MATERIAL";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        String[] dataset = new String[] {"1", "2", "3", "4", "5", "6", "7" +
                "8", "9", "10", "11", "12", "13", "14", "15"};
        mAdapter = new MyAdapter(dataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private String[] mDataSet;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView mTextView;
            public ViewHolder(TextView view) {
                super(view);
                mTextView = view;
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(String[] dataset) {
            mDataSet = dataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            // create a new view
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.textview_for_recycleview, viewGroup, false);

            ViewHolder vh = new ViewHolder((TextView) v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            viewHolder.mTextView.setText(mDataSet[i]);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataSet.length;
        }
    }

    public void destroy(View view) {
        getWindow().setExitTransition(new Explode());
        startActivity(new Intent(this, LightActivity.class));
    }

    public void hide(final View view) {
        // get the center for the clipping circle
        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;

        // get the initial radius for the clipping circle
        int initialRadius = view.getWidth();

        // create the animation (the final radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });

        anim.start();
    }

    public void reveal(View view) {
        final Button hiddenButton = (Button) findViewById(R.id.button_hide);

        int cx = hiddenButton.getWidth() / 2;
        int cy = hiddenButton.getHeight() / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(hiddenButton.getWidth(), hiddenButton.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(hiddenButton, cx, cy, 0, finalRadius);

        // make the view visible and start the animation
        hiddenButton.setVisibility(View.VISIBLE);
        anim.start();
    }

    // Starts the Explode transition to the "Light Activity"
    public void startExplodeTransition(View view) {
        getWindow().setExitTransition(new Explode());
        Intent intent = new Intent(this, LightActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    // Shared transition
    public void startSharedTransition(View view) {
        // get the element that receives the click event
        View inflatedView = getLayoutInflater().inflate(R.layout.activity_light, null);
        final Button freeButton = (Button) inflatedView.findViewById(R.id.button);
        Intent intent = new Intent(getApplicationContext(), LightActivity.class);
        // create the transition animation - the images in the layouts
        // of both activities are defined with android:transitionName="robot"
        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, freeButton,
                        "button_shared_transition_test");
        // start the new activity
        startActivity(intent, options.toBundle());
    }
}
