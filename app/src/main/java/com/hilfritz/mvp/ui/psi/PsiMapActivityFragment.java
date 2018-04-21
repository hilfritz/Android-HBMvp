package com.hilfritz.mvp.ui.psi;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.hilfritz.mvp.R;
import com.hilfritz.mvp.api.psi.PsiRestInterface;
import com.hilfritz.mvp.api.psi.pojo.PsiPojo;
import com.hilfritz.mvp.application.MyApplication;
import com.hilfritz.mvp.ui.dialog.SimpleDialog;
import com.hilfritz.mvp.ui.loading.FullscreenLoadingDialog;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PsiMapActivityFragment extends Fragment implements PsiMapContract.View {

    @Inject PsiMapContract.Presenter presenter;

    @Inject
    PsiRestInterface api;


    PsiMapContract.Model model;

    public PsiMapActivityFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //INITIALIZE INJECTION
        ((MyApplication)getActivity().getApplication()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_psi_map, container, false);
        ButterKnife.bind(this, view);
        model = new PsiMapContract.Model() {
            @Override
            public Observable<PsiPojo> getAllPsi() {
                return api.getAllPsi();
            }
        };
        presenter.init(this, model, Schedulers.io(), AndroidSchedulers.mainThread());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //presenter.populate();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void showLoading() {
        FullscreenLoadingDialog.showLoading(getFragmentManager(), getString(R.string.label_loading), R.drawable.ic_loading_svg, false);
    }

    @Override
    public void hideLoading() {
        FullscreenLoadingDialog.hideLoading();
    }

    @Override
    public void showDialogWithMessage(String str) {
        SimpleDialog simpleDialog = SimpleDialog.newInstance(str, android.R.drawable.ic_dialog_info, true, false);
        simpleDialog.show(getFragmentManager(), SimpleDialog.TAG);
    }

    @Override
    public void showMapWithData(PsiPojo psiPojo) {
        Toast.makeText(getActivity(), psiPojo.toString()+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getStringFromStringResId(int stringResID) {
        return getString(stringResID);
    }

    @OnClick(R.id.populateButton)
    public void onPopulateButtonClick(View view){
        presenter.populate();
    }
}
