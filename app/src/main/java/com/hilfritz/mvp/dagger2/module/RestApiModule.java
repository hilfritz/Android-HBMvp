package com.hilfritz.mvp.dagger2.module;

import com.hilfritz.mvp.api.RestApiInterface;
import com.hilfritz.mvp.api.RestApiManager;
import com.hilfritz.mvp.api.psi.PsiRestApi;
import com.hilfritz.mvp.api.psi.PsiRestInterface;

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
    RestApiInterface provideRestApi(){
        return new RestApiManager();
    }

    @Provides
    @Singleton
    PsiRestInterface providePsiApi(){
        return new PsiRestApi();
    }

}
