package com.hilfritz.bootstrap.view.placelist;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hilfritz.bootstrap.api.RestApiManager;
import com.hilfritz.bootstrap.api.pojo.places.Place;
import com.hilfritz.bootstrap.api.pojo.places.PlacesWrapper;
import com.hilfritz.bootstrap.application.MyApplication;
import com.hilfritz.bootstrap.framework.BaseActivity;
import com.hilfritz.bootstrap.framework.BaseFragment;
import com.hilfritz.bootstrap.framework.BasePresenter;
import com.hilfritz.bootstrap.framework.BasePresenterInterface;
import com.hilfritz.bootstrap.util.ConnectionUtil;
import com.hilfritz.bootstrap.util.ExceptionUtil;
import com.hilfritz.bootstrap.util.RxUtil;

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
    PlaceListFragment fragment;
    Subscription placeListSubscription;

    @Inject
    RestApiManager apiManager;
    private ArrayList<Place> place = new ArrayList<Place>();

    public PlaceListPresenter(MyApplication myApplication){
        //INITIALIZE INJECTION
        myApplication.getAppComponent().inject(this);
    }

    @Override
    public void __fmwk_bpi_init(BaseActivity activity, BaseFragment fragment) {
        this.fragment = (PlaceListFragment)fragment;
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
        place.clear();
        fragment.getAdapter().notifyDataSetChanged();
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
        if (!ConnectionUtil.isNetworkAvailable(fragment.getContext())){
            Log.d(TAG, "callPlaceListApi: no network");
            fragment.showDialog("No Internet connection", android.R.drawable.ic_dialog_info, true, false);
            return;
        }
        fragment.showLoading(android.R.drawable.progress_horizontal, "Loading");
        placeListSubscription = apiManager.getPlacesSubscribable()
                .subscribe(new Subscriber<PlacesWrapper>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "callPlaceListApi: onCompleted: ");
                        fragment.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "callPlaceListApi: onError: ");
                        fragment.hideLoading();
                        e.printStackTrace();
                        if (ExceptionUtil.isNoNetworkException(e)){
                            Toast.makeText(fragment.getActivity(), "No Internet connection", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(fragment.getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNext(PlacesWrapper placesWrapper) {
                        Log.d(TAG, "callPlaceListApi: onNext: ");
                        if (placesWrapper!=null){
                            if (placesWrapper.getPlace().size()>0){
                                getPlace().clear();
                                getPlace().addAll(placesWrapper.getPlace());
                                fragment.getAdapter().notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    public ArrayList<Place> getPlace() {
        return place;
    }

    public void setPlace(ArrayList<Place> place) {
        this.place = place;
    }

    public void onListItemClick(Place place){
        Log.d(TAG, "onListItemClick: ");
        int newVisibility = View.GONE;
        if (place.get__viewIsSelected()==View.GONE)
            newVisibility = View.VISIBLE;
        if (place.get__viewIsSelected()==View.VISIBLE)
            newVisibility = View.GONE;

        place.set__viewIsSelected(newVisibility);
        final int i = getPlace().indexOf(place);
        fragment.getAdapter().notifyItemChanged(i);
        //fragment.getAdapter().notifyDataSetChanged();
    }
}
