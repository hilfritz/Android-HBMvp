package com.hilfritz.mvp.framework;

import rx.Scheduler;

/**
 * @author  Hilfritz P. Camallere on 8/20/2016.
 */
public interface BasePresenterInterface {
    void __init(BaseActivity activity, BaseViewInterface view, Scheduler mainThred);
    void __firstInit();
    void __isInitFromConfigurationChange();
    void __populate();
    void __onDestroy();
}
