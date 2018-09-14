package com.feed.projecctfeed.Views;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.feed.projecctfeed.R;

public class FeedScreen extends Activity {

    private RecyclerView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        intiViews();
    }

    private void intiViews(){
        view = findViewById(R.id.list);
    }

    private void addContentsToList(){
        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.post_feed_view, view, false);
        view.addView(headerView, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addContentsToList();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
