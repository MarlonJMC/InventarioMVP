package com.example.inventariomvp.addModule.model.dataAccess;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.inventariomvp.R;
import com.example.inventariomvp.addModule.events.AddProductEvent;
import com.example.inventariomvp.common.BasicErrorEventCallBack;
import com.example.inventariomvp.common.model.dataAccess.FirebaseRealTimeDatabaseAPI;
import com.example.inventariomvp.common.pojo.Product;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class RealtimeDatabase {
    private final FirebaseRealTimeDatabaseAPI mDataBaseApi;

    public RealtimeDatabase(){
        mDataBaseApi= FirebaseRealTimeDatabaseAPI.getInstance();
    }

    // Desición entre Listener y Callback
    // Analizando lo siguiente: solo será una inserción, no necesitamos estar escuchando constantemente,
    // ... y segun los eventos de la vista tendremos, successAdd, error de máximo y error de servidor, por ende,
    //... se puede hasta reutiliar el Callback hecho en el paquete common
    public void addProduct(Product product, BasicErrorEventCallBack callBack){
        mDataBaseApi.getProductsReference().push().setValue(product, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if(error==null){
                    callBack.onSuccess();
                }else{
                    if(error.getCode()==DatabaseError.PERMISSION_DENIED){
                        callBack.onError(AddProductEvent.ERROR_MAX_VALUE, R.string.addProduct_messageError_maxValue);
                    }else{
                        callBack.onError(AddProductEvent.ERROR_SERVER, R.string.addProduct_messageError_addedError);
                    }
                }
            }
        });
    }
}
