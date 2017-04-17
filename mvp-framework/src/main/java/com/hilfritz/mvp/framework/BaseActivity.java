package com.hilfritz.mvp.framework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hilfritz.mvp.framework.helper.AppVisibilityInterface;

/**IMPORTANT: FRAMEWORK CLASS**/
/* Features:
 * 1. checks if app went to background, to do this your activity/presenter must implement AppVisibilityInterface.java and set it to #setAppVisibilityHandler() of the activity
 * @author Hilfritz P. Camallere on 8/20/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    public static int sessionDepth = 0;

    AppVisibilityInterface appVisibilityHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * IMPORTANT: FRAMEWORK METHOD
     * Called whenever onCreate() is called
     * @param savedInstanceState Bundle
     * @param presenter {@link BasePresenter} - this parameter must be a subclass of
     */
    public void __fmwk_ba_checkIfNewActivity(Bundle savedInstanceState, BasePresenter presenter) {
        if (savedInstanceState==null){
            presenter.__fmwk_bp_setInitialLoad(true);
        }else {
            presenter.__fmwk_bp_setInitialLoad(false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (sessionDepth > 0)
            sessionDepth--;
        if (sessionDepth == 0) {
            // app went to background
            Log.d(TAG, "onStop: [sessionDepth:"+sessionDepth+"] app went to background, implement AppVisibilityHandler.java interface to handle when app goes to background.");
            // TODO: 17/4/17 DO YOUR BROADCAST HERE TO HANDLE
            if (getAppVisibilityHandler()!=null){
                getAppVisibilityHandler().onAppSentToBackground();
            }
        }
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onStart()");
        sessionDepth++;
        super.onResume();

    }

    public void setAppVisibilityHandler(AppVisibilityInterface appVisibilityHandler) {
        this.appVisibilityHandler = appVisibilityHandler;
    }

    public AppVisibilityInterface getAppVisibilityHandler() {
        return appVisibilityHandler;
    }
}
