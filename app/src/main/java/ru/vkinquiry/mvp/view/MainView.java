package ru.vkinquiry.mvp.view;

import com.arellomobile.mvp.MvpView;

import ru.vkinquiry.model.Profile;
import ru.vkinquiry.ui.fragment.BaseFragment;

public interface MainView extends MvpView {
    // начать вход в
    void startSignIn();

    // вошел
    void signedId();

    void showCurrentUser(Profile profile);

    void showFragmentFromDrawer(BaseFragment baseFragment);
}
