package com.room.sample.viewmodel;

import android.app.Application;

import com.room.sample.App;
import com.room.sample.DataRepository;
import com.room.sample.db.entity.CommentEntity;
import com.room.sample.db.entity.ProductEntity;
import com.room.sample.model.Product;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Copyright (c), 2018-2019
 *
 * @author: lixin
 * Date: 2019/4/11
 * Description:
 */
public class ProductViewModel extends AndroidViewModel {

    private LiveData<ProductEntity> mObservableProduct;

    public ObservableField<ProductEntity> product = new ObservableField<>();

    private int mProductId;
    private LiveData<List<CommentEntity>> mObservableComments;


    public ProductViewModel(@NonNull Application application, DataRepository repository, int productId) {
        super(application);
        mProductId = productId;

        mObservableComments = repository.loadComments(productId);
        mObservableProduct = repository.loadProduct(productId);
    }

    public LiveData<List<CommentEntity>> getComments() {
        return mObservableComments;
    }

    public LiveData<ProductEntity> getObservableProduct() {
        return mObservableProduct;
    }

    public void setProduct(ProductEntity entity) {
        this.product.set(entity);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private Application mApplication;

        private int mProductId;

        private DataRepository mRepository;

        public Factory(Application application, int productId) {
            mApplication = application;
            mProductId = productId;
            mRepository = ((App) application).getRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ProductViewModel(mApplication, mRepository, mProductId);
        }
    }
}
