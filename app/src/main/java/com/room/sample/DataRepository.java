package com.room.sample;

import com.room.sample.db.AppDatabase;
import com.room.sample.db.entity.CommentEntity;
import com.room.sample.db.entity.ProductEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

/**
 * Copyright (c), 2018-2019
 *
 * @author: lixin
 * Date: 2019/4/11
 * Description:
 */
public class DataRepository {
    private static DataRepository ourInstance;

    private AppDatabase mAppDatabase;

    private MediatorLiveData<List<ProductEntity>> mObservableProducts;

    private DataRepository(AppDatabase database) {
        mAppDatabase = database;
        mObservableProducts = new MediatorLiveData<>();

        mObservableProducts.addSource(mAppDatabase.getProductDao().loadAllProducts(),
                productEntities -> {
                    if (mAppDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableProducts.postValue(productEntities);
                    }
                });
    }

    public static DataRepository getInstance(AppDatabase database) {
        if (ourInstance == null) {
            synchronized (DataRepository.class) {
                if (ourInstance == null) {
                    ourInstance = new DataRepository(database);
                }
            }
        }
        return ourInstance;
    }

    public LiveData<List<ProductEntity>> getProducts() {
        return mObservableProducts;
    }

    public LiveData<ProductEntity> loadProduct(int productId) {
        return mAppDatabase.getProductDao().loadProduct(productId);
    }

    public LiveData<List<CommentEntity>> loadComments(int productId) {
        return mAppDatabase.getCommentDao().loadComments(productId);
    }

    public LiveData<List<ProductEntity>> searchProducts(String query) {
        return mAppDatabase.getProductDao().searchAllProducts(query);
    }
}
