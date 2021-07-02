package com.example.inventariomvp.addModule.presenter;

import com.example.inventariomvp.addModule.events.AddProductEvent;
import com.example.inventariomvp.common.pojo.Product;

public interface AddProductPresenter {
    // Consideramos los eventos del ciclo de vida del fragmente
    // Como NO HAY un listener que escucha todo el tiempo, no se consdieran los eventos onResume y onPause

    void onShow();
    void onDestroy();

    //Nos preguntamos el objetivo de la vista y los eventos que habiamos definido en el View

    void addProduct(Product product);
    void onEventListener(AddProductEvent event);
}
