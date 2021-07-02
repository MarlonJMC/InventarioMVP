package com.example.inventariomvp.mainModule.model;

import com.example.inventariomvp.common.pojo.Product;

public interface MainInteractor { // No procesará datos, sino los pasará a través de los eventos que EventBus va a postear.
    void suscribeToProducts();
    void unSuscribeToProduct();

    void removeProduct(Product product);

}
