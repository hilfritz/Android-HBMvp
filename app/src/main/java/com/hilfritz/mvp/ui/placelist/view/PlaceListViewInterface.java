package com.hilfritz.mvp.ui.placelist.view;

import android.support.v7.widget.RecyclerView;

import com.hilfritz.mvp.api.pojo.places.Place;
import com.hilfritz.mvp.framework.BaseViewInterface;
import com.hilfritz.mvp.ui.placelist.PlaceListPresenter;
import com.hilfritz.mvp.ui.placelist.helper.PlaceListAdapter;

import java.util.ArrayList;

/**
 * Created by Hilfritz Camallere on 31/3/17.
 * PC name herdmacbook1
 */

public interface PlaceListViewInterface  extends BaseViewInterface{

    PlaceListAdapter getAdapter();
    public void showList();
    public void reInitializeRecyclerView(ArrayList<Place> placeArrayList, PlaceListPresenter presenter);
    public void notifyDataSetChangedRecyeclerView();
    public void notifyDataSetChangedRecyeclerView(int index);
    public RecyclerView getRecyclerView();
    public boolean isNetworkAvailable();
    public int getScreenWidth();
    public int getImageWidthByColumnCount(int columnCount);
    public int getImageHeightByColumnCount(int columnCount);
}
