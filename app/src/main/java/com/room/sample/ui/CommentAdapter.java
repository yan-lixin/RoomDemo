package com.room.sample.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.room.sample.R;
import com.room.sample.databinding.CommentItemBinding;
import com.room.sample.model.Comment;

import java.util.ArrayList;
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
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<? extends Comment> mComments;

    private CommentClickCallback mCommentClickCallback;

    public CommentAdapter(CommentClickCallback commentClickCallback) {
        mCommentClickCallback = commentClickCallback;
    }

    public void setComments(List<? extends Comment> comments) {
        if (mComments == null) {
            mComments = comments;
            notifyItemRangeInserted(0, comments.size());
        } else {
            //使用DiffUtils的calculateDiff计算出不同数据的item，仅刷新有变动的数据。
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mComments.size();
                }

                @Override
                public int getNewListSize() {
                    return comments.size();
                }

                /**
                 * 判断新数据与旧数据item是否相同，用id进行判断
                 * @param oldItemPosition
                 * @param newItemPosition
                 * @return
                 */
                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    Comment oldItem = mComments.get(oldItemPosition);
                    Comment newItem = comments.get(newItemPosition);
                    return oldItem.getId() == newItem.getId();
                }

                /**
                 * 判断item中的数据是否相同，一般处理显示的字段就可以了，不需要全部判断。
                 * @param oldItemPosition
                 * @param newItemPosition
                 * @return
                 */
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Comment oldItem = mComments.get(oldItemPosition);
                    Comment newItem = comments.get(newItemPosition);
                    return oldItem.getId() == newItem.getId()
                            && oldItem.getPostedAt() == newItem.getPostedAt()
                            && oldItem.getProductId() == newItem.getProductId()
                            && Objects.equals(oldItem.getText(), newItem.getText());
                }
            });
            mComments = comments;
            /**
             * 调用dispatchUpdatesTo将数据变动反应到页面上
             * 将当前adapter包装成AdapterListUpdateCallback，调用其内部的notify等方法
             */
            diffResult.dispatchUpdatesTo(this);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.comment_item,
                parent, false
            );
        binding.setCallback(mCommentClickCallback);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mBinding.setComment(mComments.get(position));
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mComments == null ? 0 : mComments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CommentItemBinding mBinding;
        public ViewHolder(CommentItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
