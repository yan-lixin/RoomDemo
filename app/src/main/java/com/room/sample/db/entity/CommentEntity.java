package com.room.sample.db.entity;

import com.room.sample.model.Comment;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Copyright (c), 2018-2019
 *
 * @author: lixin
 * Date: 2019/4/11
 * Description:
 */
@Entity(tableName = "comments",
        foreignKeys = {
            @ForeignKey(entity = ProductEntity.class,
                    parentColumns = "id",
                    childColumns = "productId",
                    onDelete = ForeignKey.CASCADE)
        },
        indices = {
            @Index(value = "productId")
        }
)
public class CommentEntity implements Comment {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int productId;
    private String text;
    private Date postedAt;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getProductId() {
        return productId;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Date getPostedAt() {
        return postedAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPostedAt(Date postedAt) {
        this.postedAt = postedAt;
    }

    public CommentEntity() {
    }

    @Ignore
    public CommentEntity(int id, int productId, String text, Date postedAt) {
        this.id = id;
        this.productId = productId;
        this.text = text;
        this.postedAt = postedAt;
    }


}
