package com.room.sample.ui;

import android.view.View;

import androidx.databinding.BindingAdapter;

/**
 * Copyright (c), 2018-2019
 *
 * @author: lixin
 * Date: 2019/4/11
 * Description: 设置自定义属性visibleGone
 */
public class BindingAdapters {

    @BindingAdapter(value = "visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
