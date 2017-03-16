package com.hilfritz.bootstrap.view.contactlist.main.userlist;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hilfritz.bootstrap.R;
import com.hilfritz.bootstrap.api.pojo.UserWrapper;
import com.hilfritz.bootstrap.application.MyApplication;
import com.hilfritz.bootstrap.eventbus.event.SortEvent;
import com.hilfritz.bootstrap.framework.BaseActivity;
import com.hilfritz.bootstrap.framework.BaseFragment;
import com.hilfritz.bootstrap.view.contactlist.main.userlist.helper.UserListDataBindingAdapter;
import com.hilfritz.bootstrap.view.dialog.SimpleDialog;
import com.hilfritz.bootstrap.view.loading.FullscreenLoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class UserListFragment extends BaseFragment implements UserListView{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "UserListFragment";

    @Inject UserListPresenter presenter;


    public static final String EXTRA_VIEW_STATE = "extraViewState";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.messageTextView)
    TextView messageTextView;

    @BindView(R.id.userListView)
    RecyclerView listView;

    //UserListAdapter adapter;
    UserListDataBindingAdapter bindingAdapter;

    public UserListFragment() {
        // Required empty public constructor
    }

    public static UserListFragment newInstance(String param1, String param2) {
        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logd("onCreate: ");
        (((MyApplication) getActivity().getApplication()).getAppComponent()).inject(this);

        /**IMPORTANT: FRAMEWORK METHOD**/
        __fmwk_bf_checkIfNewActivity(savedInstanceState, presenter);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        logd("onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        ButterKnife.bind(this,view);

        /**IMPORTANT: FRAMEWORK METHOD**/
        //INITIALIZE THE VIEWS HERE
        //LISTVIEWS, ADAPTERS, ETC
        //adapter = new UserListAdapter(this.getContext(), presenter.getUsersList());
        //listView.setAdapter(adapter);

        //INITIALIZE THE LIST
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(llm);

        bindingAdapter = new UserListDataBindingAdapter(this.getContext(), presenter.getUsersList(), presenter);
        listView.setAdapter(bindingAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logd("onViewCreated: ");

        /**IMPORTANT: FRAMEWORK METHOD**/
        /**
         * FRAMEWORK
         * IMPORTANT: PLACE THE INIT HERE
         */
        presenter.__fmwk_bpi_init((BaseActivity) getActivity(), this);

    }

    @Override
    public void onResume() {
        logd("onResume: ");
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void showMessage(String str, View.OnClickListener onClickListener){
        logd("showMessage");
        SimpleDialog sd = SimpleDialog.newInstance(str, android.R.drawable.ic_dialog_alert, true);
        sd.show(getActivity().getSupportFragmentManager(), SimpleDialog.TAG);

        /*
        listView.setVisibility(View.GONE);
        messageTextView.setVisibility(View.VISIBLE);
        messageTextView.setText(str);
        if (onClickListener!=null){
            messageTextView.setOnClickListener(onClickListener);
        }
        */
    }

    @Override
    public void showLoading(String message){
        logd("showLoading() message ");
        //listView.setVisibility(View.GONE);
        //messageTextView.setVisibility(View.VISIBLE);
        //messageTextView.setText(message);
        //setLoadingVisibility(View.VISIBLE);
        FullscreenLoadingDialog.showLoading(getActivity().getSupportFragmentManager(), message, false);
    }

    @Override
    public void showList(){
        listView.setVisibility(View.VISIBLE);
        messageTextView.setVisibility(View.GONE);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        logd("onAttach: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        logd("onDetach: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        logd("onDestroy: ");
        presenter.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sortClickListener(SortEvent sortEvent) {
        if (sortEvent.getLoadingType()==UserListPresenter.LOADING_TYPES.SORTING_AZ)
            presenter.sort(UserListPresenter.LOADING_TYPES.SORTING_AZ);
        else if (sortEvent.getLoadingType()==UserListPresenter.LOADING_TYPES.SORTING_ZA)
            presenter.sort(UserListPresenter.LOADING_TYPES.SORTING_ZA);
    }

    public RecyclerView getListView() {
        return listView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        logd("onConfigurationChanged: ");
    }

    private void logd(String msg) {
        Log.d(TAG, TAG+">> "+msg);
    }

    public void listViewItemClick(final List<UserWrapper> userList){
        logd("listViewItemClick()");
        /*
        if (userList.size() > 0) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    logd("listViewItemClick() onItemClick()");
                    UserWrapper userWrapper = userList.get(position);
                    UserListItemClickEventDeligate.userlistItemClick(userWrapper);
                }
            });
        }
        */
    }

    public void notifyDataSetChanged() {
        bindingAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading(@DrawableRes int icon, String message) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showErrorFullScreen(@DrawableRes int icon, String message) {

    }

    @Override
    public void showDialog(String message, @DrawableRes int iconId, boolean cancellable, boolean finishOnDismiss) {

    }


    @Override
    public void refresh() {

    }
}
