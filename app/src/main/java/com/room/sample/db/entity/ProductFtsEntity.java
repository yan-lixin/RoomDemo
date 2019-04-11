package com.room.sample.db.entity;

import androidx.room.Entity;
import androidx.room.Fts4;

/**
 * Copyright (c), 2018-2019
 *
 * @author: lixin
 * Date: 2019/4/11
 * Description:
 */
@Entity(tableName = "productsFts")
@Fts4(contentEntity = ProductEntity.class)
public class ProductFtsEntity {
    private String name;
    private String description;

    public ProductFtsEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
