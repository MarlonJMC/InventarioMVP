package com.example.inventariomvp.mainModule.presenter;

import com.example.inventariomvp.common.pojo.Product;
import com.example.inventariomvp.mainModule.events.MainEvent;

public interface MainPresenter { // Lógica e intermediario entre vista y modelo, validará.
    // Tambien se suscribirá y desuscribirá a eventos.

    //Corresponde al ciclo de vida de Android.
    void onCreate();
    void onPause();
    void onResume();
    void onDestroy();

    void remove(Product product);
    void onEventListener(MainEvent event);
}
