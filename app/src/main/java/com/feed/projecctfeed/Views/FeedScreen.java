package com.feed.projecctfeed.Views;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.feed.projecctfeed.BaseController.BaseActivity;
import com.feed.projecctfeed.HelperClasses.ConstantHelper;
import com.feed.projecctfeed.HelperClasses.TextLength;
import com.feed.projecctfeed.R;
import com.feed.projecctfeed.RealmDB.PostFeedDatabase;
import com.feed.projecctfeed.Views.RealmRecyclerView.FeedDataAdapter;
import com.feed.projecctfeed.databinding.ActivityFeedBinding;


import io.realm.Realm;
import io.realm.RealmResults;

public class FeedScreen extends BaseActivity {

    // variables
    private RecyclerView recyclerView;
    private String title = null;
    private String body = null;

    private Realm realm = null;
    private RealmResults<PostFeedDatabase> modules = null;

    private final int TITLE_MAX_LENGTH = 300;
    private final int BODY_MAX_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Bind data to xml
        ActivityFeedBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_feed);
        binding.setText(new TextLength(TITLE_MAX_LENGTH, BODY_MAX_LENGTH));

        intiViews();
    }

    private void intiViews(){
        recyclerView = findViewById(R.id.list);
    }

    // post new feed
    private void addPostViewToList(){
        View headerView = findViewById(R.id.viewHeader);
        final EditText mTitle = headerView.findViewById(R.id.etTitle);
        final EditText mBody = headerView.findViewById(R.id.etBody);

        mBody.setImeOptions(EditorInfo.IME_ACTION_SEND);
        mBody.setRawInputType(InputType.TYPE_CLASS_TEXT);

        mBody.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    getData(mTitle, mBody);

                    if (title.isEmpty() || title.length() > TITLE_MAX_LENGTH){
                        mTitle.setError("Validation error!");
                        return false;
                    }
                    if (body.isEmpty() || body.length() > BODY_MAX_LENGTH){
                        mBody.setError("Validation error!");
                        return false;
                    }

                    // realm
                    writeDataToDB();
                    mTitle.setText("");
                    mBody.setText("");
                    hideSoftkey(v);
                    return true;
                }
                return false;
            }
        });
    }

    private void getData(EditText one, EditText two){
        title = one.getText().toString();
        body = two.getText().toString();
    }

    // write new created post to realm DB
    private void writeDataToDB(){
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    PostFeedDatabase feedDatabase = realm.createObject(PostFeedDatabase.class);
                    Number maxId = realm.where(PostFeedDatabase.class).max("id");
                    int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;

                    feedDatabase.setId(nextId);
                    feedDatabase.setTimeStamp(ConstantHelper.date());
                    feedDatabase.setUrl(ConstantHelper.URL());
                    feedDatabase.setTitle(title);
                    feedDatabase.setBody(body);

                    recyclerView.smoothScrollToPosition(nextId);
                }
            });
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
    }

    // Add feed posts from realm DB to recycler view, also if data is unavailable at first
    // create a dummy post as "welcome user".
    private synchronized void readRecyclerViewDataFromDB() {
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    modules = realm.where(PostFeedDatabase.class).findAll();
                    if (modules.size() == 0) {
                        PostFeedDatabase feedDatabase = realm.createObject(PostFeedDatabase.class);
                        Number maxId = realm.where(PostFeedDatabase.class).max("id");
                        int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;

                        feedDatabase.setId(nextId);
                        feedDatabase.setTimeStamp(ConstantHelper.date());
                        feedDatabase.setUrl(ConstantHelper.URL());
                        feedDatabase.setTitle(getString(R.string.test_title));
                        feedDatabase.setBody(getString(R.string.test_body));
                    }
                }
            });
        } catch (Exception ignored){
        }

        recyclerView.setHasFixedSize(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(new FeedDataAdapter(this, modules, true));
    }

    // Hide keyboard
    private void hideSoftkey(View v){
        InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        addPostViewToList();
        readRecyclerViewDataFromDB();
    }

    // remove/close any open stream
    @Override
    protected void onPause() {
        realm.close();
        super.onPause();
    }
}
