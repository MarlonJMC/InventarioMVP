package com.example.inventariomvp.detailModule.view;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.inventariomvp.R;
import com.example.inventariomvp.common.pojo.Product;
import com.example.inventariomvp.common.utils.CommonUtils;
import com.example.inventariomvp.databinding.FragmentDetailBinding;
import com.example.inventariomvp.detailModule.presenter.DetailProductPresenter;
import com.example.inventariomvp.detailModule.presenter.DetailProductPresenterClass;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;


public class DetailFragment extends Fragment implements DetailProductView{

    FragmentDetailBinding binding;
    private Product mProduct;
    private DetailProductPresenter mPresenter;

    public DetailFragment() {
        mPresenter= new DetailProductPresenterClass(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater,container,false);
        if(getArguments()!=null){
            configProduct(getArguments());
            configValues();
            configEditText();
            binding.btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(CommonUtils.validateProduct(getActivity(),binding.etName, binding.etPhotoUlr,binding.etQuantity)){
                        mProduct.setName(binding.etName.getText().toString().trim());
                        mProduct.setPhotoURL(binding.etPhotoUlr.getText().toString().trim());
                        mProduct.setQuantity(Integer.parseInt(binding.etQuantity.getText().toString().trim()));
                        mPresenter.updateProduct(mProduct);
                    }
                }
            });
//            clickBtnSave();
        }
        View view=binding.getRoot();
        mPresenter.onCreate();
        return view; //Return view
    }

    private void configEditText() {
        binding.etPhotoUlr.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String photoURL=binding.etPhotoUlr.getText().toString().trim();
                        if(photoURL.isEmpty()){
                            binding.imgPhoto.setImageDrawable(null);
                        }else{
                            configPhoto(photoURL);
                        }
                    }
                }
        );
    }

    private void clickBtnSave(){

    }

    private void configValues() {
        binding.etName.setText(mProduct.getName());
        binding.etPhotoUlr.setText(mProduct.getPhotoURL());
        binding.etQuantity.setText(String.valueOf(mProduct.getQuantity()));
        binding.etName.setText(mProduct.getName());
        configPhoto(mProduct.getPhotoURL());
    }

    private void configPhoto(String photoURL) {
        if(getActivity()!=null){
            RequestOptions options= new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop();
            Glide.with(getActivity())
                    .load(photoURL)
                    .apply(options)
                    .into(binding.imgPhoto);
        }
    }

    private void configProduct(Bundle arguments) {
        mProduct=new Product();
        mProduct.setId(arguments.getString(Product.ID));
        mProduct.setName(arguments.getString(Product.NAME));
        mProduct.setPhotoURL(arguments.getString(Product.PHOTOURL));
        mProduct.setQuantity(arguments.getInt(Product.QUANTITY));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDestroy();
        binding = null;
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
    public void enableUIElements() {
        binding.etName.setEnabled(true);
        binding.etQuantity.setEnabled(true);
        binding.etPhotoUlr.setEnabled(true);
        binding.btnSave.setEnabled(true);
    }

    @Override
    public void disableUIElements() {
        binding.etName.setEnabled(false);
        binding.etQuantity.setEnabled(false);
        binding.etPhotoUlr.setEnabled(false);
        binding.btnSave.setEnabled(false);
    }

    @Override
    public void showFab() {
        if(getActivity()!=null)
        getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFab() {
        if(getActivity()!=null)
            getActivity().findViewById(R.id.fab).setVisibility(View.INVISIBLE);
    }

    @Override
    public void updateSuccess(){
       // ConstraintLayout layout= requireActivity().findViewById(R.id.layoutConstraint);

        Snackbar.make(binding.contentMainf, R.string.detailProduct_update_successfully, Snackbar.LENGTH_LONG)
        .setAction(R.string.detailProduct_snackBar_Action, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity()!=null){
                    getActivity().onBackPressed();
                }
            }
        }).show();
    }

    @Override
    public void updateError() {
//        ConstraintLayout layout= requireActivity().findViewById(R.id.layoutConstraint);
        Snackbar.make(binding.contentMainf, R.string.detailProduct_update_error, Snackbar.LENGTH_LONG)
        .show();
    }
}