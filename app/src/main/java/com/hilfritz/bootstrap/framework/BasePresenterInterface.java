package com.hilfritz.bootstrap.framework;

/**
 * Created by Hilfritz P. Camallere on 8/20/2016.
 */
public interface BasePresenterInterface {
    /**
     * IMPORTANT: CALL THIS IN
     * <ul>
     *     <li>Activity#OnCreate()</li>
     *     <li>Fragment#onViewCreated</li>
     *     <li>Fragment#onCreateView</li>
     * </ul>
     * @param activity
     * @param fragment
     */
    void __fmwk_bpi_init(BaseActivity activity, BaseFragment fragment);

    /**
     * IMPORTANT: THIS IS CALLED WHENEVER THE FRAGMENT IS NEWLY CREATED, USUALLY IS CALLED AFTER
     * {@link BasePresenterInterface#__fmwk_bpi_init(BaseActivity, BaseFragment)}
     * called because of new creation of fragment
     */
     void __fmwk_bpi_init_new();


    /**
     * IMPORTANT: THIS IS CALLED WHENEVER THE FRAGMENT IS NEWLY CREATED, USUALLY IS CALLED AFTER
     * {@link BasePresenterInterface#__fmwk_bpi_init(BaseActivity, BaseFragment)}
     * called because of orientation change or configuration change
     */
     void __fmwk_bpi_init_change();

     void __fmwk_bpi_reset();

    /**
     * IMPORTANT: CALL THIS AFTER __fmwk_bpi_init is called
     *
     * <ul>
     *     <li>Fragment#onViewCreated</li>
     *     <li>if the fragment is used in a pager, just call this method when the pager changes page #onPageChanged</li>
     * </ul>
     */
     void __fmwk_bpi_populate();
}
