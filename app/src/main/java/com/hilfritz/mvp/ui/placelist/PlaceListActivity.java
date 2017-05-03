package com.hilfritz.mvp.ui.placelist;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.hilfritz.mvp.R;
import com.hilfritz.mvp.framework.BaseActivity;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import timber.log.Timber;


public class PlaceListActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        testTimezoneConversion();
    }

    public void testTimezoneConversion(){
        Date deviceTimezoneDependentDate = new Date();
        DateTime deviceTimezoneDependentDateTime = new DateTime(deviceTimezoneDependentDate);

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd hh:mm:ss");


        SimpleDateFormat simpleDateFormatWithZone = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        simpleDateFormatWithZone.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
        DateTimeZone dateTimeZone = DateTimeZone.forID("Asia/Singapore");


        String formatted = dt.format(deviceTimezoneDependentDate);

        Timber.d("testTimezoneConversion: deviceTimezoneDependentDate:"+formatted);
        Timber.d("testTimezoneConversion: deviceTimezoneDependentDateTime:"+dtf.print(deviceTimezoneDependentDateTime));
        Timber.d("testTimezoneConversion: deviceTimezoneDependentDateTime:"+deviceTimezoneDependentDateTime.getMillis()+" deviceTimezoneDependentDate:"+deviceTimezoneDependentDate.getTime());


        Timber.d("testTimezoneConversion: [SG] timezonedDate:"+simpleDateFormatWithZone.format(deviceTimezoneDependentDate));
        Timber.d("testTimezoneConversion: [SG] timezonedDateTime:"+deviceTimezoneDependentDateTime.withZone(dateTimeZone));
        Timber.d("testTimezoneConversion: [SG] [x]timezonedDate:"+deviceTimezoneDependentDate.getTime()+" timezonedDateTime:"+deviceTimezoneDependentDateTime.withZone(dateTimeZone).getMillis());

        // CONCLUSION, THEY HAVE THE SAME MILLIS EVEN IF DIFFERENT TIMEZONE
        // EVEN IF THE OTHER TIME IN OTHER TIMEZONES ARE AHEAD OR BEHIND IT WILL STILL RESULT TO THE SAME TIME IN MILLIS


    }

}
