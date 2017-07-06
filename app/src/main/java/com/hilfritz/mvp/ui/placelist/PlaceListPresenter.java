package com.hilfritz.mvp.ui.placelist;

import android.view.View;

import com.hilfritz.mvp.api.RestApiInterface;
import com.hilfritz.mvp.api.RestApiManager;
import com.hilfritz.mvp.api.pojo.places.Place;
import com.hilfritz.mvp.api.pojo.places.PlacesWrapper;
import com.hilfritz.mvp.application.MyApplication;
import com.hilfritz.mvp.framework.BaseActivity;
import com.hilfritz.mvp.framework.BaseFragment;
import com.hilfritz.mvp.framework.BasePresenter;
import com.hilfritz.mvp.framework.BasePresenterInterface;
import com.hilfritz.mvp.framework.BasePresenterLifeCycleInterface;
import com.hilfritz.mvp.framework.helper.AppVisibilityInterface;
import com.hilfritz.mvp.ui.placelist.helper.PlaceListPresenterInterface;
import com.hilfritz.mvp.ui.placelist.view.PlaceListViewInterface;
import com.hilfritz.mvp.util.ExceptionUtil;
import com.hilfritz.mvp.util.RxUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Hilfritz Camallere on 15/3/17.
 * PC name herdmacbook1
 */

public class PlaceListPresenter extends BasePresenter implements PlaceListPresenterInterface, AppVisibilityInterface, BasePresenterLifeCycleInterface {
    public static final String TAG = "PlaceListPresenter";
    Subscription placeListSubscription;
    PlaceListViewInterface view;
    String dialogTag="dialogTag";
    Scheduler mainThread;
    RestApiInterface apiManager;

    private ArrayList<Place> placeList = new ArrayList<Place>();

    public PlaceListPresenter(){
    }
    public PlaceListPresenter(MyApplication myApplication){
        //INITIALIZE INJECTION
        myApplication.getAppComponent().inject(this);
    }

    @Override
    public void __init(BaseActivity activity, BaseFragment fragment, Scheduler mainThread) {
        this.mainThread = mainThread;
        Timber.tag(TAG);
        this.view = (PlaceListFragment)fragment;
        this.view.initViews();
        if (__isFirstTimeLoad()){
            Timber.d("__init:  new activity");
            __firstInit();
            this.view.setAppVisibilityHandler(this);
        }else{
            Timber.d("__init: configuration/orientation change");
            __isInitFromConfigurationChange();
        }
    }

    @Override
    public void __firstInit() {
        Timber.d("__firstInit: for new activity");
        placeList.clear();
        view.notifyDataSetChangedRecyeclerView();
    }

    @Override
    public void __isInitFromConfigurationChange() {
        Timber.d("__isInitFromConfigurationChange: configuration change");

    }

    @Override
    public void __populate() {
        Timber.d("__populate: ");
        callPlaceListApi();
        super.__populate();
    }
    @Override
    public boolean isOnGoingRequest() {
        return RxUtil.isSubscribed(placeListSubscription);
    }

    @Override
    public void __onResume() {
        Timber.d("__onResume: ");
    }

    @Override
    public void __onPause() {
        Timber.d("__onPause: ");
    }

    @Override
    public void __onDestroy() {

        Timber.d("__onDestroy: ");
        //THIS IS IMPORTANT, ONLY CANCEL/UNSUBSCRIBE YOUR PROCESSESS WHEN THE
        //ACTIVITY IS ACTUALLY FINISHING,
        //THIS WILL MAKE SURE YOUR PROCESSES ARE NOT CANCELLED WHEN THE DEVICE IS BEING ROTATED
        if (!view.isFinishing()) {
            return;
        }
        super.__onDestroy();
        RxUtil.unsubscribe(placeListSubscription);
        this.view = null;

    }

    @Override
    public void onActivityFinish() {

    }

    @Override
    public void callPlaceListApi(){
        Timber.d("callPlaceListApi: ");
        if (!view.isNetworkAvailable()){
            Timber.d("callPlaceListApi: no network");
            view.showDialog(dialogTag,"No Internet connection");
            return;
        }

        //IF PREVIOUSLY LOADED, DONT CALL THE API ANYMORE
        if (__isFirstTimeLoad()==false && getPlaceList().size()>0){
            view.hideLoading();
            return;
        }

        view.showLoading();
        placeListSubscription = getApiManager().getPlacesObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread)
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
                            view.showDialog(dialogTag,"No Internet connection");
                        }else{
                            view.showDialog(dialogTag,e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(PlacesWrapper placesWrapper) {
                        Timber.d("callPlaceListApi: onNext: ");
                        if (placesWrapper!=null){
                            if (placesWrapper.getPlace().size()>0){
                                getPlaceList().clear();
                                getPlaceList().addAll(placesWrapper.getPlace());
                                view.notifyDataSetChangedRecyeclerView();
                            }
                        }
                    }
                });
    }

    public ArrayList<Place> getPlaceList() {
        return placeList;
    }
    @Override
    public void onListItemClick(Place place){
        int newVisibility = View.GONE;
        if (place.get__viewIsSelected()==View.GONE) {
            newVisibility = View.VISIBLE;
            Timber.d("onListItemClick: "+place.getName()+" selected");

        }
        if (place.get__viewIsSelected()==View.VISIBLE) {
            newVisibility = View.GONE;
            Timber.d("onListItemClick: "+place.getName()+" not selected");
        }
        place.set__viewIsSelected(newVisibility);
        final int i = getPlaceList().indexOf(place);
        view.notifyDataSetChangedRecyeclerView(i);
    }


    @Override
    public void onAppSentToBackground() {
        Timber.d("onAppSentToBackground: ");
    }

    public PlaceListViewInterface getView() {
        return view;
    }

    public void setView(PlaceListViewInterface view) {
        this.view = view;
    }

    public RestApiInterface getApiManager() {
        return apiManager;
    }

    public void setApiManager(RestApiInterface apiManager) {
        this.apiManager = apiManager;
    }
}
