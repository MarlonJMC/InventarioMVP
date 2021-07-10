package com.example.inventariomvp.detailModule.presenter;

import com.example.inventariomvp.common.pojo.Product;
import com.example.inventariomvp.detailModule.event.DetailProductEvent;

public interface DetailProductPresenter {
    void onCreate();
    void onDestroy();

    void updateProduct(Product product);

    void onEventListener(DetailProductEvent event);
}
