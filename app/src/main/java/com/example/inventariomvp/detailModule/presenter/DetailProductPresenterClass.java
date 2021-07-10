package com.example.inventariomvp.detailModule.presenter;

import com.example.inventariomvp.common.pojo.Product;
import com.example.inventariomvp.detailModule.event.DetailProductEvent;
import com.example.inventariomvp.detailModule.model.DetailProductInteractor;
import com.example.inventariomvp.detailModule.model.DetailProductInteractorClass;
import com.example.inventariomvp.detailModule.view.DetailProductView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class DetailProductPresenterClass implements DetailProductPresenter{

    private DetailProductView mView;
    private DetailProductInteractor mInteractor;

    public DetailProductPresenterClass(DetailProductView view){
        this.mView=view;
        this.mInteractor=new DetailProductInteractorClass();
    }

    @Override
    public void onCreate(){
        EventBus.getDefault().register(this);
        if(mView!=null){
            mView.hideFab();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        if(mView!=null){
            mView.showFab();
            mView=null;
        }
    }

    @Override
    public void updateProduct(Product product) {
        if(setProgress()){
            mInteractor.updateProduct(product);
        }
    }

    private boolean setProgress() {
        if (mView != null){
            mView.showProgress();
            mView.disableUIElements();
            return true;
        }
        return false;
    }



    @Subscribe
    @Override
    public void onEventListener(DetailProductEvent event) {
        if (mView != null){
            mView.hideProgress();
            mView.enableUIElements();

            switch (event.getTypeEvent()){
                case DetailProductEvent.UPDATE_SUCCESS:
                    mView.updateSuccess();
                    break;
                case DetailProductEvent.ERROR_SERVER:
                    mView.updateError();
                    break;
            }
        }
    }


}
