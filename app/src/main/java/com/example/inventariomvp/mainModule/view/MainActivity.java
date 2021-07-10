package com.example.inventariomvp.mainModule.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ProgressBar;
import com.example.inventariomvp.R;
import com.example.inventariomvp.addModule.view.AddProduct_Fragment;
import com.example.inventariomvp.common.pojo.Product;
import com.example.inventariomvp.databinding.ActivityMainBinding;
import com.example.inventariomvp.detailModule.view.DetailFragment;
import com.example.inventariomvp.mainModule.presenter.MainPresenter;
import com.example.inventariomvp.mainModule.presenter.MainPresenterClass;
import com.example.inventariomvp.mainModule.view.adapters.OnItemClickListener;
import com.example.inventariomvp.mainModule.view.adapters.ProductAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnItemClickListener, MainView {

    private static final String FRAGMENTDETAIL = DetailFragment.class.getName();
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
    public void onItemClick(Product product){

        Bundle arguments = new Bundle();
        arguments.putString(Product.ID, product.getId());
        arguments.putString(Product.NAME, product.getName());
        arguments.putInt(Product.QUANTITY, product.getQuantity());
        arguments.putString(Product.PHOTOURL, product.getPhotoURL());

/*        getSupportFragmentManager().beginTransaction().add(R.id.contentMain,
                DetailFragment.instantiate(this, FRAGMENTDETAIL, arguments),
                FRAGMENTDETAIL).addToBackStack(null).commit();*/
        DetailFragment newFragment = new DetailFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
        transaction.replace(R.id.layoutConstraint, newFragment);
        newFragment.setArguments(arguments);
        transaction.addToBackStack(null);
// Commit the transaction
        transaction.commit();
    }

    @Override
    public void onLongItemClick(Product product) { //Click largo para eliminar un elemento.
        Vibrator vibrator= (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if(vibrator!=null){
            vibrator.vibrate(90);
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