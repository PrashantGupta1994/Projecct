package com.feed.projecctfeed.Views.RealmRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feed.projecctfeed.HelperClasses.ConstantHelper;
import com.feed.projecctfeed.R;
import com.feed.projecctfeed.RealmDB.PostFeedDatabase;
import com.feed.projecctfeed.Views.FeedDetails;
import com.squareup.picasso.Picasso;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * <p>
 *     RecyclerView Adapter class to handle list of each post in FeedScreen.class - Activity/Page.
 * </p>
 * */

public class FeedDataAdapter extends RealmRecyclerViewAdapter<PostFeedDatabase, FeedDataAdapter.ViewHolder> {

    /** @param context is passed from Activity to handle environment
     *  @param data holds the realm list of Feed Post
     *  @param autoUpdate as name suggest, it auto updates the list with new post as comes in.
     */
    public FeedDataAdapter(Context context, OrderedRealmCollection<PostFeedDatabase> data, boolean autoUpdate) {
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
        PostFeedDatabase feedDatabase = getData().get(position);
        holder.stamp.setText(ConstantHelper.bulletStringBuilder(String.valueOf(feedDatabase.getId()), feedDatabase.getTimeStamp()));
        holder.title.setText(feedDatabase.getTitle());
        holder.body.setText(feedDatabase.getBody());
        Picasso.get().load(feedDatabase.getUrl()).placeholder(R.drawable.ic_loading).error(R.drawable.ic_error).into(holder.imageView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView stamp;
        private TextView title;
        private TextView body;
        private ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            stamp = v.findViewById(R.id.etCount);
            title = v.findViewById(R.id.etTitle);
            body = v.findViewById(R.id.etBody);
            imageView = v.findViewById(R.id.imView);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                PostFeedDatabase feedDatabase = getData().get(position);
                int id = feedDatabase.getId();
                context.startActivity(new Intent(context, FeedDetails.class).putExtra(ConstantHelper.INTENT_ID, id));
            }
        }
    }
}
