package com.hilfritz.bootstrap.framework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
/**IMPORTANT: FRAMEWORK CLASS**/
/**
 *
 * Created by Hilfritz P. Camallere on 8/20/2016.
 */
public class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * IMPORTANT: FRAMEWORK METHOD
     * Called whenever onCreate() is called
     * @param savedInstanceState Bundle
     * @param presenter {@link BasePresenter} - this parameter must be a subclass of
     */
    public void __fmwk_bf_checkIfNewActivity(Bundle savedInstanceState, BasePresenter presenter) {
        Log.d(TAG, "bf_checkIfNewActivity: ");
        if (savedInstanceState==null){
            Log.d(TAG, "bf_checkIfNewActivity: presenter INITIAL LOAD");
            presenter.__fmwk_bp_setInitialLoad(true);
        }else {
            Log.d(TAG, "bf_checkIfNewActivity: presenter from ROTATION");
            presenter.__fmwk_bp_setInitialLoad(false);
        }
    }
}
