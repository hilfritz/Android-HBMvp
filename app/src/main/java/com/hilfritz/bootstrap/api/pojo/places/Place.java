
package com.hilfritz.bootstrap.api.pojo.places;

import android.view.View;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Place {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("photo_url")
    @Expose
    private String photoUrl;

    public int __viewIsSelected = View.GONE;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int get__viewIsSelected() {
        return __viewIsSelected;
    }

    public void set__viewIsSelected(int __viewIsSelected) {
        this.__viewIsSelected = __viewIsSelected;
    }
}
