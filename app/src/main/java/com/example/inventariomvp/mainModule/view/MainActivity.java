package com.example.inventariomvp.mainModule.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.inventariomvp.R;
import com.example.inventariomvp.addModule.view.AddProduct_Fragment;
import com.example.inventariomvp.common.pojo.Product;
import com.example.inventariomvp.databinding.ActivityMainBinding;
import com.example.inventariomvp.mainModule.presenter.MainPresenter;
import com.example.inventariomvp.mainModule.presenter.MainPresenterClass;
import com.example.inventariomvp.mainModule.view.adapters.OnItemClickListener;
import com.example.inventariomvp.mainModule.view.adapters.ProductAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnItemClickListener, MainView {

    private ActivityMainBinding binding;
    private MainPresenter mainPresenter;
    private ProductAdapter mProductAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= com.example.inventariomvp.databinding.ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ConfigurarBinding();
        configAdapter();
        configRecyclerView();

        mainPresenter = new MainPresenterClass(this);
        mainPresenter.onCreate();
    }

    private void ConfigurarBinding(){
        constraintLayout=binding.layoutConstraint;
        recyclerView=binding.recyclerView;
        progressBar=binding.progressBar;
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddProduct_Fragment().show(getSupportFragmentManager(),getString(R.string.addProduct_name));
            }
        });
    }

    private void configAdapter(){
        mProductAdapter=new ProductAdapter(new ArrayList<Product>(),this);
    }

    private void configRecyclerView(){
        LinearLayoutManager linearLayoutManager= new GridLayoutManager(this, getResources().getInteger(R.integer.main_columns));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mProductAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void add(Product product) {
        mProductAdapter.add(product);
    }

    @Override
    public void update(Product product) {
        mProductAdapter.update(product);
    }

    @Override
    public void remove(Product product) {
        mProductAdapter.remove(product);
    }

    @Override
    public void onShowError(int resMsg) {
        Snackbar.make(constraintLayout, R.string.main_error_remove, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void removeFail() {
        Snackbar.make(constraintLayout, R.string.main_error_remove, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(Product product) {
        Toast.makeText(this, "OnItemClick event", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongItemClick(Product product) { //Click largo para eliminar un elemento.
        Vibrator vibrator= (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if(vibrator!=null){
            vibrator.vibrate(60);
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.main_dialog_remove_title)
                .setMessage(R.string.main_dialog_remove_message)
                .setPositiveButton(R.string.main_dialog_remove_Ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mainPresenter.remove(product);
                    }
                }).setNegativeButton(R.string.common_dialog_cancel,null).show();
    }
}