package com.hilfritz.mvp.ui.placelist;

import com.hilfritz.mvp.AndroidTest;
import com.hilfritz.mvp.R;
import com.hilfritz.mvp.api.RestApiInterface;
import com.hilfritz.mvp.api.RestApiManager;
import com.hilfritz.mvp.api.pojo.places.Place;
import com.hilfritz.mvp.api.pojo.places.PlacesWrapper;
import com.hilfritz.mvp.ui.placelist.view.PlaceListViewInterface;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import java.util.ArrayList;
import java.util.Arrays;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Hilfritz Camallere on 7/8/2017.
 * common error: https://github.com/robolectric/robolectric/issues/2949
 * http://www.vogella.com/tutorials/Mockito/article.html
 */

public class PlacesPresenterTest extends AndroidTest {
    PlaceListViewInterface view;
    RestApiInterface apiManager;
    PlaceListPresenter presenter;

    ArrayList<Place> MANY_PLACES = new ArrayList<>(Arrays.asList(new Place(), new Place()));
    PlacesWrapper manyPlacesWrapper = new PlacesWrapper(MANY_PLACES);
    PlacesWrapper emptyPlacesWrapper = new PlacesWrapper();

    @After
    public void clear(){
        RxJavaHooks.reset();
    }
    @Before
    public void init(){
        //MOCKS THE subscribeOn(Schedulers.io()) to use the same thread the test is being run on
        //Schedulers.trampoline() runs the test in the same thread used by the test
        RxJavaHooks.setOnIOScheduler(new Func1<Scheduler, Scheduler>() {
            @Override
            public Scheduler call(Scheduler scheduler) {
                return Schedulers.trampoline();
            }
        });


        view = mock(PlaceListViewInterface.class);
        apiManager = mock(RestApiManager.class);
        presenter = new PlaceListPresenter();
        presenter.__init(null,  view, Schedulers.trampoline());
        presenter.apiManager = apiManager;


        //exceptionPlacesWrapper = throw Exception(EXCEPTION_MESSAGE1);
    }

    @Test
    public void loadAllPlacesTest(){
        when(apiManager.getPlacesObservable(anyString(), anyInt())).thenReturn(Observable.just(manyPlacesWrapper));
        when(view.isNetworkAvailable()).thenReturn(Boolean.TRUE);
        presenter.__populate();
        verify(view, atLeastOnce()).showLoading();
        verify(view, atLeastOnce()).showList();
        verify(view).hideLoading();

        //assertTrue(view.getAdapter().getItemCount()==3);
    }

    @Test
    public void loadAllPlacesThenClickListItemTest(){
        //when(apiManager.getPlacesObservable(anyString(), anyInt())).thenReturn(Observable.just(manyPlacesWrapper));
        //when(view.isNetworkAvailable()).thenReturn(Boolean.TRUE);
        //verify(view, atLeastOnce()).showLoading();
        //verify(view, atLeastOnce()).showList();
        //verify(view).hideLoading();



        ActivityController<PlaceListActivity> activityController = Robolectric.buildActivity(PlaceListActivity.class);


        view = new PlaceListFragment();

        when(apiManager.getPlacesObservable(anyString(), anyInt())).thenReturn(Observable.just(manyPlacesWrapper));
        when(view.isNetworkAvailable()).thenReturn(Boolean.TRUE);
        activityController.get()
                .getSupportFragmentManager()
                .beginTransaction()
                .add((PlaceListFragment)view, null)
                .commit();


        activityController.create().start().resume();


        view.getRecyclerView().measure(0,0);
        view.getRecyclerView().layout(0, 0, 100, 10000);

    }

    @Test
    public void loadEmptyPlaceTest(){
        when(apiManager.getPlacesObservable(anyString(), anyInt())).thenReturn(Observable.just(emptyPlacesWrapper));
        when(view.isNetworkAvailable()).thenReturn(Boolean.TRUE);


        presenter.__populate();

        verify(view, atLeastOnce()).showLoading();
        verify(view).hideLoading();

        verify(view, times(1)).getStringById(R.string.label_no_data);
        verify(view).showErrorFullScreen(null);
        verify(view, never()).showList();
        verify(view, atLeastOnce()).notifyDataSetChangedRecyeclerView();

    }

    @Test
    public void loadExceptionTest(){
        String exceptionMessage = "sorryException";
        when(apiManager.getPlacesObservable(anyString(), anyInt())).thenReturn(Observable.<PlacesWrapper>error(new Exception(exceptionMessage)));
        when(view.isNetworkAvailable()).thenReturn(Boolean.TRUE);

        presenter.__populate();

        verify(view, atLeastOnce()).showLoading();
        verify(view).hideLoading();

        //verify(view, never()).notifyDataSetChangedRecyeclerView();
        //verify(view, never()).showList();
        verify(view, times(1)).showDialog(PlaceListPresenter.DIALOG_TAG, exceptionMessage);

    }
}
