package com.room.sample.db;

import android.content.Context;
import android.util.Log;

import com.room.sample.AppExecutors;
import com.room.sample.db.converter.DateConverter;
import com.room.sample.db.dao.CommentDao;
import com.room.sample.db.dao.ProductDao;
import com.room.sample.db.entity.CommentEntity;
import com.room.sample.db.entity.ProductEntity;
import com.room.sample.db.entity.ProductFtsEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Copyright (c), 2018-2019
 *
 * @author: lixin
 * Date: 2019/4/11
 * Description:
 */
@Database(entities = {ProductEntity.class, ProductFtsEntity.class, CommentEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase ourInstance;

    public static final String DATABASE_NAME = "AppDb";

    public abstract ProductDao getProductDao();

    public abstract CommentDao getCommentDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(Context context, AppExecutors executors) {
        if (ourInstance == null) {
            synchronized (AppDatabase.class) {
                if (ourInstance == null) {
                    ourInstance = AppDatabase.buildDatabase(context.getApplicationContext(), executors);
                    ourInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return ourInstance;
    }

    private static AppDatabase buildDatabase(Context appContext, AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.getDiskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                addDelay();
                                AppDatabase database = AppDatabase.getInstance(appContext, executors);
                                List<ProductEntity> productEntities = DateGenerator.generateProducts();
                                List<CommentEntity> commentEntities = DateGenerator.generateCommentsForProducts(productEntities);

                                insertData(database, productEntities, commentEntities);
                                database.setDatabaseCreated();
                            }
                        });
                    }
                })
                .build();
    }

    private void updateDatabaseCreated(Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    private static void insertData(AppDatabase database, List<ProductEntity> productEntities, List<CommentEntity> commentEntities) {
        database.runInTransaction(new Runnable() {
            @Override
            public void run() {
                database.getProductDao().insertAll(productEntities);
                database.getCommentDao().insertAll(commentEntities);
            }
        });
    }

    /**
     * 模拟耗时操作
     */
    private static void addDelay() {
        try {
            Thread.sleep(4 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
