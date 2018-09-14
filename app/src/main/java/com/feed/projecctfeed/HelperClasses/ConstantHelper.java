package com.feed.projecctfeed.HelperClasses;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Prashant on 14-09-2018.
 */

public class ConstantHelper {

    public static final String IMAGE_URL = "https://cdn1.iconfinder.com/data/icons/ninja-things-1/1772/ninja-simple-128.png";

    public static boolean isTrue(String one, String two){
        return !one.isEmpty() && !two.isEmpty();
    }

    public static String time(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH:mm:ss");
        return sdf.format(cal.getTime());
    }
}
