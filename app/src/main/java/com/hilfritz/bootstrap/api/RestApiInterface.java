package com.hilfritz.bootstrap.api;

import com.hilfritz.bootstrap.api.pojo.UserWrapper;
import com.hilfritz.bootstrap.api.pojo.places.PlacesWrapper;

import java.util.List;

import retrofit2.Call;
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

    @GET(RestApiManager.PLACES_URL)
    Observable<PlacesWrapper> getPlacesObservable();
    @GET(RestApiManager.PLACES_URL)
    Call<PlacesWrapper> getPlacesCall();

}
