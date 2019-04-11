package com.room.sample.viewmodel;

import android.app.Application;

import com.room.sample.App;
import com.room.sample.DataRepository;
import com.room.sample.db.entity.ProductEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

/**
 * Copyright (c), 2018-2019
 *
 * @author: lixin
 * Date: 2019/4/11
 * Description:
 */
public class ProductListViewModel extends AndroidViewModel {

    private DataRepository mRepository;

    /**
     * MediatorLiveData可以观察其他LiveData对象，并对其发送做出反应
     */
    private MediatorLiveData<List<ProductEntity>> mObservableProducts;

    public ProductListViewModel(@NonNull Application application) {
        super(application);
        mObservableProducts = new MediatorLiveData<>();

        //默认设置为null，直到从数据库获取到数据
        mObservableProducts.setValue(null);

        mRepository = ((App) application).getRepository();
        LiveData<List<ProductEntity>> products = mRepository.getProducts();

        //观察数据库中Product的数据变化并通过setValue转发
        mObservableProducts.addSource(products, mObservableProducts::setValue);
    }

    public LiveData<List<ProductEntity>> getProducts() {
        return mObservableProducts;
    }

    public LiveData<List<ProductEntity>> searchProducts(String query) {
        return mRepository.searchProducts(query);
    }
}
