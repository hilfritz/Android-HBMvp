package com.hilfritz.bootstrap.framework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hilfritz.bootstrap.application.MyApplication;
/**IMPORTANT: FRAMEWORK CLASS**/
/**
 * Created by Hilfritz P. Camallere on 8/20/2016.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApplication)getApplication()).getAppComponent().inject(this);
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
}
