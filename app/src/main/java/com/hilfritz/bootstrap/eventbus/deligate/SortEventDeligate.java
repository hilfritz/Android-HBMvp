package com.hilfritz.bootstrap.eventbus.deligate;

import com.hilfritz.bootstrap.eventbus.event.SortEvent;
import com.hilfritz.bootstrap.view.contactlist.main.userlist.UserListPresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Hilfritz P. Camallere on 7/2/2016.
 */

public class SortEventDeligate {
    public static void sortAz(){
        EventBus.getDefault().post(new SortEvent(UserListPresenter.LOADING_TYPES.SORTING_AZ));
    }
    public static void sortZa(){
        EventBus.getDefault().post(new SortEvent(UserListPresenter.LOADING_TYPES.SORTING_ZA));
    }
}
