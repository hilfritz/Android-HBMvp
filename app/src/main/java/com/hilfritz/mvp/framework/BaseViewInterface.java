package com.hilfritz.mvp.framework;

import com.hilfritz.mvp.framework.helper.AppVisibilityInterface;

/**
 *
 * @author by Hilfritz P. Camallere on 7/2/2016.
 * This is the basic interface for all Views in this project's MVP code structure
 */

public interface BaseViewInterface {

    public void findViews();
    public void initViews();
    public void showLoading();
    public void hideLoading();
    public void showErrorFullScreen(String message);
    public void showDialog(String tag, String message);
    public void hideDialog(String tag, String message);
    public boolean isFinishing();
    public void setAppVisibilityHandler(AppVisibilityInterface appVisibilityInterface);
}
