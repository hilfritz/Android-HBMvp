package com.hilfritz.mvp.ui.placelist;

import android.content.Context;
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
import com.hilfritz.mvp.ui.placelist.view.PlaceListView;
import com.hilfritz.mvp.util.ConnectionUtil;
import com.hilfritz.mvp.util.ExceptionUtil;
import com.hilfritz.mvp.util.RxUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

/**
 * Created by Hilfritz Camallere on 15/3/17.
 * PC name herdmacbook1
 */

public class PlaceListPresenter extends BasePresenter implements BasePresenterInterface{
    public static final String TAG = "PlaceListPresenter";
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
        this.context = fragment.getContext();
        Timber.tag(TAG);
        if (this.view == null){
            this.view = new PlaceListView((PlaceListFragment)fragment);
        }
        this.view.bindToFragment((PlaceListFragment)fragment);
        if (__fmwk_bp_isInitialLoad()){
            Timber.d("__fmwk_bpi_init:  new activity");
            __fmwk_bpi_init_new();
        }else{
            Timber.d("__fmwk_bpi_init: configuration/orientation change");
            __fmwk_bpi_init_change();
        }
    }

    @Override
    public void __fmwk_bpi_init_new() {
        Timber.d("__fmwk_bpi_init_new: for new activity");
        placeList.clear();
        view.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void __fmwk_bpi_init_change() {
        Timber.d("__fmwk_bpi_init_change: configuration change");

    }

    @Override
    public void __fmwk_bpi_populate() {
        Timber.d("__fmwk_bpi_populate: ");
        if (__fmwk_bp_isFromRotation()){
            //IMPORTANT: CHECK
            Timber.d("__fmwk_bpi_populate: rotation detected.");
            return;
        }
        callPlaceListApi();
    }

    @Override
    public void __fmwk_bpi_resume() {
        Timber.d("__fmwk_bpi_resume: ");
    }

    @Override
    public void __fmwk_bpi_pause() {
        Timber.d("__fmwk_bpi_pause: ");
    }

    @Override
    public void __fmwk_bpi_destroy() {
        Timber.d("__fmwk_bpi_destroy: ");
        RxUtil.unsubscribe(placeListSubscription);
    }

    public void callPlaceListApi(){
        Timber.d("callPlaceListApi: ");
        if (!ConnectionUtil.isNetworkAvailable(getContext())){
            Timber.d("callPlaceListApi: no network");
            view.showDialog("No Internet connection", android.R.drawable.ic_dialog_info, true, false);
            return;
        }
        view.showLoading(android.R.drawable.progress_horizontal, "Loading");
        placeListSubscription = apiManager.getPlacesSubscribable()
                .subscribe(new Subscriber<PlacesWrapper>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("callPlaceListApi: onCompleted: ");
                        view.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("callPlaceListApi: onError: ");
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
                        Timber.d("callPlaceListApi: onNext: ");
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
        Timber.d("onListItemClick: ");
        int newVisibility = View.GONE;
        if (place.get__viewIsSelected()==View.GONE)
            newVisibility = View.VISIBLE;
        if (place.get__viewIsSelected()==View.VISIBLE)
            newVisibility = View.GONE;

        place.set__viewIsSelected(newVisibility);
        final int i = getPlaceList().indexOf(place);
        Timber.d("onListItemClick: i:"+i);
        view.getAdapter().notifyItemChanged(i);
        //fragment.getAdapter().notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }
}
