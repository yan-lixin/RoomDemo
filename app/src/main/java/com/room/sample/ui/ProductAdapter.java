package com.room.sample.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.room.sample.R;
import com.room.sample.databinding.ProductItemBinding;
import com.room.sample.model.Product;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Copyright (c), 2018-2019
 *
 * @author: lixin
 * Date: 2019/4/11
 * Description:
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<? extends Product> mProducts;

    private ProductClickCallback mProductClickCallback;

    public ProductAdapter(ProductClickCallback productClickCallback) {
        mProductClickCallback = productClickCallback;
        setHasStableIds(true);
    }

    public void setProducts(List<? extends Product> products) {
        if (mProducts == null) {
            mProducts = products;
            notifyItemRangeInserted(0, products.size());
        } else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mProducts.size();
                }

                @Override
                public int getNewListSize() {
                    return products.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    Product oldItem = mProducts.get(oldItemPosition);
                    Product newItem = products.get(newItemPosition);
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Product oldItem = mProducts.get(oldItemPosition);
                    Product newItem = products.get(newItemPosition);
                    return oldItem.getId() == newItem.getId()
                            && oldItem.getPrice() == newItem.getPrice()
                            && Objects.equals(oldItem.getName(), newItem.getName())
                            && Objects.equals(oldItem.getDescription(), newItem.getDescription());
                }
            });
            mProducts = products;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.product_item,
                parent,
                false
        );
        binding.setCallback(mProductClickCallback);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mBinding.setProduct(mProducts.get(position));
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mProducts == null ? 0 : mProducts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ProductItemBinding mBinding;
        public ViewHolder(ProductItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}
