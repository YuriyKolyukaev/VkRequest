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
import ru.vkinquiry.model.view.BaseViewModel;
import ru.vkinquiry.model.view.counter.MemberViewModel;
import ru.vkinquiry.mvp.view.BaseFeedView;
import ru.vkinquiry.rest.api.GroupsApi;
import ru.vkinquiry.rest.model.request.GroupsGetMembersRequerstModel;


@InjectViewState
public class MembersPresenter extends BaseFeedPresenter<BaseFeedView> {

        @Inject
    GroupsApi mGroupsApi;

    public MembersPresenter() {
        MyApplication.getsApplicationComponent().inject(this);
    }



    /*onCreateLoadDataObservable - загрузка данных из сети. */
    @Override
    public Observable<BaseViewModel> onCreateLoadDataObservable(int count, int offset) {
        return mGroupsApi.getMembers(new GroupsGetMembersRequerstModel(
                ApiConstants.MY_GROUP_ID, count, offset).toMap())
                .flatMap(baseItemResponseFull -> {
                    return Observable.fromIterable(baseItemResponseFull.response.getItems());
                })
                //сохраняет member в базу данных.
                .doOnNext(member -> saveToDb(member))
                // трансформирует member в MemberViewModel
                .map(member -> new MemberViewModel(member));
    }

    // Загрузка данных из базы данных.
    @Override
    public Observable<BaseViewModel> onCreateRestoreDataObservable() {
        return Observable.fromCallable(getListFromRealmCallable())
        .flatMap(Observable::fromIterable)
                .map(member -> new MemberViewModel(member));
    }


    // Возвращает из базы данных список участников.
    public Callable<List<Member>> getListFromRealmCallable() {
        return () -> {
            String[] sortFields = {Member.ID};
            Sort[] sortOrder = {Sort.ASCENDING};

            Realm realm = Realm.getDefaultInstance();
            RealmResults<Member> results = realm.where(Member.class)
                    .findAllSorted(sortFields, sortOrder);
            return realm.copyFromRealm(results);
        };
    }
}
