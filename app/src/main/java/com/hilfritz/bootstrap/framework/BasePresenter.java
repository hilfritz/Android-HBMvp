package com.hilfritz.bootstrap.framework;

import android.util.Log;

/**
 * Created by Hilfritz P. Camallere on 7/2/2016.
 */

public class BasePresenter {
    private static final String TAG = "BasePresenter";
    /**
     * True - if the activity is newly created
     * False - if the activity is just rotated
     */
    boolean initialLoad = true;

    public boolean __fmwk_bp_isInitialLoad() {
        Log.d(TAG, "bp_isInitialLoad: "+((initialLoad)?"true":"false"));
        return initialLoad;
    }

    public void __fmwk_bp_setInitialLoad(boolean initialLoad) {
        Log.d(TAG, "bp_setInitialLoad: set to:"+((initialLoad)?"true":"false"));
        this.initialLoad = initialLoad;
    }

}
