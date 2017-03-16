package com.hilfritz.mvp.dagger2.module;

import com.hilfritz.mvp.api.RestApiManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hilfritz P. Camallere on 6/4/2016.
 */
@Module
public class RestApiModule {
    @Provides
    @Singleton
    RestApiManager provideRestApi(){
        return new RestApiManager();
    }


}
