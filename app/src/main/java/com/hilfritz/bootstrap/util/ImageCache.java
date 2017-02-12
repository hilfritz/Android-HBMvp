package com.hilfritz.bootstrap.util;

import android.content.Context;
import android.util.Log;

import com.hilfritz.bootstrap.application.MyApplication;

import java.io.File;

/**
 * Created by Hilfritz Camallere on 21/3/16.
 */
public class ImageCache {
    private static final String TAG = "ImageCache";
    public static final String DIRECTORY_IMAGE_CACHE_DIR = "image";
    public static final String REPORTING_OFFICER_IMAGE_CACHE_DIR = "reportingOfficer";
    File cacheDir;
    File imageDir;
    File reportingOfficerDir;

    public ImageCache(){

        //cacheDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //cacheDir = Environment.getExternalStorageDirectory().getAbsoluteFile();
        File cacheDir = MyApplication.getInstance().getDir("HerdMe", Context.MODE_PRIVATE);

        if (cacheDir.exists()==false){
            if (cacheDir.mkdirs()){
                Log.d(TAG, "ImageCache: cache directory created");
            }else{
                Log.d(TAG, "ImageCache: cache directory cannot be created");
            }
        }

        imageDir = new File(cacheDir, DIRECTORY_IMAGE_CACHE_DIR);
        if (imageDir.exists()==false){
            if (imageDir.mkdirs()){
                Log.d(TAG, "ImageCache: imageDir directory created");
            }else{
                Log.d(TAG, "ImageCache: imageDir directory cannot be created");
            }
        }

        reportingOfficerDir = new File(imageDir, REPORTING_OFFICER_IMAGE_CACHE_DIR);
        if (reportingOfficerDir.exists()==false){
            if (reportingOfficerDir.mkdirs()){
                Log.d(TAG, "ImageCache: reportingOfficerDir directory created");
            }else{
                Log.d(TAG, "ImageCache: reportingOfficerDir directory cannot be created");
            }
        }
    }




    public File getReportingOfficerCacheDirectory(Context context){
        return reportingOfficerDir;
    }





}
