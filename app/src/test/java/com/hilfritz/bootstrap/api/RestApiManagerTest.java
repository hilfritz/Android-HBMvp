package com.hilfritz.bootstrap.api;

import com.hilfritz.bootstrap.BuildConfig;
import com.hilfritz.bootstrap.api.pojo.places.PlacesWrapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Hilfritz Camallere on 15/3/17.
 * PC name herdmacbook1
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class RestApiManagerTest {
    RestApiManager restApiManager;

    @Before
    public void setup(){
        restApiManager = new RestApiManager();
    }

    @Test
    public void testPlacesApi(){
        final Call<PlacesWrapper> placesCall = restApiManager.getPlacesCall();
        Response<PlacesWrapper> result =  null;

        try {
            result =  placesCall.execute();
            assertNotNull(result);
            assertNotNull(result.body());
            assertNotNull(result.body().getPlaceList());
            assertTrue(result.body().getPlaceList().size()>0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
