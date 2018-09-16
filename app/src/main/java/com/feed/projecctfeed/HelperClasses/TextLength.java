package com.feed.projecctfeed.HelperClasses;

/**
 * <p>
 *     POJO class for data binding of FeedScreen.class.
 * </p>
 * */

public class TextLength {
    int mTitle;
    int mBody;

    /** @param mTitle takes the max char length allowed for post title.
     *  @param mBody takes the max char length allowed for post body.
     */
    public TextLength(int mTitle, int mBody) {
        this.mTitle = mTitle;
        this.mBody = mBody;
    }

    public int getTitle() {
        return mTitle;
    }

    public int getBody() {
        return mBody;
    }
}
