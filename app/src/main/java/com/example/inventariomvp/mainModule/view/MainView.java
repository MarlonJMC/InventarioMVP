package com.example.inventariomvp.mainModule.view;

import com.example.inventariomvp.common.pojo.Product;

public interface MainView { //Como Será una BD en tiempo real, no podemos percibir exactamente cuando ya hayan cargado los productos, por lo que se controlará unicamente el eliminado...

       // Acciones que realizará la interfaz con la ayuda del adaptador.
        void showProgress();
        void hideProgress();

        void add(Product product); //Operaciones en la vista, agregar a la vista.
        void update(Product product);
        void remove(Product product);

        void onShowError(int resMsg); // Mostrar cualquier tipo de mensaje
        void removeFail();


}
