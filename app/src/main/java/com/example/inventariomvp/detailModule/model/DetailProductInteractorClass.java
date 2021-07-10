package com.example.inventariomvp.detailModule.model;

import com.example.inventariomvp.common.BasicEventCallBack;
import com.example.inventariomvp.detailModule.event.DetailProductEvent;
import com.example.inventariomvp.detailModule.model.dataAccess.RealTimeDatabase;
import com.example.inventariomvp.common.pojo.Product;

import org.greenrobot.eventbus.EventBus;

public class DetailProductInteractorClass implements DetailProductInteractor{

    RealTimeDatabase mDatabasse;

    public DetailProductInteractorClass(){
        mDatabasse= new RealTimeDatabase();
    }

    @Override
    public void updateProduct(Product product) {
        mDatabasse.updateProduct(product, new BasicEventCallBack() {
            @Override
            public void onSuccess() {
                post(DetailProductEvent.UPDATE_SUCCESS);
            }

            @Override
            public void onError() {
                post(DetailProductEvent.ERROR_SERVER);
            }
        });
    }

    private void post(int typeEvent) {
        DetailProductEvent event= new DetailProductEvent();
        event.setTypeEvent(typeEvent);
        EventBus.getDefault().post(event);
    }


}
