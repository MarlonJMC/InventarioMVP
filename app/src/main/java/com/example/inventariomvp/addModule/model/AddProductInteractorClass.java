package com.example.inventariomvp.addModule.model;

import com.example.inventariomvp.addModule.events.AddProductEvent;
import com.example.inventariomvp.addModule.model.dataAccess.RealtimeDatabase;
import com.example.inventariomvp.common.BasicErrorEventCallBack;
import com.example.inventariomvp.common.pojo.Product;

import org.greenrobot.eventbus.EventBus;

public class AddProductInteractorClass implements AddProductInteractor {

    RealtimeDatabase mDatabase;

    public AddProductInteractorClass(){
        mDatabase=new RealtimeDatabase();
    }

    @Override
    public void addProduct(Product product) {
        mDatabase.addProduct(product, new BasicErrorEventCallBack() {
            @Override
            public void onSuccess() {
                post(AddProductEvent.SUCCESS_ADD);
            }


            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent, resMsg);
            }
        });
    }

    private void post(int typeEvent) {
        post(typeEvent,0);
    }

    private void post(int typeEvent, int resMsg){
        AddProductEvent event=new AddProductEvent();
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }


}
