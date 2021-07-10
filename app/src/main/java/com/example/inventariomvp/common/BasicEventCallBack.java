package com.example.inventariomvp.common;

public interface BasicEventCallBack {
    //En el modulo de detalle no se necesita parámetro
    //El dueño de la aplicacion quiere que solo exista un erroGlobal para la función actualizar, porque se supone sabrá utilizar la app de entrada.
    void onSuccess();
    void onError();
}
