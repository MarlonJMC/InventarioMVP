package com.example.inventariomvp.mainModule.model.dataAccess;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.inventariomvp.R;
import com.example.inventariomvp.common.BasicErrorEventCallBack;
import com.example.inventariomvp.common.model.dataAccess.FirebaseRealTimeDatabaseAPI;
import com.example.inventariomvp.common.pojo.Product;
import com.example.inventariomvp.mainModule.events.MainEvent;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class RealTimeDatabase {

    private final FirebaseRealTimeDatabaseAPI mDatabaseAPI;
    private ChildEventListener mProductChildEventListener;

    public RealTimeDatabase() {
        mDatabaseAPI=FirebaseRealTimeDatabaseAPI.getInstance();
    }

    // Generar los metodos que se llamaran desde el interactor


    public void suscribeToProducts(ProductEventListener listener){
        if(mProductChildEventListener==null){

            mProductChildEventListener = new ChildEventListener(){
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    listener.onChildAdded(getProduct(snapshot));
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    listener.onChildUpdated(getProduct(snapshot));
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    listener.onChildRemoved(getProduct(snapshot));
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    switch (error.getCode()){
                        case DatabaseError.PERMISSION_DENIED:
                            listener.onError(R.string.main_error_permission_denied);
                            break;
                        default:
                            listener.onError(R.string.main_error_server);
                    }
                }
            };
        }
        mDatabaseAPI.getProductsReference().addChildEventListener(mProductChildEventListener);
    }

    private Product getProduct(DataSnapshot snapshot) {
        Product product= snapshot.getValue(Product.class);
        if(product!=null){
            product.setId(snapshot.getKey());
        }

        return product;
    }

    public void unSuscribeToProduct(){
        if(mProductChildEventListener !=null){
            mDatabaseAPI.getProductsReference().removeEventListener(mProductChildEventListener);
        }
    }

    public void removeProduct(Product product, BasicErrorEventCallBack errorEventCallBack){
        mDatabaseAPI.getProductsReference().child(product.getId()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if(error==null){
                    errorEventCallBack.onSuccess();
                }else{
                    switch (error.getCode()){
                        case DatabaseError.PERMISSION_DENIED:
                            errorEventCallBack.onError(MainEvent.ERROR_TO_REMOVE, R.string.main_error_remove);
                            break;
                        default:
                            errorEventCallBack.onError(MainEvent.ERROR_SERVER, R.string.main_error_server);
                            break;
                    }
                }
            }
        });
    }

}
