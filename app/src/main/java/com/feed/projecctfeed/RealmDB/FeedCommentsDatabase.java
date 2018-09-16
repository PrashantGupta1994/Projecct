package com.feed.projecctfeed.RealmDB;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * <p>
 *     A Model/POJO class to handle comments for each post.
 * </p>
 * */

public class FeedCommentsDatabase extends RealmObject {
    //@PrimaryKey
    private String mID;
    private String mComment;
    private String mTimeStamp;

    public FeedCommentsDatabase() {
    }

    public void setID(String mID) {
        this.mID = mID;
    }

    public void setComment(String mComment) {
        this.mComment = mComment;
    }

    public void setTimeStamp(String mTimeStamp) {
        this.mTimeStamp = mTimeStamp;
    }

    public String getID() {
        return mID;
    }

    public String getComment() {
        return mComment;
    }

    public String getTimeStamp() {
        return mTimeStamp;
    }
}
