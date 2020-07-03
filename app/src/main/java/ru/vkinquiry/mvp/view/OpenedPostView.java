package ru.vkinquiry.mvp.view;

import ru.vkinquiry.model.view.NewsItemFooterViewModel;

public interface OpenedPostView extends BaseFeedView {
    void setFooter(NewsItemFooterViewModel viewModel);
}
