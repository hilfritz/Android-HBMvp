package com.hilfritz.mvp.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hilfritz.mvp.dagger2.component.AppComponent;
import com.hilfritz.mvp.dagger2.component.DaggerAppComponent;
import com.hilfritz.mvp.dagger2.module.CacheModule;
import com.hilfritz.mvp.dagger2.module.SessionModule;
import com.hilfritz.mvp.dagger2.module.UtilityModule;
import com.hilfritz.mvp.dagger2.module.PresenterModule;
import com.hilfritz.mvp.dagger2.module.RestApiModule;

/**
 * Created by Hilfritz P. Camallere on 6/4/2016.
 */

public class MyApplication extends Application {
    AppComponent appComponent;
    private static MyApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initializeDagger();
        Fresco.initialize(this);
    }

    private void initializeDagger() {
        appComponent = DaggerAppComponent.builder()
                .restApiModule(new RestApiModule())
                .presenterModule(new PresenterModule(this))
                .sessionModule(new SessionModule(this))
                .utilityModule(new UtilityModule())
                .cacheModule(new CacheModule())
                .build();

    }
    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    public AppComponent getAppComponent() {
        return appComponent;
    }


}
