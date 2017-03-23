package com.hilfritz.mvp.ui.contactlist.main.userlist;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hilfritz.mvp.R;
import com.hilfritz.mvp.api.RestApiManager;
import com.hilfritz.mvp.api.pojo.UserWrapper;
import com.hilfritz.mvp.application.MyApplication;
import com.hilfritz.mvp.eventbus.deligate.UserListItemClickEventDeligate;
import com.hilfritz.mvp.framework.BaseActivity;
import com.hilfritz.mvp.framework.BaseFragment;
import com.hilfritz.mvp.framework.BasePresenter;
import com.hilfritz.mvp.framework.BasePresenterInterface;
import com.hilfritz.mvp.ui.loading.FullscreenLoadingDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Hilfritz P. Camallere on 6/4/2016.
 * The presenter for the fragment list
 */

public class UserListPresenter extends BasePresenter implements BasePresenterInterface{
    private static final String TAG = "UserListPresenter";
    Activity activity;
    UserListFragment fragment;
    Subscription loadUsersSubscription;
    Subscription sortUsersSubscription;
    List<UserWrapper> usersList = new ArrayList<UserWrapper>();
    public static final long DELAY = 1000;

    public enum LOADING_TYPES {
        REGULAR,
        SORTING_AZ,
        SORTING_ZA
    }

    @Inject RestApiManager apiManager;

    public UserListPresenter(MyApplication myApplication){
        //INITIALIZE INJECTION
        myApplication.getAppComponent().inject(this);
    }

    public void sort(LOADING_TYPES sortMode){
        logd( "sort() ");
        if (sortMode==LOADING_TYPES.SORTING_AZ){
            showLoading(LOADING_TYPES.SORTING_AZ);
            sortUsersSubscription = getSortAzsubscribable()
                    .subscribe(getSortSubscriber());
        }else if (sortMode==LOADING_TYPES.SORTING_ZA){
            showLoading(LOADING_TYPES.SORTING_ZA);
            sortUsersSubscription =getSortZasubscribable()
                    .subscribe(getSortSubscriber());
        }
    }

    public boolean sortAz(){
        logd("sortAz() ");
        Collections.sort(usersList, new Comparator<UserWrapper>() {
            @Override
            public int compare(UserWrapper lhs, UserWrapper rhs) {
                return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
            }
        });
        return true;
    }

