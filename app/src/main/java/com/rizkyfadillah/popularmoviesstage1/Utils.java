package com.rizkyfadillah.popularmoviesstage1;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * @author Rizky Fadillah on 18/07/2017.
 */

public class Utils {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
