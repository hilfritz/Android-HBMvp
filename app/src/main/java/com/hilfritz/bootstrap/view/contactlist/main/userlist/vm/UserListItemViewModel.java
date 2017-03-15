package com.hilfritz.bootstrap.view.contactlist.main.userlist.vm;

/**
 * Created by Hilfritz Camallere on 13/3/17.
 * PC name herdmacbook1
 */

public class UserListItemViewModel {
    String name = "";
    String firstName = "";
    String lastName = "";
    String email = "";

    public UserListItemViewModel(String firstName, String lastName, String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.name = firstName+" "+lastName;
        this.email = email;
    }
    public UserListItemViewModel(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
