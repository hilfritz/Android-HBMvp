package com.hilfritz.mvp.framework;

/**
 * @author by Hilfritz P. Camallere on 7/2/2016.
 */

public abstract class BasePresenter implements BasePresenterInterface {
    private static final String TAG = "BasePresenter";
    private int populateCounter = 0;
    PRESENTER_CREATION presenterCreation = PRESENTER_CREATION.PRESENTER_NEW;

    public enum PRESENTER_CREATION{
        PRESENTER_NEW,
        PRESENTER_REUSE
    };

    @Override
    public void __populate() {
        populateCounter++;
        if (populateCounter>1){
            presenterCreation = PRESENTER_CREATION.PRESENTER_REUSE;
        }
    }

    @Override
    public void __onDestroy() {
        populateCounter = 0;
    }

    public void __setForRefresh(){
        populateCounter = 0;
    }
    public boolean __isFirstTimeLoad(){
        if (populateCounter>0){
            return false;
        }
        return true;
    }
}
