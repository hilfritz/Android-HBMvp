package com.hilfritz.mvp.eventbus.event;

import com.hilfritz.mvp.ui.contactlist.main.userlist.UserListPresenter;

/**
 * Created by Hilfritz P. Camallere on 7/2/2016.
 */

public class SortEvent {
    UserListPresenter.LOADING_TYPES loadingType;
    public SortEvent(UserListPresenter.LOADING_TYPES loadingType) {
        this.loadingType = loadingType;
    }

    public UserListPresenter.LOADING_TYPES getLoadingType() {
        return loadingType;
    }
}
