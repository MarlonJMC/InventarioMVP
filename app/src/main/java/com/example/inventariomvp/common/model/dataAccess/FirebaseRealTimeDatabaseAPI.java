package com.example.inventariomvp.common.model.dataAccess;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseRealTimeDatabaseAPI {

    private static final String PATH_PRODUCTS="products";
    private DatabaseReference mReference;

    private static FirebaseRealTimeDatabaseAPI INSTANCE=null;

    private FirebaseRealTimeDatabaseAPI(){
        mReference= FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseRealTimeDatabaseAPI getInstance(){
        if(INSTANCE==null){ // Solo si es nula, se va a crear una instancia de la BD
            INSTANCE= new com.example.inventariomvp.common.model.dataAccess.FirebaseRealTimeDatabaseAPI();
        }
        return INSTANCE;
    }

    // Referencias;

    public DatabaseReference getmReference(){
        return mReference;
    }

    public DatabaseReference getProductsReference(){
        return getmReference().child(PATH_PRODUCTS);
    }

}
