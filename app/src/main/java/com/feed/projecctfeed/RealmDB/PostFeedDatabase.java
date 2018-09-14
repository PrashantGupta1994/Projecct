package com.feed.projecctfeed.RealmDB;

import io.realm.RealmObject;

public class PostFeedDatabase extends RealmObject {

    //@PrimaryKey
    private int id;

    private String mTitle, mBody, mTimeStamp;
    private String mUrl;

    public PostFeedDatabase() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setBody(String mBody) {
        this.mBody = mBody;
    }

    public void setTimeStamp(String mTimeStamp) {
        this.mTimeStamp = mTimeStamp;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }

    public String getTimeStamp() {
        return mTimeStamp;
    }

    public String getUrl() {
        return mUrl;
    }
}
