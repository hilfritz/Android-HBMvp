package com.hilfritz.bootstrap.view.contactlist.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hilfritz.bootstrap.R;
import com.hilfritz.bootstrap.api.pojo.UserWrapper;
import com.hilfritz.bootstrap.application.MyApplication;
import com.hilfritz.bootstrap.framework.BaseActivity;
import com.hilfritz.bootstrap.framework.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserDetailActivityFragment extends BaseFragment {

    public static final String EXTRA_USERWRAPPER = "userWrapperExtra";
    @Inject
    UserDetailFragmentPresenter presenter;
    @BindView(R.id.messageTextView)
    TextView messageTextView;

    @BindView(R.id.mainLinearLayout)
    LinearLayout linearLayout;

    @BindView(R.id.value)
    TextView value;

    @BindView(R.id.value1)
    TextView value1;

    @BindView(R.id.value2)
    TextView value2;

    @BindView(R.id.value3)
    TextView value3;

    @BindView(R.id.value4)
    TextView value4;

    @BindView(R.id.value5)
    TextView value5;
    @Inject
    Gson gson;


    public UserDetailActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        (((MyApplication) getActivity().getApplication()).getAppComponent()).inject(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /**IMPORTANT: FRAMEWORK METHOD**/
        /**
         * FRAMEWORK
         * IMPORTANT: PLACE THE INIT HERE
         */
        presenter.__fmwk_bpi_init((BaseActivity) getActivity(), this);
        messageTextView.setText("...");
        linearLayout.setVisibility(View.GONE);

        //IF NEW ACTIVITY
        String extra = getUserWrapperIntentExtra();
        if (extra != null) {
            UserWrapper user =  gson.fromJson( extra, UserWrapper.class );
            presenter.populate(user);
        }else{
            //IF INSIDE THe MAIN ACTIVITY
        }

    }

    private String getUserWrapperIntentExtra() {
        return getActivity().getIntent().getStringExtra(UserDetailActivityFragment.EXTRA_USERWRAPPER);
    }

    public UserWrapper getUserWrapper() {
        return null;
    }

    public void populate(UserWrapper userWrapper){
        linearLayout.setVisibility(View.VISIBLE);
        messageTextView.setVisibility(View.GONE);
        value.setText(userWrapper.getName());
        value1.setText(userWrapper.getUsername());
        value2.setText(userWrapper.getPhone());
        value3.setText(userWrapper.getAddress().getSuite()
                +" "+userWrapper.getAddress().getStreet()
                +" "+userWrapper.getAddress().getCity()
                +" "+userWrapper.getAddress().getZipcode()
                );
        value4.setText(userWrapper.getWebsite());
        value5.setText(
                Html.fromHtml(
                        userWrapper.getCompany().getName()
                                +" <br/>"+userWrapper.getPhone()
                                +" <br/>"+userWrapper.getEmail()
                                )
        );
    }
}
