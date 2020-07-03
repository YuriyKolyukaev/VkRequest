package ru.vkinquiry.mvp.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;
import ru.vkinquiry.common.manager.NetworkManager;
import ru.vkinquiry.model.view.BaseViewModel;
import ru.vkinquiry.mvp.view.BaseFeedView;

public abstract class BaseFeedPresenter <V extends BaseFeedView> extends MvpPresenter<V> {

    public static final int START_PAGE_SIZE = 15;
    public static final int NEXT_PAGE_SIZE = 15;

    private boolean mIsInLoading;


    @Inject
    NetworkManager mNetworkManager;


    // инициирует загрузку данных с помощью метода onCreateLoadDataObservable
    @SuppressLint("CheckResult")
    public void loadData (ProgressType progressType, int offset, int count) {
        if (mIsInLoading) {
            return;
        }
        mIsInLoading = true;

        /* Этот Observable возвращает булевую переменную: если true то устройство имеет доступ к
        * VkApi, false - нет. В методе loadData с помощью оператора flatMap мы трансформируем
        * Observable с параметром boolean в Observable с параметром BaseViewModel, который
        * в зависимости от доступа к Api излучает либо данные из сети либо данные из базы данных.*/
        mNetworkManager.getNetworkObservable()
                .flatMap(aBoolean -> {
                    if (!aBoolean && offset > 0) {
                        return Observable.empty();
                    }
                    return aBoolean
                            ? onCreateLoadDataObservable(count, offset)
                            : onCreateRestoreDataObservable();
                })
        /* В зависимости от статуса загрузки будут вызываться методы onLoadStart, onLoadFinish,
        * onLoadingSuccess или onLoadingFail.*/
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    onLoadingStart(progressType);
                })
                .doFinally(() -> {
                    onLoadingFinish(progressType);
                })
                .subscribe(repositories -> {
                    onLoadingSuccess(progressType, repositories);
                }, error -> {
                    error.printStackTrace();
                    onLoadingFailed(error);
                });

    }
    // Абстрактный метод для создания Observable, который загружает данные, взятые из сети
    public abstract Observable<BaseViewModel> onCreateLoadDataObservable(int count, int offset);

    public enum ProgressType {
        Refreshing, ListProgress, Paging
    }

    // метод который в зависимости от типа загрузки будет показывать различные прогресс бары
    public void showProgress (ProgressType progressType) {
        switch (progressType)   {
            case Refreshing:
                getViewState().showRefreshing();
                break;
            case ListProgress:
                getViewState().showListProgress();
                break;
        }
    }

    // скрывать прогресс бары
    public void hideProgress (ProgressType progressType) {
        switch (progressType) {
            case Refreshing:
                getViewState().hideRefreshing();
                break;
            case ListProgress:
                getViewState().hideListProgress();
                break;
        }
    }

    /*Методы loadStart, loadNext и loadRefresh будут вызывать метод loadData с различными
    параметрами, зависящими от типа загрузки. loadStart вызывается при первой загрузке*/
    public void loadStart() {
        loadData(ProgressType.ListProgress, 0,START_PAGE_SIZE);
    }

    // loadNext вызывается при загрузке новых элементов при прокрутке
    public  void loadNext (int offset) {
        loadData(ProgressType.Paging, offset, NEXT_PAGE_SIZE);
    }

    // loadRefresh вызывается при обновлении списка
    public void loadRefresh() {
        loadData(ProgressType.Refreshing, 0, START_PAGE_SIZE);
    }

    public void onLoadingStart(ProgressType progressType) {
        showProgress(progressType);
    }

    public void onLoadingFinish(ProgressType progressType) {
        mIsInLoading = false;
        hideProgress(progressType);
    }

    public void onLoadingFailed(Throwable throwable) {
        getViewState().showError(throwable.getMessage());
    }

    public void onLoadingSuccess(ProgressType progressType, List<BaseViewModel> items) {
        Log.i("TEST_LOG", "onLoadingSuccess " + items);
        if (progressType == ProgressType.Paging) {
            getViewState().addItems(items);
        } else {
            getViewState().setItems(items);
        }
    }

    // Вызываем тогда, когда хотим сохранить данные
    public void saveToDb(RealmObject item) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(item));
    }

    /* Работает аналогично onCreateLoadDataObservable, только загружает данные не из сети а
    из базы данных*/
    public abstract Observable<BaseViewModel> onCreateRestoreDataObservable();
}
