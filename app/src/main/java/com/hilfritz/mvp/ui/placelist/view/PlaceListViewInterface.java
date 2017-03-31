package com.hilfritz.mvp.ui.placelist.view;

import com.hilfritz.mvp.framework.BaseViewInterface;
import com.hilfritz.mvp.ui.placelist.helper.PlaceListAdapter;

/**
 * Created by Hilfritz Camallere on 31/3/17.
 * PC name herdmacbook1
 */

public interface PlaceListViewInterface extends BaseViewInterface {

    PlaceListAdapter getAdapter();
}
