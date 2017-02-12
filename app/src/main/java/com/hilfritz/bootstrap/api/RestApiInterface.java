package com.hilfritz.bootstrap.api;

import com.hilfritz.bootstrap.api.pojo.UserWrapper;


import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Hilfritz P. Camallere on 6/4/2016.
 */

public interface RestApiInterface {
    @GET(RestApiManager.USERS_URL)
    List<UserWrapper> getUsers();

    @GET(RestApiManager.USERS_URL)
    Observable<List<UserWrapper>> getUsersObservable();
}
