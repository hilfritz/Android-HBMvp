
package com.hilfritz.mvp.api.pojo.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlacesWrapper {
    public PlacesWrapper() {
    }

    public PlacesWrapper(List<Place> place) {

        this.place = place;
    }

    @SerializedName("place_list")
    @Expose
    private List<Place> place = null;

    public List<Place> getPlace() {
        return place;
    }

    public void setPlace(List<Place> place) {
        this.place = place;
    }

}
