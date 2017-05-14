package com.hilfritz.mvp.ui.placelist;

import com.hilfritz.mvp.BuildConfig;
import com.hilfritz.mvp.MyApplicationTest;
import com.hilfritz.mvp.api.RestApiManager;
import com.hilfritz.mvp.application.MyApplication;
import com.hilfritz.mvp.ui.placelist.view.PlaceListViewInterface;
import com.hilfritz.mvp.util.ConnectionUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by home on 5/14/2017.
 * common error: https://github.com/robolectric/robolectric/issues/2949
 * http://www.vogella.com/tutorials/Mockito/article.html
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = MyApplicationTest.class)
public class PlaceListTest {

    @Before
    public void setup(){
    }

    @Test
    public void testLoadingVisibilityContinuationWhenLoadingAndScreenRotationHappened(){
        //--------ARRANGE
        PlaceListViewInterface viewInterface = mock(PlaceListViewInterface.class);
        PlaceListPresenter presenter = new PlaceListPresenter();
        presenter.setView(viewInterface);

        //WHEN: MOCK THE __ISFROMROTATION
        //USE SPY HERE, NORMAL "WHEN" ON THE ORIGINAL WON'T WORK
        PlaceListPresenter spypresenter = spy(presenter);
        when(spypresenter.__fmwk_bp_isFromRotation()).thenReturn(true);
        when(spypresenter.isOnGoingRequest()).thenReturn(true);

        //--------ACT
        spypresenter.__fmwk_bpi_populate();

        //--------ASSERT
        verify(spypresenter.getView(), times(1))
                .showLoading(
                        anyInt(),
                        anyString()
                       );
    }
    /*
    @Test
    public void testNoNetworkAvailable(){
        //--------ARRANGE
        PlaceListViewInterface viewInterface = mock(PlaceListViewInterface.class);
        PlaceListPresenter presenter = new PlaceListPresenter();
        presenter.setView(viewInterface);

        //WHEN: MOCK THE __ISFROMROTATION
        //USE SPY HERE, NORMAL "WHEN" ON THE ORIGINAL WON'T WORK
        //SPY IS USEFUL FOR MOCKING REAL OBJECT METHODS
        PlaceListPresenter spypresenter = spy(presenter);
        when(spypresenter.__fmwk_bp_isFromRotation()).thenReturn(true);
        when(spypresenter.isOnGoingRequest()).thenReturn(true);
        when(spypresenter.isNetworkAvailable()).thenReturn(true);

        //--------ACT
        spypresenter.__fmwk_bpi_populate();

        //--------ASSERT
        verify(spypresenter.getView(), times(1))
                .showDialog(anyString(),anyInt(),anyBoolean(), anyBoolean());
    }
    */
}
