package ru.vkinquiry.ui.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import ru.vkinquiry.R;

// Здесь будет отображаться список записей текущего пользователя
public class MyPostsFragment extends NewsFeedFragment{

    public MyPostsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.setEnabledIdFiltering(true);
    }

    @Override
    public int onCreateToolbarTitle() {
        return R.string.screen_name_my_posts;
    }
}
