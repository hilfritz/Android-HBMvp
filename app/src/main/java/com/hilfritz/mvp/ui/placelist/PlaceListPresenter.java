package com.hilfritz.mvp.ui.placelist;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hilfritz.mvp.api.RestApiManager;
import com.hilfritz.mvp.api.pojo.places.Place;
import com.hilfritz.mvp.api.pojo.places.PlacesWrapper;
import com.hilfritz.mvp.application.MyApplication;
import com.hilfritz.mvp.framework.BaseActivity;
import com.hilfritz.mvp.framework.BaseFragment;
import com.hilfritz.mvp.framework.BasePresenter;
import com.hilfritz.mvp.framework.BasePresenterInterface;
import com.hilfritz.mvp.util.ConnectionUtil;
import com.hilfritz.mvp.util.ExceptionUtil;
import com.hilfritz.mvp.util.RxUtil;
import com.hilfritz.mvp.ui.placelist.view.PlaceListView;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Hilfritz Camallere on 15/3/17.
 * PC name herdmacbook1
 */

public class PlaceListPresenter extends BasePresenter implements BasePresenterInterface{
    public static final String TAG = "PlaceListPresenter";
    //PlaceListFragment fragment;
    Subscription placeListSubscription;
    PlaceListView view;
    Context context;

    @Inject
    RestApiManager apiManager;
    private ArrayList<Place> placeList = new ArrayList<Place>();

    public PlaceListPresenter(MyApplication myApplication){
        //INITIALIZE INJECTION
        myApplication.getAppComponent().inject(this);
    }

    @Override
    public void __fmwk_bpi_init(BaseActivity activity, BaseFragment fragment) {
        //this.fragment = (PlaceListFragment)fragment;
        this.view = new PlaceListView((PlaceListFragment)fragment);
        this.context = fragment.getContext();
        if (__fmwk_bp_isInitialLoad()){
            Log.d(TAG, "__fmwk_bpi_init:  new activity");
            __fmwk_bpi_init_new();
        }else{
            Log.d(TAG, "__fmwk_bpi_init: configuration/orientation change");
            __fmwk_bpi_init_change();
        }
    }

    @Override
    public void __fmwk_bpi_init_new() {
        Log.d(TAG, "__fmwk_bpi_init_new: init for new activity");
        placeList.clear();
        view.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void __fmwk_bpi_init_change() {
        Log.d(TAG, "__fmwk_bpi_init_change: configuration change");

    }

    @Override
    public void __fmwk_bpi_reset() {
        Log.d(TAG, "__fmwk_bpi_reset: ");
        RxUtil.unsubscribe(placeListSubscription);
    }

    @Override
    public void __fmwk_bpi_populate() {
        Log.d(TAG, "__fmwk_bpi_populate: ");
        callPlaceListApi();
    }

    public void callPlaceListApi(){
        Log.d(TAG, "callPlaceListApi: ");
        if (!ConnectionUtil.isNetworkAvailable(getContext())){
            Log.d(TAG, "callPlaceListApi: no network");
            view.showDialog("No Internet connection", android.R.drawable.ic_dialog_info, true, false);
            return;
        }
        view.showLoading(android.R.drawable.progress_horizontal, "Loading");
        placeListSubscription = apiManager.getPlacesSubscribable()
                .subscribe(new Subscriber<PlacesWrapper>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "callPlaceListApi: onCompleted: ");
                        view.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "callPlaceListApi: onError: ");
                        view.hideLoading();
                        e.printStackTrace();
                        if (ExceptionUtil.isNoNetworkException(e)){
                            Toast.makeText(getContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNext(PlacesWrapper placesWrapper) {
                        Log.d(TAG, "callPlaceListApi: onNext: ");
                        if (placesWrapper!=null){
                            if (placesWrapper.getPlace().size()>0){
                                getPlaceList().clear();
                                getPlaceList().addAll(placesWrapper.getPlace());
                                view.getAdapter().notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    public ArrayList<Place> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(ArrayList<Place> place) {
        this.placeList = place;
    }

    public void onListItemClick(Place place){
        Log.d(TAG, "onListItemClick: ");
        int newVisibility = View.GONE;
        if (place.get__viewIsSelected()==View.GONE)
            newVisibility = View.VISIBLE;
        if (place.get__viewIsSelected()==View.VISIBLE)
            newVisibility = View.GONE;

        place.set__viewIsSelected(newVisibility);
        final int i = getPlaceList().indexOf(place);
        view.getAdapter().notifyItemChanged(i);
        //fragment.getAdapter().notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }
}
