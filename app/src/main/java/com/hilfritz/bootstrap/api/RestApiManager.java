package com.hilfritz.bootstrap.api;

import com.hilfritz.bootstrap.api.pojo.UserWrapper;
import com.hilfritz.bootstrap.view.contactlist.main.userlist.UserListPresenter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Hilfritz P. Camallere on 6/4/2016.
 * see https://guides.codepath.com/android/Consuming-APIs-with-Retrofit
 */

public class RestApiManager {
    public static final String BASE_URL = "http://jsonplaceholder.typicode.com";
    public static final String USERS_URL = BASE_URL+"/users";
    RestApiInterface api ;
    Retrofit retrofit;

    public RestApiManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())  //very important for RXJAVA
                .build();
        api = retrofit.create(RestApiInterface.class);

    }

    public Observable<List<UserWrapper>> getUsersSubscribable(){
        return api.getUsersObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .delay(UserListPresenter.DELAY, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                ;
    }

    /**
     *
     * @param subscription Subscription - this is the rxjava subscription object
     */
    public static void unsubscribe(Subscription subscription) {
        if (subscription!=null && subscription.isUnsubscribed()==false){
            subscription.unsubscribe();
        }
    }
}
