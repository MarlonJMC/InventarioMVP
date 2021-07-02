package com.example.inventariomvp.mainModule.model;

import com.example.inventariomvp.common.BasicErrorEventCallBack;
import com.example.inventariomvp.common.pojo.Product;
import com.example.inventariomvp.mainModule.events.MainEvent;
import com.example.inventariomvp.mainModule.model.dataAccess.ProductEventListener;
import com.example.inventariomvp.mainModule.model.dataAccess.RealTimeDatabase;

import org.greenrobot.eventbus.EventBus;

public class MainInteractorClass implements com.example.inventariomvp.mainModule.model.MainInteractor{

// Implementación del comportamiento del interactor
    private RealTimeDatabase mDatabase;

    public MainInteractorClass() {
        // Inicializamos la BD
        mDatabase= new RealTimeDatabase();
    }

    @Override
    public void suscribeToProducts() {
        mDatabase.suscribeToProducts(new ProductEventListener() {
            @Override
            public void onChildAdded(Product product) {
                post(product, MainEvent.SUCCESS_ADD);
            }

            @Override
            public void onChildUpdated(Product product) {
                post(product, MainEvent.SUCCESS_UPDATE);
            }

            @Override
            public void onChildRemoved(Product product) {
                post(product, MainEvent.SUCCESS_REMOVE);
            }

            @Override
            public void onError(int resMsg) {
                post(MainEvent.ERROR_SERVER,resMsg);
            }
        });
    }

    @Override
    public void unSuscribeToProduct() {
        mDatabase.unSuscribeToProduct();
    }

    @Override
    public void removeProduct(Product product) {
        mDatabase.removeProduct(product, new BasicErrorEventCallBack() {
            @Override
            public void onSuccess() {
                post(MainEvent.SUCCESS_REMOVE);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent,resMsg);
            }
        });
    }


    private void post(Product product, int typeEvent, int resMsg){ // Se encargara de publicar "postear" el objeto Event definido en este módulo
        MainEvent event=new MainEvent();
        event.setProduct(product);
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event); //Publicandolo
    }
    //Sobrecargas del metodo post.
    private void post(Product product, int typeEvent){
        post(product,typeEvent,0);
    }

    private void post(int typeEvent, int resMsg){
        post(null,typeEvent,resMsg);
    }

    private void post(int typeEvent){
        post(null,typeEvent,0);
    }
}
