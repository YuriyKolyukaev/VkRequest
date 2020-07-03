package ru.vkinquiry.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.ButterKnife;
import ru.vkinquiry.R;
import ru.vkinquiry.mvp.presenter.BaseFeedPresenter;
import ru.vkinquiry.mvp.presenter.MembersPresenter;

public class MembersFragment extends BaseFeedFragment {

    @InjectPresenter
    MembersPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    protected BaseFeedPresenter onCreateFeedPresenter() {
        return mPresenter;
    }

    // Возвращаем строку для заголовка экрана
    @Override
    public int onCreateToolbarTitle() {
        return R.string.screen_name_members;
    }
}
