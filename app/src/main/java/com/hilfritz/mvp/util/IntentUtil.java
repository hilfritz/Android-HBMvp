package com.hilfritz.mvp.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Hilfritz Camallere on 19/7/16.
 */
public class IntentUtil {
    public static void sendEmailToTechSupport(String receiver, String subject, String body, Context context){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + receiver));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        //emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text
        context.startActivity(Intent.createChooser(emailIntent, "Choose Email app"));
    }

    public static void sendEmailToTechSupport(String subject, String body, Context context){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "help.android@herdhr.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        //emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text
        context.startActivity(Intent.createChooser(emailIntent, "Choose Email app"));
    }

    public static void addSmsIntentNoPicker(Context context, String mobile){
        Uri sms_uri = Uri.parse("smsto:"+mobile);
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
        context.startActivity(sms_intent);
    }
    /**
     *
     * @param activity Activity
     * @param tag String tag
     * @return String returns the extra intent with the tag <b>tag</b>, null if not present
     */
    public static String getStringIntentExtra(Activity activity, String tag){
        String retVal = null;
        if (activity.getIntent()!=null){
            if (activity.getIntent().getStringExtra(tag)!=null){
                retVal = activity.getIntent().getStringExtra(tag);
            }
        }
        return retVal;
    }


    public static int getIntIntentExtra(Activity activity, String key, int defaultValue){
        int retVal = defaultValue;
        if (activity.getIntent()!=null){
            retVal = activity.getIntent().getIntExtra(key, defaultValue);
        }
        return retVal;
    }
}
