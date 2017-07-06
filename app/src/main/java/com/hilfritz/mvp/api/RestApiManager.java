package com.hilfritz.mvp.api;

import com.hilfritz.mvp.api.pojo.UserWrapper;
import com.hilfritz.mvp.api.pojo.places.PlacesWrapper;


import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
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
    //public static final String PLACES_URL = "https://github.com/hilfritz/Android-HBMvp/blob/development/temp/places/places.json";

    //THE URL HERE IS AFTER PASTING THE URL FROM GITHUB TO RAWGIT.COM
    public static final String PLACES_URL = "https://rawgit.com/hilfritz/Android-HBMvp/development/temp/places/places.json";

    RestApiInterface api ;
    Retrofit retrofit;

    public RestApiManager() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient().newBuilder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())   //<<<----- EXCLUSIVE ONLY FOR THIS CLASS
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())  //very important for RXJAVA

                .build();
        api = retrofit.create(RestApiInterface.class);

    }

    public Observable<List<UserWrapper>> getUsersSubscribable(){
        return getApi().getUsersObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                ;
    }

    public Observable<PlacesWrapper> getPlacesSubscribable(){
        return getApi().getPlacesObservable();
    }

    public Call<PlacesWrapper> getPlacesCall(){
        return getApi().getPlacesCall();
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

    public RestApiInterface getApi() {
        return api;
    }
}
