package com.example.inventariomvp.addModule.presenter;

import com.example.inventariomvp.addModule.events.AddProductEvent;
import com.example.inventariomvp.addModule.model.AddProductInteractor;
import com.example.inventariomvp.addModule.model.AddProductInteractorClass;
import com.example.inventariomvp.addModule.view.AddProductView;
import com.example.inventariomvp.common.pojo.Product;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class AddProductPresenterClass implements AddProductPresenter {

    private AddProductView mView;
    private AddProductInteractor mInteractor;

    public AddProductPresenterClass(AddProductView mView){
        this.mView=mView;
        this.mInteractor= new AddProductInteractorClass();
    }

    @Override
    public void onShow() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mView=null;
    }

    @Override
    public void addProduct(Product product) {
        if(setProgress()){
            mInteractor.addProduct(product);
        }
    }

    @Subscribe
    @Override
    public void onEventListener(AddProductEvent event) {
        if(mView!=null){
            mView.hideProgress();
            mView.enableUIElements();

          switch (event.getTypeEvent()){
              case AddProductEvent.SUCCESS_ADD:{
                mView.productAdded();
            }break;
              case AddProductEvent.ERROR_MAX_VALUE:{
                mView.showError(event.getResMsg());
            }break;
              case AddProductEvent.ERROR_SERVER:{
                  mView.showError(event.getResMsg());
            }break;
          }

        }
    }

    private boolean setProgress() {
        if(mView!=null){
            mView.disableUIElements();
            mView.showProgress();
            return true;
        }
        return false;
    }

}
