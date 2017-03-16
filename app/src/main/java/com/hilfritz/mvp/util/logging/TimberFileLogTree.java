package com.hilfritz.mvp.util.logging;

import android.util.Log;

import timber.log.Timber;

/**
 * Created by Hilfritz Camallere on 27/2/17.
 * PC name herdmacbook1
 * @see http://www.sureshjoshi.com/mobile/file-logging-in-android-with-timber/
 */

public class TimberFileLogTree  extends Timber.DebugTree{
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (priority == Log.VERBOSE) {
            return;
        }

        String logMessage = tag + ": " + message;
        switch (priority) {
            case Log.DEBUG:
                //mLogger.debug(logMessage);
                break;
            case Log.INFO:
                //mLogger.info(logMessage);
                break;
            case Log.WARN:
                //mLogger.warn(logMessage);
                break;
            case Log.ERROR:
                //mLogger.error(logMessage);
                break;
        }
    }
}
