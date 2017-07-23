package com.rizkyfadillah.popularmoviesstage1;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * @author Rizky Fadillah on 23/07/2017.
 */

public class CustomLinearLayoutManager extends LinearLayoutManager {

    public CustomLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
