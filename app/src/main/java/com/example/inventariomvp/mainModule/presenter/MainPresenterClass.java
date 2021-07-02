package com.example.inventariomvp.mainModule.presenter;

import com.example.inventariomvp.common.pojo.Product;
import com.example.inventariomvp.mainModule.events.MainEvent;
import com.example.inventariomvp.mainModule.model.MainInteractor;
import com.example.inventariomvp.mainModule.model.MainInteractorClass;
import com.example.inventariomvp.mainModule.view.MainView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainPresenterClass implements MainPresenter{

    private MainView mView; //Vista
    private MainInteractor mInteractor; //Interactor de la BD

    public MainPresenterClass(MainView mView) {
        this.mView = mView;
        this.mInteractor=new MainInteractorClass();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        mInteractor.unSuscribeToProduct();;
    }

    @Override
    public void onResume() {
        mInteractor.suscribeToProducts();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mView=null;
    }

    @Override
    public void remove(Product product) {
        if(setProgress()){
            mInteractor.removeProduct(product);
        }
    }

    private boolean setProgress() {
        if(mView!=null){
            mView.showProgress();
            return true;
        }
        return false;
    }

    @Subscribe
    @Override
    public void onEventListener(MainEvent event) {
        if(mView!=null){
            mView.hideProgress();

            switch (event.getTypeEvent()){
                case MainEvent.SUCCESS_ADD:{
                    mView.add(event.getProduct());
                }break;
                
                case MainEvent.SUCCESS_UPDATE:{
                    mView.update(event.getProduct());
                }break;

                case MainEvent.SUCCESS_REMOVE:{
                    mView.remove(event.getProduct());
                }break;

                case MainEvent.ERROR_SERVER:{
                    mView.onShowError(event.getResMsg());
                }break;

                case MainEvent.ERROR_TO_REMOVE:{
                    mView.removeFail();
                }break;
            }

        }
    }
}
