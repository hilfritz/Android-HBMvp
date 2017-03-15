package com.hilfritz.bootstrap.view.contactlist.main.userlist.helper;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hilfritz.bootstrap.R;
import com.hilfritz.bootstrap.api.pojo.UserWrapper;
import com.hilfritz.bootstrap.databinding.UserListItemBindableBinding;
import com.hilfritz.bootstrap.view.contactlist.main.userlist.UserListPresenter;

import java.util.List;

/**
 * Created by Hilfritz P. Camallere on 6/4/2016.
 */

public class UserListDataBindingAdapter extends RecyclerView.Adapter<UserListDataBindingAdapter.ViewHolder> {
    List<UserWrapper> items;
    Context context;
    UserListPresenter presenter;

    public UserListDataBindingAdapter(Context context, List<UserWrapper> list, UserListPresenter presenter) {
        this.items = list;
        this.context = context;
        this.presenter = presenter;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UserListItemBindableBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.user_list_item_bindable, parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindConnection(items.get(position), this.presenter);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private UserListItemBindableBinding binding;


        public ViewHolder(UserListItemBindableBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindConnection(UserWrapper userWrapper, UserListPresenter presenter){
            binding.setVm(userWrapper);
            binding.setPresenter(presenter);
        }
    }

}
