package com.hilfritz.bootstrap.util;

import android.util.Log;

/**
 * Created by hilfritz on 30/3/16.
 * @see http://stackoverflow.com/questions/7606077/how-to-display-long-messages-in-logcat
 * this logger class helps prevent string being cut when string is too long
 */
public class Logger {
    public static final String API_REQUEST = "#API-REQUEST";
    public static final String SESSION_DATA = "#SESSION-DATA";
    public static final void logd(String tag, String message){
        Logger.d(tag, message);
    }
    public static void d(String TAG, String message) {
        int maxLogSize = 1000;
        for(int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            Log.d(TAG, message.substring(start, end));
        }
    }
}
