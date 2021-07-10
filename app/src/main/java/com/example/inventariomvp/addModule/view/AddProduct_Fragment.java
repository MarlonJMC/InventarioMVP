package com.example.inventariomvp.addModule.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.inventariomvp.R;
import com.example.inventariomvp.addModule.presenter.AddProductPresenterClass;
import com.example.inventariomvp.common.pojo.Product;
import com.example.inventariomvp.common.utils.CommonUtils;
import com.example.inventariomvp.databinding.FragmentAddProductBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;


public class AddProduct_Fragment extends DialogFragment implements DialogInterface.OnShowListener, AddProductView {

    FragmentAddProductBinding binding;
    private AddProductPresenterClass mPresenter;

    public AddProduct_Fragment() {
        // Required empty public constructor
        mPresenter= new AddProductPresenterClass(this);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * //@param param1 Parameter 1.
     * //@param param2 Parameter 2.
     * //@return A new instance of fragment AddProduct_Fragment.
     */
    // TODO: Rename and change types and number of parameters
   /* public static AddProduct_Fragment newInstance(String param1, String param2) {
        AddProduct_Fragment fragment = new AddProduct_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Inflate the layout for this fragment+
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity())
                .setTitle(R.string.AddProduct_title_dialog)
                .setPositiveButton(R.string.AddProduct_dialog_Ok,null)
                .setNegativeButton(R.string.common_dialog_cancel,null);

        // Forma de inicializar el binding en Dialogs fragments, en fragmentos y activitys es diferente
        binding=FragmentAddProductBinding.inflate(LayoutInflater.from(getContext()));
        builder.setView(binding.getRoot());

        configFocus();
        configEditText();
        
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);

       // binding=FragmentAddProductBinding.inflate(getActivity().getLayoutInflater());

        return dialog;
    }

    private void configFocus() {
        binding.etName.requestFocus();
    }

    private void configEditText(){ //Al añadir la URL debe cargar la imagen en el preview

        binding.etPhotoUlr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final String photoUrl= binding.etPhotoUlr.getText().toString().trim();
                if(photoUrl.isEmpty()){
                    binding.imgPhoto.setImageDrawable(null);
                }else{
                    RequestOptions options= new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop();

                    Glide.with(getActivity())
                            .load(photoUrl)
                            .apply(options)
                            .into(binding.imgPhoto);
                }
            }
        });
    }

    @Override
    public void onShow(DialogInterface dialog) {
        final AlertDialog dialogIn= (AlertDialog) getDialog();
        if(dialogIn!=null){
            Button positiveButton= dialogIn.getButton(DialogInterface.BUTTON_POSITIVE);
            Button negativeButton= dialogIn.getButton(DialogInterface.BUTTON_NEGATIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(CommonUtils.validateProduct(getActivity(),binding.etName,binding.etPhotoUlr,binding.etQuantity)){
                        Product product=new Product();
                        product.setName(binding.etName.getText().toString().trim());
                        product.setPhotoURL(binding.etPhotoUlr.getText().toString().trim());
                        product.setQuantity(Integer.parseInt(binding.etQuantity.getText().toString().trim()));
                        mPresenter.addProduct(product);
                    }
                }
            });

            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        mPresenter.onShow();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
        mPresenter.onDestroy();
    }

    @Override
    public void enableUIElements() {
        binding.etName.setEnabled(true);
        binding.etQuantity.setEnabled(true);
        binding.etPhotoUlr.setEnabled(true);
    }

    @Override
    public void disableUIElements() {
        binding.etName.setEnabled(false);
        binding.etQuantity.setEnabled(false);
        binding.etPhotoUlr.setEnabled(false);
    }

    @Override
    public void showProgress() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void productAdded() {
        Toast.makeText(getActivity(),R.string.addPruductMessage_successfully_added,Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void showError(int resMsg) {
        Snackbar.make(binding.contentMain,resMsg, BaseTransientBottomBar.LENGTH_INDEFINITE).setAction(R.string.addProductSnackBar_action, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void maxValueError(int resMsg) {
        binding.etQuantity.setError(getString(resMsg));
        binding.etQuantity.requestFocus();
    }
}