package com.feed.projecctfeed.Views.RealmRecyclerView;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feed.projecctfeed.HelperClasses.ConstantHelper;
import com.feed.projecctfeed.R;
import com.feed.projecctfeed.RealmDB.FeedCommentsDatabase;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * <p>
 *     RecyclerView Adapter class to handle list of comments in FeedDetails.class Activity/Page.
 * </p>
 *
 * */

public class CommentsAdapter extends RealmRecyclerViewAdapter<FeedCommentsDatabase, CommentsAdapter.ViewHolder> {

    /** @param context is passed from Activity to handle environment.
     *  @param data holds the realm list of Feed comments by position of list item.
     *  @param autoUpdate as name suggest, it auto updates the list with new comments as comes in.
     * */
    public CommentsAdapter(Context context, OrderedRealmCollection<FeedCommentsDatabase> data, boolean autoUpdate) {
        super(context, data, autoUpdate);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_comments, parent, false);
        return new CommentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeedCommentsDatabase feedCommentsDatabase = getData().get(position);
        holder.mComment.setText(feedCommentsDatabase.getComment());
        holder.mTimeStamp.setText(ConstantHelper.bulletStringBuilder(feedCommentsDatabase.getID(), feedCommentsDatabase.getTimeStamp()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mComment;
        private TextView mTimeStamp;

        public ViewHolder(View v) {
            super(v);
            mComment = v.findViewById(R.id.tvComment);
            mTimeStamp = v.findViewById(R.id.tvCommentStamp);
        }
    }
}
