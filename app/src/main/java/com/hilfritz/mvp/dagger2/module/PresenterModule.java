package com.hilfritz.mvp.dagger2.module;

import com.hilfritz.mvp.application.MyApplication;
import com.hilfritz.mvp.ui.contactlist.detail.UserDetailFragmentPresenter;
import com.hilfritz.mvp.ui.contactlist.main.userlist.UserListPresenter;
import com.hilfritz.mvp.ui.placelist.PlaceListPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hilfritz P. Camallere on 6/28/2016.
 */
@Module
public class PresenterModule {


    private final MyApplication myApplication;

    public PresenterModule(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    //FRAGMENTS HERE
    @Provides
    @Singleton
    UserDetailFragmentPresenter provideUserDetailPresenter(){
        return new UserDetailFragmentPresenter();
    }
    @Provides
    @Singleton
    UserListPresenter provideUserListPresenter(){
        return new UserListPresenter(myApplication);
    }

    @Provides
    @Singleton
    PlaceListPresenter providePlaceListPresenter(){
        return new PlaceListPresenter(myApplication);
    }

    //ACTIVITY HERE

}
