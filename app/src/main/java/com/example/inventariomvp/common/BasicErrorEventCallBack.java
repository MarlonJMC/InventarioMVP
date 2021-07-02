package com.example.inventariomvp.common;

public interface BasicErrorEventCallBack {
    void onSuccess();
    void onError(int typeEvent, int resMsg);
}
