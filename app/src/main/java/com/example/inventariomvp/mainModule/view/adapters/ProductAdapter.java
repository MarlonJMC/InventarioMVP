package com.example.inventariomvp.mainModule.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.inventariomvp.R;
import com.example.inventariomvp.common.pojo.Product;
import com.example.inventariomvp.databinding.ItemProductBinding;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ItemProductBinding binding;
    private List<Product> products;
    private OnItemClickListener listener;
    private Context context;

    public ProductAdapter(List<Product> products, OnItemClickListener listener) {
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        binding = ItemProductBinding.inflate(LayoutInflater.from(context));
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        View view=binding.getRoot();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);

        holder.setOnClickListener(product, listener);

        holder.tvData.setText(context.getString(R.string.item_product_data, product.getName(),
                String.valueOf(product.getQuantity()) ));

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        Glide.with(context)
                .load(product.getPhotoURL())
                .apply(options)
                .into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void add(Product product){
        if (!products.contains(product)){
            products.add(product);
            notifyItemInserted(products.size()-1);
        } else {
            update(product);
        }
    }

    public void update(Product product) {
        if (products.contains(product)){
            final int index = products.indexOf(product);
            products.set(index, product);
            notifyItemChanged(index);
        }
    }

    public void remove(Product product){
        if (products.contains(product)){
            final int index = products.indexOf(product);
            products.remove(index);
            notifyItemRemoved(index);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        AppCompatImageView imgPhoto=binding.imgPhoto;
        TextView tvData=binding.tvData;

        private View view;

        ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }

        void setOnClickListener(final Product product, final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(product);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onLongItemClick(product);
                    return true;
                }
            });
        }
    }
}
