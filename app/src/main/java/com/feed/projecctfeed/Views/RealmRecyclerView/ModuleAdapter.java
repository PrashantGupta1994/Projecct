package com.feed.projecctfeed.Views.RealmRecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feed.projecctfeed.R;
import com.feed.projecctfeed.RealmDB.PostFeedDatabase;
import com.squareup.picasso.Picasso;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


public class ModuleAdapter extends RealmRecyclerViewAdapter<PostFeedDatabase, ModuleAdapter.ViewHolder> {

    public ModuleAdapter(Context context, OrderedRealmCollection<PostFeedDatabase> data, boolean autoUpdate) {
        super(context, data, autoUpdate);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_feed_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            PostFeedDatabase feedDatabase = getData().get(position);
            holder.title.setText(feedDatabase.getTitle());
            holder.body.setText(feedDatabase.getBody());
            Picasso.get().load(feedDatabase.getUrl()).fit().centerInside().into(holder.imageView);
        }catch (NullPointerException ignored){
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title, body;
        private ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.etTitle);
            body = v.findViewById(R.id.etBody);
            imageView = v.findViewById(R.id.imView);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
