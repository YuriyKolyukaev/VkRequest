package ru.vkinquiry.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import ru.vkinquiry.CurrentUser;
import ru.vkinquiry.MyApplication;
import ru.vkinquiry.common.utils.VkListHelper;
import ru.vkinquiry.consts.ApiConstants;
import ru.vkinquiry.model.WallItem;
import ru.vkinquiry.model.view.BaseViewModel;
import ru.vkinquiry.model.view.NewsItemBodyViewModel;
import ru.vkinquiry.model.view.NewsItemFooterViewModel;
import ru.vkinquiry.model.view.NewsItemHeaderViewModel;
import ru.vkinquiry.mvp.view.BaseFeedView;
import ru.vkinquiry.rest.api.WallApi;
import ru.vkinquiry.rest.model.request.WallGetRequestModel;

// Реализует метод onCreateLoadDataObservable
@InjectViewState
public class NewsFeedPresenter extends BaseFeedPresenter<BaseFeedView> {

    private boolean enabledIdFiltering = false;

    @Inject
    WallApi mWallApi;

    public NewsFeedPresenter() {
        MyApplication.getsApplicationComponent().inject(this);
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservable(int count, int offset) {
        return mWallApi.get(new WallGetRequestModel(ApiConstants.MY_GROUP_ID, count, offset).toMap())
                .flatMap(full -> Observable.fromIterable(VkListHelper.getWallList(full.response)))
                .compose(applyFilter())
                // вызов сохранения
                .doOnNext(this::saveToDb)
                .flatMap(wallItem -> {
                    Log.i("TEST_LOG", "TEST_PRESENTER " + wallItem);
                    List<BaseViewModel> baseItems = new ArrayList<>();
                    baseItems.add(new NewsItemHeaderViewModel(wallItem));
                    baseItems.add(new NewsItemBodyViewModel(wallItem));
                    baseItems.add(new NewsItemFooterViewModel(wallItem));
                    return Observable.fromIterable(baseItems);
                });
    }

    public void setEnabledIdFiltering(boolean enabledIdFiltering) {
        this.enabledIdFiltering = enabledIdFiltering;
    }

    protected ObservableTransformer<WallItem, WallItem> applyFilter() {
        if (enabledIdFiltering && CurrentUser.getId() != null) {
            return baseItemObservable -> baseItemObservable.filter(
                    wallItem -> CurrentUser.getId().equals(String.valueOf(wallItem.getId()))
            );
        } else {
            return baseItemObservable -> baseItemObservable;
        }
    }

    // Метод для получения списка из БД
    public Callable<List<WallItem>> getListFromRealmCallable() {
        return () -> {
            String[] sortFields = {"date"};
            Sort[] sortOrder = {Sort.DESCENDING};
            Realm realm = Realm.getDefaultInstance();
            RealmResults<WallItem> realmResults = realm.where(WallItem.class)
                    .findAllSorted(sortFields, sortOrder);
            return realm.copyFromRealm(realmResults);
        };
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservable() {
        return Observable.fromCallable(getListFromRealmCallable())
                .flatMap(Observable::fromIterable)
                .compose(applyFilter())
                .flatMap(wallItem -> Observable.fromIterable(parsePojoModel(wallItem)));
    }

    private List<BaseViewModel> parsePojoModel (WallItem wallItem) {
        List<BaseViewModel> baseItems = new ArrayList<>();
        baseItems.add(new NewsItemHeaderViewModel(wallItem));
        baseItems.add(new NewsItemBodyViewModel(wallItem));
        baseItems.add(new NewsItemFooterViewModel(wallItem));

        return baseItems;

    }

}