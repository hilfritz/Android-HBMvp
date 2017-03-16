package com.hilfritz.bootstrap.util.view;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hilfritz Camallere on 13/2/17.
 * PC name herdmacbook1
 */

public class ViewGroupUtil {
    public static ViewGroup getParent(View view) {
        return (ViewGroup)view.getParent();
    }

    public static void removeView(View view) {
        ViewGroup parent = getParent(view);
        if(parent != null) {
            parent.removeView(view);
        }
    }

    public static void replaceView(View currentView, View newView) {
        ViewGroup parent = getParent(currentView);
        if(parent == null) {
            return;
        }
        final int index = parent.indexOfChild(currentView);
        removeView(currentView);
        removeView(newView);
        parent.addView(newView, index);
    }
}
