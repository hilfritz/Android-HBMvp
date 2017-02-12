package com.hilfritz.bootstrap.eventbus.event;

import com.hilfritz.bootstrap.api.pojo.UserWrapper;

/**
 * Created by Hilfritz P. Camallere on 7/2/2016.
 */

public class UserListItemClickEvent {
    UserWrapper userWrapper;

    public UserListItemClickEvent(UserWrapper userWrapper) {
        this.userWrapper = userWrapper;
    }

    public UserWrapper getUserWrapper() {
        return userWrapper;
    }

    public void setUserWrapper(UserWrapper userWrapper) {
        this.userWrapper = userWrapper;
    }
}
