package com.hilfritz.mvp.ui.psi;

import com.hilfritz.mvp.R;
import com.hilfritz.mvp.api.psi.pojo.PsiPojo;
import com.hilfritz.mvp.util.RxUtil;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * @author Hilfritz Camallere on 21/4/18
 */
public class PsiMapPresenterImpl implements PsiMapContract.Presenter {
    PsiMapContract.View view;
    PsiMapContract.Model model;
    Scheduler bgThread, mainThread;
    Subscription getAllPsiSubscription;

    @Override
    public void init(PsiMapContract.View view, PsiMapContract.Model model, Scheduler bgThread, Scheduler mainThread) {
        this.view = view;
        this.model = model;
        this.bgThread = bgThread;
        this.mainThread = mainThread;
    }

    @Override
    public void populate() {
        RxUtil.unsubscribe(getAllPsiSubscription);
        //1. SHOW LOADING
        view.showLoading();
        //2. CALL PSI DATA FROM API
        getAllPsiSubscription = model.getAllPsi()
                .observeOn(mainThread)
                .subscribeOn(bgThread)
                .subscribe(new Subscriber<PsiPojo>() {
                    @Override
                    public void onCompleted() {
                        view.hideLoading();
                        RxUtil.unsubscribe(getAllPsiSubscription);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideLoading();
                        view.showDialogWithMessage(" Error:\n"+e.getLocalizedMessage()+" \n"+view.getStringFromStringResId(R.string.something_went_wrong));
                        e.printStackTrace();
                        RxUtil.unsubscribe(getAllPsiSubscription);
                    }

                    @Override
                    public void onNext(PsiPojo psiPojo) {
                        //4. CHECK PSI DATA
                        if (psiPojo==null){
                            //4.1 CHECK PSI DATA - IF WRONG DATA, SHOW ERROR MESSAGE
                            view.showDialogWithMessage(view.getStringFromStringResId(R.string.something_went_wrong));
                        }else{
                            view.showMapWithData(psiPojo);
                        }
                    }
                });
    }

    @Override
    public void destroy() {
        RxUtil.unsubscribe(getAllPsiSubscription);
    }
}
