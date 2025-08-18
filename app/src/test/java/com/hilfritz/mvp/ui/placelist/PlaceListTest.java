package com.hilfritz.mvp.ui.placelist;

import com.hilfritz.mvp.BuildConfig;
import com.hilfritz.mvp.MyApplicationTest;
import com.hilfritz.mvp.api.RestApiInterface;
import com.hilfritz.mvp.api.RestApiManager;
import com.hilfritz.mvp.api.pojo.places.Place;
import com.hilfritz.mvp.api.pojo.places.PlacesWrapper;
import com.hilfritz.mvp.ui.placelist.view.PlaceListViewInterface;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Collections;

import rx.Observable;
import rx.observers.TestSubscriber;

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
    /*

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
        spypresenter.__populate();

        //--------ASSERT
        verify(spypresenter.getView(), times(1))
                .showLoading(
                        anyInt(),
                        anyString()
                       );
    }

    @Test
    public void testNoNetworkAvailable(){
        //--------ARRANGE
        class PlaceListPresenterNetWorkDynamic extends PlaceListPresenter{
            boolean isNetworkAvailable = false;

            public PlaceListPresenterNetWorkDynamic(boolean isNetworkAvailable) {
                this.isNetworkAvailable = isNetworkAvailable;
            }

            @Override
            public boolean isNetworkAvailable() {
                return this.isNetworkAvailable;
            }
        }

        PlaceListViewInterface viewInterface = mock(PlaceListViewInterface.class);
        PlaceListPresenter presenter = new PlaceListPresenterNetWorkDynamic(false);
        presenter.setView(viewInterface);

        //WHEN: MOCK THE __ISFROMROTATION
        //USE SPY HERE, NORMAL "WHEN" ON THE ORIGINAL WON'T WORK
        //SPY IS USEFUL FOR MOCKING REAL OBJECT METHODS
        PlaceListPresenter spypresenter = spy(presenter);

        when(spypresenter.__fmwk_bp_isFromRotation()).thenReturn(false);
        when(spypresenter.isOnGoingRequest()).thenReturn(false);
        //when(spypresenter.isNetworkAvailable()).thenReturn(false);

        //--------ACT
        spypresenter.__populate();

        //--------ASSERT
        verify(spypresenter.getView(), times(1))
                .showDialog(anyString(),anyInt(),anyBoolean(), anyBoolean());
    }

    //@Test
    public void testEmptyLocationList(){
        //SEE
        //http://stackoverflow.com/questions/39827081/unit-testing-android-application-with-retrofit-and-rxjava/39828581#39828581
        //--------ARRANGE/GIVEN
        class PlaceListPresenterNetWorkDynamic extends PlaceListPresenter{
            boolean isNetworkAvailable = false;

            public PlaceListPresenterNetWorkDynamic(boolean isNetworkAvailable) {
                this.isNetworkAvailable = isNetworkAvailable;
            }

            @Override
            public boolean isNetworkAvailable() {
                return this.isNetworkAvailable;
            }
        }


        RestApiInterface mockApiInterface = mock(RestApiInterface.class);
        RestApiManager mockRestApiManager = mock(RestApiManager.class);
        PlaceListViewInterface viewInterface = mock(PlaceListViewInterface.class);
        PlaceListPresenter presenter = new PlaceListPresenterNetWorkDynamic(true);
        presenter.setView(viewInterface);

        //WHEN: MOCK THE __ISFROMROTATION
        //USE SPY HERE, NORMAL "WHEN" ON THE ORIGINAL WON'T WORK
        //SPY IS USEFUL FOR MOCKING REAL OBJECT METHODS
        PlaceListPresenter spypresenter = spy(presenter);
        RestApiManager spyapimanager = spy(mockRestApiManager);
        when(spypresenter.__fmwk_bp_isFromRotation()).thenReturn(false);
        when(spypresenter.isOnGoingRequest()).thenReturn(false);

        spypresenter.setApiManager(spyapimanager);
        when(spypresenter.getApiManager().getApi()).thenReturn(mockApiInterface);

        TestSubscriber<PlacesWrapper> pw= new TestSubscriber<PlacesWrapper>();

        when(mockApiInterface.getPlacesObservable()).thenReturn(Observable.just(new PlacesWrapper(Collections.<Place>emptyList())));
        mockApiInterface.getPlacesObservable().subscribe(pw);
        //mockApiInterface.getPlacesObservable().subscribe();

        pw.assertCompleted();



        //--------ACT
        spypresenter.__populate();



        //--------ASSERT/THEN
        verify(spypresenter.getView(), times(1))
                .hideLoading();
    }
    */

    @Test
    public void shouldPass(){
        assertTrue(1==1);
    }


}
