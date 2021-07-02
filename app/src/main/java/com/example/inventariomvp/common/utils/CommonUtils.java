package com.example.inventariomvp.common.utils;

import android.content.Context;
import android.widget.EditText;

import com.example.inventariomvp.R;

public class CommonUtils {

    public static boolean validateProduct(Context context, EditText etName, EditText etPhotoURL, EditText etQuantity){
        boolean isValid=true;
        //Cantidad
            if(etQuantity.getText().toString().trim().isEmpty()){
                etQuantity.setError(context.getString(R.string.common_validate_requiered));
                etQuantity.requestFocus();
                isValid=false;
            }else if(Integer.parseInt(etQuantity.getText().toString().trim())<=0){
                etQuantity.setError(context.getString(R.string.common_validate_min_quantity));
                etQuantity.requestFocus();
                isValid=false;
            }

            if(etPhotoURL.getText().toString().trim().isEmpty()){
                etPhotoURL.setError(context.getString(R.string.common_validate_requiered));
                etPhotoURL.requestFocus();
                isValid=false;
            }

            if(etName.getText().toString().trim().isEmpty()){
                etName.setError(context.getString(R.string.common_validate_requiered));
                etName.requestFocus();
                isValid=false;
            }

        return isValid;
    }

}
