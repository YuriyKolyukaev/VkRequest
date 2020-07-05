package ru.vkinquiry.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import ru.vkinquiry.MyApplication;
import ru.vkinquiry.common.utils.VkListHelper;
import ru.vkinquiry.model.CommentItem;
import ru.vkinquiry.model.Place;
import ru.vkinquiry.model.view.BaseViewModel;
import ru.vkinquiry.model.view.CommentBodyViewModel;
import ru.vkinquiry.model.view.CommentFooterViewModel;
import ru.vkinquiry.model.view.CommentHeaderViewModel;
import ru.vkinquiry.mvp.view.BaseFeedView;
import ru.vkinquiry.rest.api.WallApi;
import ru.vkinquiry.rest.model.request.WallGetCommentsRequestModel;

@InjectViewState
public class CommentsPresenter extends BaseFeedPresenter<BaseFeedView> {

    @Inject
    WallApi mWallApi;
    private Place mPlace;


    public CommentsPresenter() {
        MyApplication.getsApplicationComponent().inject(this);
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservable(int count, int offset) {
        return mWallApi.getComments(new WallGetCommentsRequestModel(
                Integer.parseInt(mPlace.getOwnerId()), Integer.parseInt(mPlace.getPostId()), offset).toMap())
                .flatMap(full -> Observable.fromIterable(VkListHelper.getCommentsList(full.response)))
                .doOnNext(commentItem -> commentItem.setPlace(mPlace))
                .doOnNext(this::saveToDb)
                .flatMap(commentItem -> Observable.fromIterable(parsePojoModel(commentItem)));
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservable() {
        return Observable.fromCallable(getListFromRealmCallable())
                .flatMap(Observable::fromIterable)
                .filter(commentItem -> commentItem.getPlace().equals(this.mPlace) && !commentItem.isFromTopic)
                .flatMap(commentItem -> Observable.fromIterable(parsePojoModel(commentItem)));
    }


    private List<BaseViewModel> parsePojoModel(CommentItem commentItem) {
        List<BaseViewModel> baseItems = new ArrayList<>();
        baseItems.add(new CommentHeaderViewModel(commentItem));
        baseItems.add(new CommentBodyViewModel(commentItem));
        baseItems.add(new CommentFooterViewModel(commentItem));
        return baseItems;
    }


    public Callable<List<CommentItem>> getListFromRealmCallable() {
        return () -> {
            String[] sortFields = {"id"};
            Sort[] sortOrder = {Sort.ASCENDING};

            Realm realm = Realm.getDefaultInstance();
            RealmResults<CommentItem> results = realm.where(CommentItem.class)
                    .findAllSorted(sortFields, sortOrder);
            return realm.copyFromRealm(results);
        };
    }


    // метод для прявязки комментария к записи.
    public void setPlace(Place place) {
        this.mPlace = place;
    }
}
