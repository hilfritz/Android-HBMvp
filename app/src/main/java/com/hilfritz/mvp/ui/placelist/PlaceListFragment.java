package com.hilfritz.mvp.ui.placelist;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.hilfritz.mvp.R;
import com.hilfritz.mvp.api.RestApiInterface;
import com.hilfritz.mvp.api.pojo.places.Place;
import com.hilfritz.mvp.application.MyApplication;
import com.hilfritz.mvp.framework.BaseActivity;
import com.hilfritz.mvp.framework.BaseFragment;
import com.hilfritz.mvp.framework.helper.AppVisibilityInterface;
import com.hilfritz.mvp.ui.dialog.SimpleDialog;
import com.hilfritz.mvp.ui.loading.FullscreenLoadingDialog;
import com.hilfritz.mvp.ui.placelist.helper.PlaceListAdapter;
import com.hilfritz.mvp.ui.placelist.view.PlaceListViewInterface;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class PlaceListFragment extends BaseFragment implements PlaceListViewInterface {
    private static final String TAG = "PlaceListFragment";
    @Inject PlaceListPresenter presenter;

    @BindView(R.id.list_view)
    RecyclerView listView;

    @Inject
    RestApiInterface apiManager;

    PlaceListAdapter adapter;

    public PlaceListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApplication)getActivity().getApplication()).getAppComponent().inject(this);
        Timber.d("onCreate:");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_list, container, false);
        Timber.d("onCreateView:");
        ButterKnife.bind(this, view);
        presenter.setApiManager(apiManager);
        presenter.__init((BaseActivity) getActivity(), this, AndroidSchedulers.mainThread());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.d("onViewCreated: ");
        presenter.__populate();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy: ");
        presenter.__onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("onResume: ");
        presenter.__onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        Timber.d("onPause: ");
        presenter.__onPause();
    }


    @Override
    public PlaceListAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void showList() {

    }

    @Override
    public void reInitializeRecyclerView(ArrayList<Place> placeArrayList, PlaceListPresenter presenter) {
        adapter = new PlaceListAdapter(presenter.getPlaceList(), presenter);
        listView.setAdapter(getAdapter());
    }



    @Override
    public void notifyDataSetChangedRecyeclerView() {
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChangedRecyeclerView(int index) {
        getAdapter().notifyItemChanged(index);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return listView;
    }

    @Override
    public boolean isNetworkAvailable() {
        return ((BaseActivity)getActivity()).isNetworkAvailable();
    }

    @Override
    public int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        return displayMetrics.widthPixels;

    }

    @Override
    public int getImageWidthByColumnCount(int columnCount) {
        return 0;
    }

    @Override
    public int getImageHeightByColumnCount(int columnCount) {
        return 0;
    }

    @Override
    public void showPlaceDetailDialog(final Place place) {
        final Dialog dialog = new Dialog(getActivity());
        final View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_yes_no, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);
        final TextView viewById = (TextView) dialogView.findViewById(R.id.message);
        viewById.setText("Continue saving "+place.getName()+"?");
        dialogView.findViewById(R.id.left_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveClickPlaceDetailDialog(place, dialog);
            }
        });

        dialogView.findViewById(R.id.right_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelClickPlaceDetailDialog(place, dialog);
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void onSaveClickPlaceDetailDialog(Place place, Dialog dialog) {
        dialog.dismiss();
    }

    @Override
    public void onCancelClickPlaceDetailDialog(Place place, Dialog dialog) {
        dialog.dismiss();
    }

    @Override
    public void findViews() {

    }

    @Override
    public void initViews() {
        int screenWidth = getScreenWidth();
        int columnCount = 1;
        //HANDLE ORIENTATION CHANGE OF RECYCLERVIEW,
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            listView.setLayoutManager(llm);

        } else{
            columnCount = 2;
            listView.setLayoutManager(new GridLayoutManager(getActivity(), columnCount));
        }


        reInitializeRecyclerView(presenter.getPlaceList(), presenter);
        notifyDataSetChangedRecyeclerView();
    }



    @Override
    public void showLoading() {
        FullscreenLoadingDialog.showLoading(getFragmentManager(), "loading", false);
    }

    @Override
    public void hideLoading() {
        FullscreenLoadingDialog.hideLoading();
    }

    @Override
    public void showErrorFullScreen(String message) {

    }

    @Override
    public void showDialog(String tag, String message) {
        SimpleDialog simpleDialog = SimpleDialog.newInstance(message, android.R.drawable.ic_dialog_info, true, false);
        simpleDialog.show(getFragmentManager(), SimpleDialog.TAG);
    }

    @Override
    public void hideDialog(String tag) {
        Fragment prev = getFragmentManager().findFragmentByTag("fragment_dialog");
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            if (df.isHidden()==false) {
                df.dismiss();
            }
        }
    }

    @Override
    public boolean isFinishing() {
        return getActivity().isFinishing();
    }

    @Override
    public void setAppVisibilityHandler(AppVisibilityInterface appVisibilityInterface) {
        ((BaseActivity)getActivity()).setAppVisibilityHandler(appVisibilityInterface);
    }

    @Override
    public String getStringById(int stringId) {
        return getString(stringId);
    }


}
