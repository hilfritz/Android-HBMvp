package com.hilfritz.bootstrap.dagger2.module;


import com.hilfritz.bootstrap.util.ImageCache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CacheModule {
    @Singleton
    @Provides
    ImageCache provideImageCache(){
        return new ImageCache();
    }
}
