package com.room.sample.db.dao;

import com.room.sample.db.entity.ProductEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * Copyright (c), 2018-2019
 *
 * @author: lixin
 * Date: 2019/4/11
 * Description:
 */
@Dao
public interface ProductDao {

    @Query("select * from products")
    LiveData<List<ProductEntity>> loadAllProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ProductEntity> productEntities);

    @Query("select * from products where id = :productId")
    LiveData<ProductEntity> loadProduct(int productId);

    @Query("select * from products where id = :productId")
    ProductEntity loadProductSync(int productId);

    @Query("select products.* from products join productsFts on (products.id == productsFts.rowid)" +
            "where productsFts MATCH :query")
    LiveData<List<ProductEntity>> searchAllProducts(String query);
}
