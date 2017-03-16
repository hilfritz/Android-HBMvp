package com.hilfritz.mvp.ui.placelist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hilfritz.mvp.R;
import com.hilfritz.mvp.application.MyApplication;
import com.hilfritz.mvp.framework.BaseActivity;
import com.hilfritz.mvp.framework.BaseFragment;
import com.hilfritz.mvp.ui.placelist.helper.PlaceListAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceListFragment extends BaseFragment{

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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_list, container, false);
        ButterKnife.bind(this, view);

        //INITIALIZE VIEWS HERE
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(llm);

        adapter = new PlaceListAdapter(presenter.getPlaceList(), presenter);
        listView.setAdapter(getAdapter());
        getAdapter().notifyDataSetChanged();


        presenter.__fmwk_bpi_init((BaseActivity) getActivity(), this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.__fmwk_bpi_populate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.__fmwk_bpi_reset();
    }

    public PlaceListAdapter getAdapter() {
        return adapter;
    }
}