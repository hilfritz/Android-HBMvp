package com.hilfritz.bootstrap.view.placelist.helper;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hilfritz.bootstrap.R;
import com.hilfritz.bootstrap.api.pojo.places.Place;
import com.hilfritz.bootstrap.databinding.ListItemPlaceBinding;
import com.hilfritz.bootstrap.view.placelist.PlaceListPresenter;

import java.util.ArrayList;

/**
 * Created by Hilfritz Camallere on 15/3/17.
 * PC name herdmacbook1
 * @see https://mutualmobile.com/posts/using-data-binding-api-in-recyclerview
 */

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.ViewHolder> {
    ArrayList<Place> list = new ArrayList<Place>();
    private static final String TAG = "PlaceListAdapter";
    PlaceListPresenter presenter;

    public PlaceListAdapter(ArrayList<Place> list, PlaceListPresenter presenter) {
        this.list = list;
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemPlaceBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_item_place, parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindConnection(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ListItemPlaceBinding binding;
        public ViewHolder(ListItemPlaceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindConnection(Place userWrapper){
            binding.setVm(userWrapper);
            binding.setPresenter(presenter);
        }
    }

    @BindingAdapter("bind:loadImage")
    public static void loadImage(SimpleDraweeView simpleDraweeView, String url){
        //Log.d(TAG, "loadImage: url:"+url);
        simpleDraweeView.setImageURI(Uri.parse(url));
    }

}
