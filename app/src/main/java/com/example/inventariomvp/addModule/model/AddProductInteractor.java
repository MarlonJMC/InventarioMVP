package com.example.inventariomvp.addModule.model;

import com.example.inventariomvp.common.pojo.Product;

public interface AddProductInteractor {
    void addProduct(Product product);
    // De los eventos del presenter, el único que va a hacer una solicitud a la BD
}
