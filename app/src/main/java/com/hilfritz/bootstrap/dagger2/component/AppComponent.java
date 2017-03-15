package com.hilfritz.bootstrap.dagger2.component;

import com.hilfritz.bootstrap.dagger2.module.CacheModule;
import com.hilfritz.bootstrap.dagger2.module.SessionModule;
import com.hilfritz.bootstrap.dagger2.module.UtilityModule;
import com.hilfritz.bootstrap.dagger2.module.PresenterModule;
import com.hilfritz.bootstrap.dagger2.module.RestApiModule;
import com.hilfritz.bootstrap.framework.BaseActivity;
import com.hilfritz.bootstrap.view.contactlist.detail.UserDetailActivity;
import com.hilfritz.bootstrap.view.contactlist.detail.UserDetailActivityFragment;
import com.hilfritz.bootstrap.view.contactlist.detail.UserDetailFragmentPresenter;
import com.hilfritz.bootstrap.view.contactlist.main.MainActivity;
import com.hilfritz.bootstrap.view.contactlist.main.userlist.UserListFragment;
import com.hilfritz.bootstrap.view.contactlist.main.userlist.UserListPresenter;
import com.hilfritz.bootstrap.view.dialog.SimpleDialog;
import com.hilfritz.bootstrap.view.placelist.PlaceListFragment;
import com.hilfritz.bootstrap.view.placelist.PlaceListPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Hilfritz P. Camallere on 6/4/2016.
 */
@Singleton
@Component(modules = {
        RestApiModule.class,
        PresenterModule.class,
        UtilityModule.class,
        SessionModule.class,
        CacheModule.class
})
public interface AppComponent {
    void inject(UserListFragment fragment);
    void inject(UserListPresenter presenter);

    void inject(UserDetailActivityFragment fragment);
    void inject(UserDetailFragmentPresenter presenter);

    void inject(PlaceListFragment fragment);
    void inject(PlaceListPresenter presenter);


    void inject(BaseActivity baseActivity);
    void inject(MainActivity activity);
    void inject(UserDetailActivity activity);
    void inject(SimpleDialog simpleDialog);

}
