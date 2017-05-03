package com.hilfritz.mvp.ui.contactlist.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.hilfritz.mvp.R;
import com.hilfritz.mvp.api.pojo.UserWrapper;
import com.hilfritz.mvp.application.MyApplication;
import com.hilfritz.mvp.framework.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    Gson gson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        (((MyApplication) getApplication()).getAppComponent()).inject(this);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void start(Context activity, UserWrapper user, Gson gson){
        Intent intent = new Intent(activity, UserDetailActivity.class);
        intent.putExtra(UserDetailActivityFragment.EXTRA_USERWRAPPER, gson.toJson(user));
        activity.startActivity(intent);
    }

}
