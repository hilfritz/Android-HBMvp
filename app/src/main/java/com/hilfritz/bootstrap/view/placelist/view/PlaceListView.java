package com.hilfritz.bootstrap.view.placelist.view;

import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentManager;

import com.hilfritz.bootstrap.framework.BaseViewInterface;
import com.hilfritz.bootstrap.view.dialog.SimpleDialog;
import com.hilfritz.bootstrap.view.loading.FullscreenLoadingDialog;
import com.hilfritz.bootstrap.view.placelist.PlaceListFragment;
import com.hilfritz.bootstrap.view.placelist.helper.PlaceListAdapter;

/**
 * Created by Hilfritz Camallere on 16/3/17.
 * PC name herdmacbook1
 */

public class PlaceListView implements BaseViewInterface {

    PlaceListFragment fragment;
    FragmentManager fragmentManager;

    public PlaceListView(PlaceListFragment fragment) {
        this.fragment = fragment;
        this.fragmentManager = fragment.getFragmentManager();
    }


    @Override
    public void showLoading(@DrawableRes int icon, String message) {
        FullscreenLoadingDialog.showLoading(getFragmentManager(), message, icon, false);
    }

    @Override
    public void hideLoading() {
        FullscreenLoadingDialog.hideLoading();
    }

    @Override
    public void showErrorFullScreen(@DrawableRes int icon, String message) {

    }

    @Override
    public void showDialog(String message, @DrawableRes int iconId, boolean cancellable, boolean finishOnDismiss) {
        SimpleDialog simpleDialog = SimpleDialog.newInstance(message, iconId, true, false);
        simpleDialog.show(getFragmentManager(), SimpleDialog.TAG);
    }

    @Override
    public void refresh() {

    }

    public PlaceListFragment getFragment() {
        return fragment;
    }

    public void setFragment(PlaceListFragment fragment) {
        this.fragment = fragment;
    }

    public PlaceListAdapter getAdapter(){
        return fragment.getAdapter();
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}
