package com.feed.projecctfeed.Views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.feed.projecctfeed.BaseController.BaseActivity;
import com.feed.projecctfeed.HelperClasses.ConstantHelper;
import com.feed.projecctfeed.RealmDB.FeedDetailsDataBinding;
import com.feed.projecctfeed.R;
import com.feed.projecctfeed.RealmDB.FeedCommentsDatabase;
import com.feed.projecctfeed.RealmDB.PostFeedDatabase;
import com.feed.projecctfeed.Views.RealmRecyclerView.CommentsAdapter;
import com.feed.projecctfeed.databinding.ActivityFeedDetailsBinding;

import java.lang.ref.WeakReference;

import io.realm.Realm;
import io.realm.RealmResults;

public class FeedDetails extends BaseActivity {

    // variables
    private Realm realm = null;
    private RealmResults<FeedCommentsDatabase> mCommentList = null;

    private String mID;
    private RecyclerView recyclerView;
    private TextView mCommentsLabel;
    private ActivityFeedDetailsBinding feedDetailsBinding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Bind data to xml
        feedDetailsBinding = DataBindingUtil.setContentView(FeedDetails.this, R.layout.activity_feed_details);

        // get view id from recycler view passed through intent.
        int ID = getIntent().getIntExtra(ConstantHelper.INTENT_ID, 0);
        mID = String.valueOf(ID);

        // initialize views
        intiViews();

        // first read the detailed data of post clicked in recycler view on previous screen
        // which passes the ID/position of list item.
        readDataLayoutFromDB(ID);

        // read all comments of this particular post from realm DB
        readCommentsFromDB();
    }

    private void intiViews(){
        recyclerView = findViewById(R.id.listComments);
        mCommentsLabel = findViewById(R.id.commentsLabel);
    }

    // first read the detailed data of post clicked in recycler view on previous screen
    // which passes the ID/position of list item.
    private synchronized void readDataLayoutFromDB(final int ID){
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    PostFeedDatabase feedDatabase = realm.where(PostFeedDatabase.class).equalTo("id", ID).findFirst();
                    feedDetailsBinding.setFeedData(new FeedDetailsDataBinding(
                            feedDatabase.getTitle(),
                            feedDatabase.getBody(),
                            ConstantHelper.bulletStringBuilder(String.valueOf(feedDatabase.getId()), feedDatabase.getTimeStamp()),
                            feedDatabase.getUrl())
                    );
                }
            });
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
    }

    // read all comments of this particular post from realm DB
    private synchronized void readCommentsFromDB(){
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    mCommentList = realm.where(FeedCommentsDatabase.class).equalTo("mID", mID).findAll();
                }
            });
        } finally {
            if(realm != null && mCommentList != null) {
                postComments();
            }
        }
    }

    // write comments from user to realm DB,
    // "mID" is the position/ID of list item clicked on previous page and keeping
    // comments with this mID.
    private void writeCommentsToDB(final String mComment){
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    FeedCommentsDatabase feedCommentsDatabase = realm.createObject(FeedCommentsDatabase.class);
                    feedCommentsDatabase.setID(mID);
                    feedCommentsDatabase.setTimeStamp(ConstantHelper.time());
                    feedCommentsDatabase.setComment(mComment);
                }
            });
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
    }

    // initialize recycler view list and add all comments form realm DB
    private void postComments(){
        Log.e("Data", mCommentList.toString());
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(new CommentsAdapter(this, mCommentList, true));
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void onCommentClickListener(){
        mCommentsLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCommentDialog(FeedDetails.this);
            }
        });
    }

    // WeakReference is of no use as its under an method, was trying to create local static class,
    // skip as is not very useful. :P
    private void mCommentDialog(Activity activity){
        final WeakReference<Activity> weakReference  = new WeakReference<>(activity);
        final Context ctx = weakReference.get();
        final EditText etPostComment;

        final Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.post_comment);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);

        etPostComment = dialog.findViewById(R.id.etPostComment);
        etPostComment.setImeOptions(EditorInfo.IME_ACTION_SEND);
        etPostComment.setRawInputType(InputType.TYPE_CLASS_TEXT);

        etPostComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String data = etPostComment.getText().toString();

                    if (data.isEmpty()){
                        etPostComment.setError("Validation error!");
                        return false;
                    }

                    writeCommentsToDB(data);
                    etPostComment.setText("");
                    dialog.dismiss();
                    return true;
                }
                return false;
            }
        });
        dialog.show();
    }

    // init click even on comment posting dialog
    @Override
    protected void onResume() {
        super.onResume();
        onCommentClickListener();
    }

    // remove/close any open streams
    @Override
    protected void onDestroy() {
        if(realm != null) {
            realm.close();
        }
        super.onDestroy();
    }
}
