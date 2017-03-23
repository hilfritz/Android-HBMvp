package com.hilfritz.mvp.ui.placelist;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hilfritz.mvp.R;
import com.hilfritz.mvp.application.MyApplication;
import com.hilfritz.mvp.framework.BaseActivity;
import com.hilfritz.mvp.framework.BaseFragment;
import com.hilfritz.mvp.framework.BaseFragmentInterface;
import com.hilfritz.mvp.ui.placelist.helper.PlaceListAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class PlaceListFragment extends BaseFragment implements BaseFragmentInterface{
    private static final String TAG = "PlaceListFragment";
    @Inject PlaceListPresenter presenter;

    @BindView(R.id.list_view)
    RecyclerView listView;

    PlaceListAdapter adapter;

    public PlaceListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApplication)getActivity().getApplication()).getAppComponent().inject(this);
        Timber.d("onCreate:");
        __fmwk_bf_checkIfNewActivity(savedInstanceState, presenter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_list, container, false);
        Timber.d("onCreateView:");
        ButterKnife.bind(this, view);
        __fmwk_bfi_init_views();
        presenter.__fmwk_bpi_init((BaseActivity) getActivity(), this);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.d("onViewCreated: ");
        presenter.__fmwk_bpi_populate();
    }


    @Override
    public void __fmwk_bfi_init_views() {
        Timber.d("__fmwk_bfi_init_views:");
        //INITIALIZE VIEWS HERE

        //HANDLE ORIENTATION CHANGE OF RECYCLERVIEW,
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            listView.setLayoutManager(llm);
        } else{
            listView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }

        //INITIALIZE THE ADAPTERS
        adapter = new PlaceListAdapter(presenter.getPlaceList(), presenter);
        listView.setAdapter(getAdapter());
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy: ");
        presenter.__fmwk_bpi_reset();
    }

    public PlaceListAdapter getAdapter() {
        return adapter;
    }

}
