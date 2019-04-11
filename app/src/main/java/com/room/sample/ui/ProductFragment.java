package com.room.sample.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.room.sample.R;
import com.room.sample.databinding.ProductFragmentBinding;
import com.room.sample.db.entity.CommentEntity;
import com.room.sample.db.entity.ProductEntity;
import com.room.sample.model.Comment;
import com.room.sample.viewmodel.ProductViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * Copyright (c), 2018-2019
 *
 * @author: lixin
 * Date: 2019/4/11
 * Description:
 */
public class ProductFragment extends Fragment {

    private static final String KEY_PRODUCT_ID = "product_id";
    private ProductFragmentBinding mBinding;
    private CommentAdapter mCommentAdapter;

    public static ProductFragment newInstance(int produtId) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_PRODUCT_ID, produtId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.product_fragment,
                container,
                false
        );
        mCommentAdapter = new CommentAdapter(mCommentClickCallback);
        mBinding.commentList.setAdapter(mCommentAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProductViewModel.Factory factory = new ProductViewModel.Factory(
                getActivity().getApplication(), getArguments().getInt(KEY_PRODUCT_ID)
        );
        ProductViewModel viewModel = ViewModelProviders.of(this, factory).get(ProductViewModel.class);
        mBinding.setProductViewModel(viewModel);
        subscribeToModel(viewModel);
    }

    private void subscribeToModel(ProductViewModel viewModel) {
        viewModel.getObservableProduct().observe(this, new Observer<ProductEntity>() {
            @Override
            public void onChanged(ProductEntity productEntity) {
                viewModel.setProduct(productEntity);
            }
        });

        viewModel.getComments().observe(this, new Observer<List<CommentEntity>>() {
            @Override
            public void onChanged(List<CommentEntity> commentEntities) {
                if (commentEntities != null) {
                    mBinding.setIsLoading(false);
                    mCommentAdapter.setComments(commentEntities);
                } else {
                    mBinding.setIsLoading(true);
                }
            }
        });
    }

    private final CommentClickCallback mCommentClickCallback = new CommentClickCallback() {
        @Override
        public void onClick(Comment comment) {
            Toast.makeText(getActivity(), comment.getText(), Toast.LENGTH_SHORT).show();
        }
    };
}
