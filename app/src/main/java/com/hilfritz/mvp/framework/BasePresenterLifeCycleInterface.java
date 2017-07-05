package com.hilfritz.mvp.framework;

/**
 * Created by Hilfritz Camallere on 31/3/17.
 * PC name herdmacbook1
 */

public interface BasePresenterLifeCycleInterface {
    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onResume() method.
     */
    void __onResume();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onPause() method.
     */
    void __onPause();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onDestroy() method.
     */
    void __onDestroy();
    void onActivityFinish();
}
