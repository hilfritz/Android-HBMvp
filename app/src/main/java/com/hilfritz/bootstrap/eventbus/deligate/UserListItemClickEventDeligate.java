package com.hilfritz.bootstrap.eventbus.deligate;

import com.hilfritz.bootstrap.api.pojo.UserWrapper;
import com.hilfritz.bootstrap.eventbus.event.UserListItemClickEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Hilfritz P. Camallere on 7/2/2016.
 */
public class UserListItemClickEventDeligate {
    public static void userlistItemClick(UserWrapper userWrapper){
        EventBus.getDefault().post(new UserListItemClickEvent(userWrapper));
    }
}
