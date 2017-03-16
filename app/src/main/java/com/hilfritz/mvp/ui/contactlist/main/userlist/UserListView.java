package com.hilfritz.mvp.ui.contactlist.main.userlist;

import android.view.View;

import com.hilfritz.mvp.framework.BaseViewInterface;

/**
 * Created by Hilfritz P. Camallere on 6/28/2016.
 */

public interface UserListView extends BaseViewInterface {

    public void showMessage(String str, View.OnClickListener clickListener);
    public void showLoading(String str);
    public void showList();
}