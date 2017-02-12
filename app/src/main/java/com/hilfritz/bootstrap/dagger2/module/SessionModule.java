package com.hilfritz.bootstrap.dagger2.module;

import android.content.Context;

import com.hilfritz.bootstrap.application.MyApplication;

import java.util.UUID;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hilfritz P. Camallere on 7/2/2016.
 */
@Module
public class SessionModule {
    MyApplication myApplication;
    Context context;

    String sessionUuid = null;


    public SessionModule(MyApplication myApplication) {
        this.myApplication = myApplication;
        sessionUuid = UUID.randomUUID().toString()+"<>"+System.currentTimeMillis();
    }



}
