package com.example.inventariomvp.mainModule.view.adapters;

import com.example.inventariomvp.common.pojo.Product;

public interface OnItemClickListener {
    void onItemClick(Product product);
    void onLongItemClick(Product product);
}
