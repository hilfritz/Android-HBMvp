package com.hilfritz.bootstrap.view.placelist;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.hilfritz.bootstrap.R;
import com.hilfritz.bootstrap.framework.BaseActivity;


public class PlaceListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
