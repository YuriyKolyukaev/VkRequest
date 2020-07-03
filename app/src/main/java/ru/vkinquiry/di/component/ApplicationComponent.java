package ru.vkinquiry.di.component;

import javax.inject.Singleton;

import dagger.Component;
import ru.vkinquiry.common.manager.NetworkManager;
import ru.vkinquiry.di.module.ApplicationModule;
import ru.vkinquiry.di.module.ManagerModule;
import ru.vkinquiry.di.module.RestModule;
import ru.vkinquiry.mvp.presenter.BoardPresenter;
import ru.vkinquiry.mvp.presenter.InfoPresenter;
import ru.vkinquiry.mvp.presenter.MainPresenter;
import ru.vkinquiry.mvp.presenter.MembersPresenter;
import ru.vkinquiry.mvp.presenter.NewsFeedPresenter;
import ru.vkinquiry.mvp.presenter.OpenedPostPresenter;
import ru.vkinquiry.ui.activity.BaseActivity;
import ru.vkinquiry.ui.activity.MainActivity;
import ru.vkinquiry.ui.fragment.NewsFeedFragment;
import ru.vkinquiry.ui.fragment.OpenedPostFragment;
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

    //holders
    void inject (NewsItemBodyHolder holder);
    void inject (NewsItemFooterHolder holder);
    void inject (ImageAttachmentHolder holder);
    void inject(VideoAttachmentHolder holder);



    // presenters
    void inject(NewsFeedPresenter presenter);
    void inject(MainPresenter presenter);
    void inject(MembersPresenter presenter);
    void inject(BoardPresenter presenter);
    void inject(InfoPresenter presenter);
    void inject(OpenedPostPresenter presenter);

    // managers
    void inject (NetworkManager manager);
}
