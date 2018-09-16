package com.feed.projecctfeed.RealmDB;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.feed.projecctfeed.R;
import com.squareup.picasso.Picasso;

/**
 * <p>
 *     A plain POJO model class to handle Feed posting in main screen.
 * </p>
 * */
public class FeedDetailsDataBinding {

    private String mTitle;
    private String mBody;
    private String mTimeStamp;
    private String mUrl;

    /** @param mTitle for Title of post
     *  @param mBody for Body of post
     *  @param mTimeStamp for current date/time of post
     *  @param mUrl holds random image url from ConstantHelper class.
     **/
    public FeedDetailsDataBinding(String mTitle, String mBody, String mTimeStamp, String mUrl) {
        this.mTitle = mTitle;
        this.mBody = mBody;
        this.mTimeStamp = mTimeStamp;
        this.mUrl = mUrl;
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

    @BindingAdapter({"mUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.get().load(imageUrl).placeholder(R.drawable.ic_loading).error(R.drawable.ic_error).into(view);
    }
}
