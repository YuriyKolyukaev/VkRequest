package ru.vkinquiry.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import ru.vkinquiry.MyApplication;
import ru.vkinquiry.consts.ApiConstants;
import ru.vkinquiry.model.Member;
import ru.vkinquiry.model.Topic;
import ru.vkinquiry.model.view.BaseViewModel;
import ru.vkinquiry.model.view.TopicViewModel;
import ru.vkinquiry.mvp.view.BaseFeedView;
import ru.vkinquiry.rest.api.BoardApi;
import ru.vkinquiry.rest.model.request.BoardGetTopicsRequestModel;

@InjectViewState
public class BoardPresenter extends BaseFeedPresenter<BaseFeedView> {

    @Inject
    BoardApi mBoardApi;

    public BoardPresenter() {
        MyApplication.getsApplicationComponent().inject(this);
    }

    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservable(int count, int offset) {
        return mBoardApi.getTopics(new BoardGetTopicsRequestModel(
                ApiConstants.MY_GROUP_ID, count, offset
        ).toMap()).flatMap(baseItemResponseFull ->
                Observable.fromIterable(baseItemResponseFull.response.getItems()))
                .doOnNext(topic -> topic.setGroupId(ApiConstants.MY_GROUP_ID))
                .doOnNext(this::saveToDb)
                .map(TopicViewModel::new);
    }

    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservable() {
        return Observable.fromCallable(getListFromrealmCallable())
                .flatMap(Observable::fromIterable)
                .map(TopicViewModel::new);
    }

    public Callable<List<Topic>> getListFromrealmCallable() {
        return () -> {
            String[] sortFields = {Member.ID};
            Sort[] sortOrder = {Sort.DESCENDING};

            Realm realm = Realm.getDefaultInstance();
            RealmResults<Topic> results = realm.where(Topic.class)
                    .equalTo("groupId", ApiConstants.MY_GROUP_ID)
                    .findAllSorted(sortFields, sortOrder);

            return realm.copyFromRealm(results);
        };
    }
}
