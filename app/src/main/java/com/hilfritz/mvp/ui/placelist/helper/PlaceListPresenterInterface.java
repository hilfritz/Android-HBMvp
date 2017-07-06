package com.hilfritz.mvp.ui.placelist.helper;

import com.hilfritz.mvp.api.pojo.places.Place;
import com.hilfritz.mvp.framework.BasePresenterInterface;

/**
 * Created by home on 7/5/2017.
 */

public interface PlaceListPresenterInterface extends BasePresenterInterface {
    public void callPlaceListApi();
    public boolean isOnGoingRequest();
    public void onListItemClick(Place place);

}
