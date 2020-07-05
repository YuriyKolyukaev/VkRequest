package ru.vkinquiry.mvp.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;
import ru.vkinquiry.CurrentUser;
import ru.vkinquiry.MyApplication;
import ru.vkinquiry.common.manager.MyFragmentManager;
import ru.vkinquiry.common.manager.NetworkManager;
import ru.vkinquiry.model.Profile;
import ru.vkinquiry.mvp.view.MainView;
import ru.vkinquiry.rest.api.UsersApi;
import ru.vkinquiry.rest.model.request.UsersGetRequestModel;
import ru.vkinquiry.ui.activity.SettingActivity;
import ru.vkinquiry.ui.fragment.BaseFragment;
import ru.vkinquiry.ui.fragment.BoardFragment;
import ru.vkinquiry.ui.fragment.GroupRulesFragment;
import ru.vkinquiry.ui.fragment.InfoFragment;
import ru.vkinquiry.ui.fragment.MembersFragment;
import ru.vkinquiry.ui.fragment.MyPostsFragment;
import ru.vkinquiry.ui.fragment.NewsFeedFragment;

/*Эта аннотация нужня для привязки ViewState к Presenter. Сущность ViewState хранит в себе список
 команд которые были переданы из Presenter'а во View*/
@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject
    MyFragmentManager myFragmentManager;

    @Inject
    UsersApi mUserApi;

    @Inject
    NetworkManager mNetworkManager;

    public void checkAuth() {
        // Если пользователь не авторизован
        if (!CurrentUser.isAutorized()) {
            // вызывается метод интерфейса MainView для старта авторизации
            getViewState().startSignIn();
        } else {
            getCurrentUser();
            /*в противном случае вызывается другой метод интерфейса для получения
            идентификатора авторизованного пользователя*/
            getViewState().signedId();
        }
    }

    public MainPresenter() {
        MyApplication.getsApplicationComponent().inject(this);
    }

    /* Обращается к методу UsersApi.get и оператором flatMap трансвормирует Observable listFull
    profile в Observable profile. Далее в операторе doOnNext сохраняет profile в базу данных с
    помощью метода saveToDb.*/
    public Observable<Profile> getProfileFromNetwork() {
        return mUserApi.get(new UsersGetRequestModel(CurrentUser.getId()).toMap())
                // Другая строка
                .flatMap(listFull -> Observable.fromIterable(listFull.response))
  //              .flatMap(Observable::fromIterable)
                .doOnNext(this::saveToDb);
    }

    private Observable<Profile> getProfileFromDb() {
        return Observable.fromCallable(getListFromRealmCallable());
    }

    public void saveToDb(RealmObject item) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(item));
    }

    public Callable<Profile> getListFromRealmCallable() {
        return () -> {
            Realm realm = Realm.getDefaultInstance();
            Profile realmResults = realm.where(Profile.class)
                    .equalTo("id", Integer.parseInt(CurrentUser.getId()))
                    .findFirst();
            return realm.copyFromRealm(realmResults);
        };
    }

    // Вызывается из Activity. Метод для заполнения Header'а данными пользователя.
    @SuppressLint("CheckResult")
    private void getCurrentUser() {
        mNetworkManager.getNetworkObservable()
                .flatMap(aBoolean -> {
                    if (!CurrentUser.isAutorized()) {
                        getViewState().startSignIn();
                    }
                    return aBoolean
                            ? getProfileFromNetwork()
                            : getProfileFromDb();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profile -> {
                    getViewState().showCurrentUser(profile);
                }, error -> {
                    error.printStackTrace();
                });
    }

    // Этот метод вызывается когда пользователь совершил нажатие на пункт Drawer'а.
    public void drawerItemClick(int id) {
        BaseFragment fragment = null;

        switch (id) {
            case 1:
                fragment = new NewsFeedFragment();
                break;
            case 2:
                fragment = new MyPostsFragment();
                break;
            case 3:
                getViewState().startActivityFromDrawer(SettingActivity.class);
                break;
            case 4:
                fragment = new MembersFragment();
                break;
            case 5:
                fragment = new BoardFragment();
                break;
            case 6:
                fragment = new InfoFragment();
                break;
            case 7:
                fragment = new GroupRulesFragment();
                break;
        }
        if (fragment != null && !myFragmentManager.isAlreadyContains(fragment)) {
            getViewState().showFragmentFromDrawer(fragment);
        }
    }
}
