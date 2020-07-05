package ru.vkinquiry.mvp.view;

import com.arellomobile.mvp.MvpView;

import ru.vkinquiry.model.WallItem;
import ru.vkinquiry.model.view.counter.LikeCounterViewModel;

public interface PostFooterView extends MvpView {
    void like(LikeCounterViewModel likes);

    void openComments(WallItem wallItem);
}
