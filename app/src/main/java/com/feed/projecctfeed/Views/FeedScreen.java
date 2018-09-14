package com.feed.projecctfeed.Views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.feed.projecctfeed.BaseController.BaseActivity;
import com.feed.projecctfeed.HelperClasses.ConstantHelper;
import com.feed.projecctfeed.R;
import com.feed.projecctfeed.RealmDB.PostFeedDatabase;
import com.feed.projecctfeed.Views.RealmRecyclerView.ModuleAdapter;


import io.realm.Realm;
import io.realm.RealmResults;

public class FeedScreen extends BaseActivity {

    private RecyclerView recyclerView;
    private Realm realm = null;
    private RealmResults<PostFeedDatabase> modules = null;

    private String title = null;
    private String body = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        intiViews();
    }

    private void intiViews(){
        recyclerView = findViewById(R.id.list);
    }

    private void addPostViewToList(){
        View headerView = findViewById(R.id.viewHeader);
        final EditText mTitle = headerView.findViewById(R.id.etTitle);
        final EditText mBody = headerView.findViewById(R.id.etBody);

        mBody.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    title = mTitle.getText().toString();
                    body = mBody.getText().toString();

                    if (ConstantHelper.isTrue(title, body)) {
                        writeDataToDB();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private synchronized void writeDataToDB(){
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NonNull Realm realm) {
                    PostFeedDatabase feedDatabase = realm.createObject(PostFeedDatabase.class);
                    Number maxId = realm.where(PostFeedDatabase.class).max("id");
                    int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;

                    feedDatabase.setId(nextId);
                    feedDatabase.setTimeStamp(ConstantHelper.time());
                    feedDatabase.setUrl(ConstantHelper.IMAGE_URL);
                    feedDatabase.setTitle(title);
                    feedDatabase.setBody(body);
                    Toast.makeText(FeedScreen.this, "Posted!", Toast.LENGTH_SHORT).show();
                }
            });
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
    }

    private synchronized void initRecyclerView() {
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
                        feedDatabase.setTimeStamp(ConstantHelper.time());
                        feedDatabase.setUrl(ConstantHelper.IMAGE_URL);
                        feedDatabase.setTitle(getString(R.string.test_title));
                        feedDatabase.setBody(getString(R.string.test_body));
                    }
                }
            });
        } catch (Exception ignored){
        }

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (modules.size() != 0) {
            recyclerView.setAdapter(new ModuleAdapter(this, modules, true));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addPostViewToList();
        initRecyclerView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        realm.close();
    }

}
