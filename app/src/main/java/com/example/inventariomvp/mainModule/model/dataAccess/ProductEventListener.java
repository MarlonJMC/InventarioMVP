package com.example.inventariomvp.mainModule.model.dataAccess;

import com.example.inventariomvp.common.pojo.Product;

public interface ProductEventListener {

    // Version simplificada de childEventListener

    void onChildAdded(Product product);
    void onChildUpdated(Product product);
    void onChildRemoved(Product product);

    void onError(int resMsg);
}
