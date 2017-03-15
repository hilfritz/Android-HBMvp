
package com.hilfritz.bootstrap.api.pojo.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlacesWrapper {

    @SerializedName("place_list")
    @Expose
    private List<PlaceList> placeList = null;

    public List<PlaceList> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<PlaceList> placeList) {
        this.placeList = placeList;
    }

}
