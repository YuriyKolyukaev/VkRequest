package ru.vkinquiry.di.component;

import javax.inject.Singleton;

import dagger.Component;
import ru.vkinquiry.common.manager.NetworkManager;
import ru.vkinquiry.di.module.ApplicationModule;
import ru.vkinquiry.di.module.ManagerModule;
import ru.vkinquiry.di.module.RestModule;
import ru.vkinquiry.model.view.CommentBodyViewModel;
import ru.vkinquiry.model.view.CommentFooterViewModel;
import ru.vkinquiry.model.view.InfoContactsViewModel;
import ru.vkinquiry.model.view.InfoLinksViewModel;
import ru.vkinquiry.model.view.TopicViewModel;
import ru.vkinquiry.mvp.presenter.BoardPresenter;
import ru.vkinquiry.mvp.presenter.CommentsPresenter;
import ru.vkinquiry.mvp.presenter.InfoContactsPresenter;
import ru.vkinquiry.mvp.presenter.InfoLinksPresenter;
import ru.vkinquiry.mvp.presenter.InfoPresenter;
import ru.vkinquiry.mvp.presenter.MainPresenter;
import ru.vkinquiry.mvp.presenter.MembersPresenter;
import ru.vkinquiry.mvp.presenter.NewsFeedPresenter;
import ru.vkinquiry.mvp.presenter.OpenedCommentPresenter;
import ru.vkinquiry.mvp.presenter.OpenedPostPresenter;
import ru.vkinquiry.mvp.presenter.TopicCommentsPresenter;
import ru.vkinquiry.ui.activity.BaseActivity;
import ru.vkinquiry.ui.activity.MainActivity;
import ru.vkinquiry.ui.fragment.CommentsFragment;
import ru.vkinquiry.ui.fragment.InfoContactsFragment;
import ru.vkinquiry.ui.fragment.InfoLinksFragment;
import ru.vkinquiry.ui.fragment.NewsFeedFragment;
import ru.vkinquiry.ui.fragment.OpenedCommentFragment;
import ru.vkinquiry.ui.fragment.OpenedPostFragment;
import ru.vkinquiry.ui.fragment.TopicCommentsFragment;
import ru.vkinquiry.ui.view.holder.NewsItemBodyHolder;
import ru.vkinquiry.ui.view.holder.NewsItemFooterHolder;
import ru.vkinquiry.ui.view.holder.attachment.ImageAttachmentHolder;
import ru.vkinquiry.ui.view.holder.attachment.VideoAttachmentHolder;

@Singleton
@Component(modules = {ApplicationModule.class, ManagerModule.class, RestModule.class})
public interface ApplicationComponent {

    // activities
    void inject (BaseActivity activity);
    void inject (MainActivity activity);

    // fragments
    void inject (NewsFeedFragment fragment);
    void inject (OpenedPostFragment fragment);

    void inject(CommentsFragment fragment);

    void inject(OpenedCommentFragment fragment);

    void inject(TopicCommentsFragment fragment);

    void inject(InfoLinksFragment fragment);

    void inject(InfoContactsFragment fragment);

    //holders
    void inject (NewsItemBodyHolder holder);
    void inject (NewsItemFooterHolder holder);
    void inject (ImageAttachmentHolder holder);
    void inject(VideoAttachmentHolder holder);

    void inject(CommentBodyViewModel.CommentBodyViewHolder holder);

    void inject(CommentFooterViewModel.CommentFooterHolder holder);

    void inject(TopicViewModel.TopicViewHolder holder);

    void inject(InfoLinksViewModel.InfoLinkViewHolder holder);

    void inject(InfoContactsViewModel.InfoContactsViewHolder holder);

    // presenters
    void inject(NewsFeedPresenter presenter);
    void inject(MainPresenter presenter);
    void inject(MembersPresenter presenter);
    void inject(BoardPresenter presenter);
    void inject(InfoPresenter presenter);
    void inject(OpenedPostPresenter presenter);

    void inject(CommentsPresenter presenter);

    void inject(OpenedCommentPresenter presenter);

    void inject(TopicCommentsPresenter presenter);

    void inject(InfoLinksPresenter presenter);

    void inject(InfoContactsPresenter presenter);

    // managers
    void inject (NetworkManager manager);
}
