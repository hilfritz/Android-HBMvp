package com.hilfritz.bootstrap.view.loading;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.hilfritz.bootstrap.R;
import com.hilfritz.bootstrap.eventbus.event.DialogEvent;
import com.hilfritz.bootstrap.eventbus.event.SortEvent;
import com.hilfritz.bootstrap.util.StringUtil;
import com.hilfritz.bootstrap.view.contactlist.main.userlist.UserListPresenter;
import com.squareup.otto.Subscribe;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hilfritz Camallere on 11/11/16.
 */

public class FullscreenLoadingDialog extends DialogFragment {

    public static final String TAG = "LoadingDialog";
    private static final String ARG_MESSAGE = "param1";
    private static final String ARG_ICONID = "param2";
    private static final String ARG_CANCELLABLE = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";
    private static final String ARG_PARAM7 = "param7";

    public Context c;
    public Dialog d;

    @BindView(R.id.message)
    public TextView message;


    @BindView(R.id.rounded_img)
    SimpleDraweeView icon;

    int dialogType = 0;
    //@Inject
    //public GPSTracker gpsTracker;

    /**
     * TO REMOVE THE TITLE SPACE
     * see http://stackoverflow.com/questions/15277460/how-to-create-a-dialogfragment-without-title
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Log.d(TAG, "onCreateDialog: ");

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE); //REMOVES THE TITLE OF THE DIALOG
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);  //REMOVES THE DEFAULT DIALOG BACKGROUND
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate( R.layout.dialog_loading_fullscreen, container, false);
        ButterKnife.bind(this, view);

        loadDefaultLoadingGif();

        if (getArguments() != null) {
            String message = getArguments().getString(ARG_MESSAGE);
            setMessage(message);
            int iconId = getArguments().getInt(ARG_ICONID);
            setDrawableId(iconId);
            setCancelable(getArguments().getBoolean(ARG_CANCELLABLE, true));



        }

        return view;
    }

    private void loadDefaultLoadingGif() {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(R.raw.gear_gif).build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(imageRequest.getSourceUri())
                .setAutoPlayAnimations(true)

        .build();
        icon.setController(controller);
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN)
    public void onDialogEvent(DialogEvent e) {
        Log.d(TAG, "onDialogEvent: ");
        if (e.getEventType()==DialogEvent.CLOSE){
            Log.d(TAG, "onDialogEvent: closing dialog");
            dismiss();
        }
    }
    public void setMessage(String message){
        Log.d(TAG, "setMessage: ");
        if (!StringUtil.isStringEmpty(message)) {
            this.message.setText(message);
        }
    }

    public void setDrawableId(int drawableId){
        Log.d(TAG, "setDrawableId: ");
        //icon.setImageURI(FrescoUtil.getUriFromDrawableId(drawableId));
    }

    /**
     * Simple cancellable/non-cancellable dialogs
     * @return
     */
    public static FullscreenLoadingDialog newInstance() {
        FullscreenLoadingDialog fragment = new FullscreenLoadingDialog();
        Bundle args = new Bundle();
        args.putBoolean(ARG_CANCELLABLE, false);
        fragment.setArguments(args);
        return fragment;
    }
    public static FullscreenLoadingDialog newInstance(String message, int iconDrawableId, boolean isCancellable) {
        FullscreenLoadingDialog fragment = new FullscreenLoadingDialog();
        Bundle args = new Bundle();
        args.putBoolean(ARG_CANCELLABLE, isCancellable);
        args.putInt(ARG_ICONID, iconDrawableId);
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }
    public static FullscreenLoadingDialog newInstance(String message, boolean isCancellable) {
        FullscreenLoadingDialog fragment = new FullscreenLoadingDialog();
        Bundle args = new Bundle();
        args.putBoolean(ARG_CANCELLABLE, isCancellable);
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    public static void showLoading(FragmentManager fragmentManager){
        FullscreenLoadingDialog cd = FullscreenLoadingDialog.newInstance();
        cd.show(fragmentManager, FullscreenLoadingDialog.TAG);
    }

    public static void showLoading(FragmentManager fragmentManager, String message, int iconDrawableRes, boolean isCancellable){
        FullscreenLoadingDialog cd = FullscreenLoadingDialog.newInstance(message, iconDrawableRes, isCancellable);
        cd.show(fragmentManager, FullscreenLoadingDialog.TAG);
    }
    public static void showLoading(FragmentManager fragmentManager, String message, boolean isCancellable){
        FullscreenLoadingDialog cd = FullscreenLoadingDialog.newInstance(message, isCancellable);
        cd.show(fragmentManager, FullscreenLoadingDialog.TAG);
    }


    @Override
    public void onResume() {
        Log.d(TAG,"onResume: ");
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    public static final void hideLoading(){
        DialogEvent.fireEvent(DialogEvent.CLOSE);
    }

}
