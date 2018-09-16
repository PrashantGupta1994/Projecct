package com.feed.projecctfeed.HelperClasses;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;


/** <p>
 *  A constant helper class, which provide extra helper methods to app.
 * </p>
 */

public class ConstantHelper {

    /** Few default URL of images from iStock(http://www.google.com) */
    private static final String IMAGE_URL = "https://media.istockphoto.com/photos/passenger-airplane-flying-above-clouds-during-sunset-picture-id155439315";
    private static final String IMAGE_URL1 = "https://media.istockphoto.com/photos/commercial-jet-flying-over-clouds-picture-id155380716";
    private static final String IMAGE_URL2 = "https://media.istockphoto.com/photos/airplane-landscape-with-big-white-passenger-airplane-is-flying-in-the-picture-id683815596";
    private static final String IMAGE_URL3 = "https://media.istockphoto.com/photos/turbines-picture-id681169358";
    private static final String IMAGE_URL4 = "https://media.istockphoto.com/photos/airplane-picture-id490305380";

    public static final String INTENT_ID = "ID";

    /** Get a random image from feed post among these 5 */
    public static String URL(){
        String[] images = new String[]{IMAGE_URL, IMAGE_URL1, IMAGE_URL2, IMAGE_URL3, IMAGE_URL4};
        int i = new Random().nextInt(5);
        return images[i];
    }

    /** Get date in current 16-09-2018 format */
    public static String date(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(" dd-MM-yyyy");
        return sdf.format(cal.getTime());
    }

    /** Get current time in 12 hour AM/PM format */
    public static String time(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(" hh:mm a");
        return sdf.format(cal.getTime());
    }

    /** Create bullet pointed string */
    public static String bulletStringBuilder(String one, String two){
        //SpannableString string = new SpannableString(one+two);
        //string.setSpan(new BulletSpan(5, Color.BLACK), 5, two.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //return string.toString();
        return one + "  \u25CF" + two;
    }
}
