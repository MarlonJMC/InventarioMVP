package com.example.inventariomvp.detailModule.model.dataAccess;

import androidx.annotation.NonNull;

import com.example.inventariomvp.addModule.model.dataAccess.RealtimeDatabase;
import com.example.inventariomvp.common.BasicEventCallBack;
import com.example.inventariomvp.common.model.dataAccess.FirebaseRealTimeDatabaseAPI;
import com.example.inventariomvp.common.pojo.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class RealTimeDatabase {
    private FirebaseRealTimeDatabaseAPI mDatabaseAPI;

    public RealTimeDatabase(){
        mDatabaseAPI=FirebaseRealTimeDatabaseAPI.getInstance();
    }

    public void updateProduct(Product product, final BasicEventCallBack callback){
        mDatabaseAPI.getProductsReference().child(product.getId()).setValue(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError();
                    }
                });
    }


}
