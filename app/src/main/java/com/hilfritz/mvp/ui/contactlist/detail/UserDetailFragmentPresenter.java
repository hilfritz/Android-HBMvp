package com.hilfritz.mvp.ui.contactlist.detail;

import android.app.Activity;

import com.google.gson.Gson;
import com.hilfritz.mvp.api.pojo.UserWrapper;
import com.hilfritz.mvp.application.MyApplication;
import com.hilfritz.mvp.framework.BaseActivity;
import com.hilfritz.mvp.framework.BaseFragment;
import com.hilfritz.mvp.framework.BasePresenter;
import com.hilfritz.mvp.framework.BasePresenterInterface;
import com.hilfritz.mvp.util.IntentUtil;

import javax.inject.Inject;

/**
 * Created by Hilfritz P. Camallere on 6/5/2016.
 */

public class UserDetailFragmentPresenter extends BasePresenter implements BasePresenterInterface{
    Activity activity;
    UserDetailActivityFragment fragment;
    BaseFragment baseFragment;

    @Inject
    Gson gson;

    public UserDetailFragmentPresenter() {
    }

    public UserDetailFragmentPresenter(Activity activity, UserDetailActivityFragment fragment) {
        this.activity = activity;
        this.fragment = fragment;
    }

    public void populate(UserWrapper user){
        fragment.populate(user);
    }

    @Override
    public void __fmwk_bpi_init(BaseActivity activity, BaseFragment fragment) {

        //INITIALIZE
        this.activity = activity;
        this.baseFragment = fragment;
        this.fragment = (UserDetailActivityFragment) fragment;
        ((MyApplication) activity.getApplication()).getAppComponent().inject(this);



        //CHECK IF THE CALLING ACTIVITY IS A NEWLY STARTED ACTIVITY
        //IF NEW ACTIVITY
        String userStr = IntentUtil.getStringIntentExtra(activity, UserDetailActivityFragment.EXTRA_USERWRAPPER);
        if (userStr != null) {
            UserWrapper user =  gson.fromJson( userStr, UserWrapper.class );
            populate(user);
        }else{
            //IF INSIDE THe MAIN ACTIVITY

        }

    }

    @Override
    public void __fmwk_bpi_init_new() {

    }

    @Override
    public void __fmwk_bpi_init_change() {

    }


    @Override
    public void __fmwk_bpi_populate() {

    }

    @Override
    public void __fmwk_bpi_resume() {

    }

    @Override
    public void __fmwk_bpi_pause() {

    }

    @Override
    public void __fmwk_bpi_destroy() {

    }
}