    public Observable<Boolean> getSortAzsubscribable(){
        return Observable.just(sortAz())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .delay(UserListPresenter.DELAY, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                ;
    }
    public Subscriber<Boolean> getSortSubscriber(){
        logd("getSortSubscriber() ");
        return new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                sortUsersSubscription = null;
                FullscreenLoadingDialog.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                logd("getSortSubscriber() onNext()");
                FullscreenLoadingDialog.hideLoading();
                String message = fragment.getString(R.string.error_loading_listview2, e.getLocalizedMessage())+" "+fragment.getString(R.string.try_again);
                fragment.showMessage(message, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        populate();
                    }
                });
            }

            @Override
            public void onNext(Boolean aBoolean) {
                fragment.notifyDataSetChanged();
                fragment.showList();
                logd("getSortSubscriber() onNext()");
            }
        };
    }
    public Observable<Boolean> getSortZasubscribable(){
        return Observable.just(sortZa())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .delay(UserListPresenter.DELAY, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                ;

    }
    public boolean sortZa(){
        logd("sortZa() ");
        Collections.sort(usersList, new Comparator<UserWrapper>() {
            @Override
            public int compare(UserWrapper lhs, UserWrapper rhs) {
                return rhs.getName().toLowerCase().compareTo(lhs.getName().toLowerCase());
            }
        });
        return true;
    }




    public void populate() {
        Log.d(TAG, "populate()");
        fragment.notifyDataSetChanged();

        //CALL API
        showLoading(LOADING_TYPES.REGULAR);
        loadUsersSubscription = apiManager.getUsersSubscribable()
                .subscribe(getUsersSubscriber());
    }

    /**
     *
     * @param loadingType int
     *                    <ul>
     *                      <li>0 - no loading</li>
     *                      <li>1 - sort az loading</li>
     *                      <li>2 - sort za loading</li>
     *                    </ul>
     */
    public void showLoading(LOADING_TYPES loadingType){
        logd("showLoading():"+loadingType);
        switch (loadingType){
            case REGULAR:
                //loading from server
                fragment.showLoading(fragment.getString(R.string.loading_users));
                break;
            case SORTING_AZ:
                //sort az
                fragment.showLoading(fragment.getString(R.string.sort_az_load));
                break;
            case SORTING_ZA:
                //sort za
                fragment.showLoading(fragment.getString(R.string.sort_za_load));
                break;
            default:
                break;
        }
    }

    public void logd(String str){
        Log.d(TAG,TAG+">>"+str);
    }

    public Subscriber<List<UserWrapper>> getUsersSubscriber(){
        return new Subscriber<List<UserWrapper>>() {
            @Override
            public void onCompleted() {
                loadUsersSubscription = null;
                FullscreenLoadingDialog.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                // cast to retrofit.HttpException to get the response code
                if (e instanceof HttpException) {
                    HttpException response = (HttpException)e;
                    int code = response.code();
                }
                FullscreenLoadingDialog.hideLoading();
                logd ("getUsersSubscriber() onError() error loading");
                String message = fragment.getString(R.string.error_loading_listview, e.getLocalizedMessage())+" "+fragment.getString(R.string.try_again);
                fragment.showMessage(message, null);
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                /*
                fragment.showMessage(message, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        populate();
                    }
                });
                */
            }

            @Override
            public void onNext(List<UserWrapper> userList) {
                if (userList!=null && userList.size()>0){
                    showUsersList(userList);
                    logd("getUsersSubscriber() onNext() adapter.notifyDataSetChanged() userList.size():"+userList.size());
                }

                fragment.showList();
                logd("getUsersSubscriber() onNext()");
            }
        };
    }

    /**
     * THIS IS HOW TO PROPERLY POPULATE A LIST
     * <UL>
     *     <LI>CLEAR THE LIST, ADD THE NEW LIST TO THE EXISTING LIST</LI>
     *     <LI>CALL NOTIFYDATASETCHANGED</LI>
     *     <LI>CALL VIEW'S SHOWLIST METHOD</LI>
     * </UL>
     *
     * @param userList
     */
    private void showUsersList(List<UserWrapper> userList) {
        usersList.clear();
        usersList.addAll(userList);
        fragment.notifyDataSetChanged();
        fragment.listViewItemClick(usersList);
        fragment.showList();
    }

    public void onDestroy() {
        logd ("onDestroy()");
        /*
        if (loadUsersSubscription!=null && loadUsersSubscription.isUnsubscribed()==false){
            loadUsersSubscription.unsubscribe();
        }
        */
    }

    public boolean isOnGoingSortingUsers(){
        boolean retVal = false;
        if (sortUsersSubscription!=null && sortUsersSubscription.isUnsubscribed()==false){
            retVal = true;
        }
        return retVal;
    }

    public boolean isOnGoingLoadingUsers(){
        boolean retVal = false;
        if (loadUsersSubscription!=null && loadUsersSubscription.isUnsubscribed()==false){
            retVal = true;
        }
        return retVal;
    }

    @Override
    public void __fmwk_bpi_init(BaseActivity activity, BaseFragment fragment) {

        //INITIALIZE INJECTION
        ((MyApplication)(activity.getApplication())).getAppComponent().inject(this);
        this.activity = activity;
        this.fragment = (UserListFragment) fragment;

        if (__fmwk_bp_isInitialLoad()==true) {
            logd("bindToFragment() for new activity");
            usersList.clear();
            //FRAMEWORK
            /**
             * DO YOUR UI INTERFACE PROCESSES HERE, TAKE NOTE IF IT IS FROM ORIENTATION CHANGE OR
             * FROM A COMPLETELY NEW FRAGMENT
             * YOU HAVE TO CHECK IF INITIAL LOAD OR NOT
             */
            populate();
        }else{
            logd("bindToFragment() for orientation change");
        }

    }

    @Override
    public void __fmwk_bpi_init_new() {

    }

    @Override
    public void __fmwk_bpi_init_change() {

    }

    public List<UserWrapper> getUsersList() {
        return usersList;
    }

    @Override
    public void __fmwk_bpi_reset() {

    }

    @Override
    public void __fmwk_bpi_populate() {

    }

    public void onListItemClick(UserWrapper user){
        UserListItemClickEventDeligate.userlistItemClick(user);
    }
}
