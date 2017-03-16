package com.hilfritz.mvp.eventbus.deligate;

import com.hilfritz.mvp.api.pojo.UserWrapper;
import com.hilfritz.mvp.eventbus.event.UserListItemClickEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Hilfritz P. Camallere on 7/2/2016.
 */
public class UserListItemClickEventDeligate {
    public static void userlistItemClick(UserWrapper userWrapper){
        EventBus.getDefault().post(new UserListItemClickEvent(userWrapper));
    }
}
