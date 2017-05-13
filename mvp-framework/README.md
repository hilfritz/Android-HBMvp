# Android MVP
This is a simple Model-View-Presenter architecture for Android applications and a few boilerplate codes.

- This aims to help provide a good starting point when creating new Android projects using Model-View-Presenter architecture.
- Has support for the different Android Lifecycle methods (e.g. onCreate(), onStop() ..
- 


## Sample project

The sample project is good usage of how the library is used. See the placelist package)

[Sample here - see the <b>com.hilfritz.mvp.ui.placelist</b> package](https://github.com/hilfritz/Android-HBMvp/tree/development/app)

- Features useful utility classes
- Dependency Injection implementation for the Presenters using Dagger 2.0
- Uses of event bus for interacting between different parts of the app
- Fresco library for displaying the image 
- Robolectirc setup for unit testing
- Retrofit for rest api requests
- Rxjava for multiple thread process (e.g. retrofit requests)


Most my Android projects follow this code. Personally I created the library to help me get a code jumpstart and save some time in setting up a new Android project. 


<be>
## Setup

To use the library in your app, edit your app's build.gradle:
```gradle
compile 'com.hilfritz:hbmvp:1.0.0'
```

### Framework methods
- starts with __
 (like BasePresenter.java's method __fmwk_bp_isInitialLoad())
- this distinction of naming methods was done in purpose to be able to easily spot normal methods from framework methods.



### Version:
- V1.0.0

### License:
- MIT

### References:
- Good MVP implementation - https://github.com/antoniolg/androidmvp
- Refactoring Android App- https://www.youtube.com/watch?v=ZWYOy8E4jWo

## -
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
## -
### Typical Presenter Setup:
* extend <i><b>BasePresenter.java</b></i>
* implement <i><b>BasePresenterInterface.java</b></i>
* injected to corresponding fragments by injection framework. (see the sample app)


```java
public class PlaceListPresenter extends BasePresenter implements BasePresenterInterface{
    public static final String TAG = "PlaceListPresenter";

    Subscription placeListSubscription;
    PlaceListViewInterface view;
    Context context;
    PlaceListActivity activity;

    @Inject
    RestApiManager apiManager;
    private ArrayList<Place> placeList = new ArrayList<Place>();

    public PlaceListPresenter(MyApplication myApplication){
        //INITIALIZE INJECTION
        myApplication.getAppComponent().inject(this);
    }

    @Override
    public void __fmwk_bpi_init(BaseActivity activity, BaseFragment fragment) {
        this.context = fragment.getContext();
        this.activity = (PlaceListActivity) activity;
        Timber.tag(TAG);
        this.view = (PlaceListFragment)fragment;
        if (__fmwk_bp_isInitialLoad()){

            Timber.d("__fmwk_bpi_init:  new activity");
            __fmwk_bpi_init_new();
        }else{
            Timber.d("__fmwk_bpi_init: configuration/orientation change");
            __fmwk_bpi_init_change();
        }
    }

    @Override
    public void __fmwk_bpi_init_new() {
        Timber.d("__fmwk_bpi_init_new: for new activity");
        placeList.clear();
        view.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void __fmwk_bpi_init_change() {
        Timber.d("__fmwk_bpi_init_change: configuration change");

    }

    @Override
    public void __fmwk_bpi_populate() {
        Timber.d("__fmwk_bpi_populate: ");
        if (__fmwk_bp_isFromRotation()){
            //IMPORTANT: CHECK
            Timber.d("__fmwk_bpi_populate: rotation detected.");
            if (RxUtil.isSubscribed(placeListSubscription)){
                view.showLoading(android.R.drawable.progress_horizontal, "Loading");
            }
            return;
        }
        callPlaceListApi();
    }

    @Override
    public void __fmwk_bpi_resume() {
        Timber.d("__fmwk_bpi_resume: ");
    }

    @Override
    public void __fmwk_bpi_pause() {
        Timber.d("__fmwk_bpi_pause: ");
    }

    @Override
    public void __fmwk_bpi_destroy() {
        Timber.d("__fmwk_bpi_destroy: ");
        //...

    }

    public void callPlaceListApi(){
        Timber.d("callPlaceListApi: ");
        //... see the sample app
    }

    public ArrayList<Place> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(ArrayList<Place> place) {
        this.placeList = place;
    }

    public void onListItemClick(Place place){
       //... see the sample app
    }

    public Context getContext() {
        return context;
    }
}
```

### Typical Fragment Setup:
 * extend <i><b>BaseFragment.java</b></i>
 * implements <i><b>BaseFragmentInterface</b></i>
 * lifecycle methods to implement: <i><b>onCreate(), onCreateView(), onViewCreated(), onDestroy(), onResume(), onPause() </b></i>
 * implement own View Interface
 * <b>Important:</b> the method calls to these method name prefixed with <b>__</b> (e.g.<b>__methodName</b>) are very important. Always follow this structure with other fragments, this will help setup the framework.
```java
public class PlaceListFragment extends BaseFragment implements BaseFragmentInterface, PlaceListViewInterface{
    private static final String TAG = "PlaceListFragment";

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
        presenter.__fmwk_bpi_destroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("onResume: ");
        presenter.__fmwk_bpi_resume();
    }


    @Override
    public void onPause() {
        super.onPause();
        Timber.d("onPause: ");
        presenter.__fmwk_bpi_pause();
    }

}
```


