package ru.vkinquiry.ui.view.holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;
import ru.vkinquiry.MyApplication;
import ru.vkinquiry.R;
import ru.vkinquiry.common.manager.MyFragmentManager;
import ru.vkinquiry.common.utils.Utils;
import ru.vkinquiry.common.utils.VkListHelper;
import ru.vkinquiry.model.Place;
import ru.vkinquiry.model.WallItem;
import ru.vkinquiry.model.countable.Likes;
import ru.vkinquiry.model.view.NewsItemFooterViewModel;
import ru.vkinquiry.model.view.counter.CommentCounterViewModel;
import ru.vkinquiry.model.view.counter.LikeCounterViewModel;
import ru.vkinquiry.model.view.counter.RepostCounterViewModel;
import ru.vkinquiry.rest.api.LikeEventOnSubscribe;
import ru.vkinquiry.rest.api.WallApi;
import ru.vkinquiry.rest.model.request.WallGetByIdRequestModel;
import ru.vkinquiry.ui.activity.BaseActivity;
import ru.vkinquiry.ui.fragment.CommentsFragment;

public class NewsItemFooterHolder extends BaseViewHolder<NewsItemFooterViewModel> {

    @BindView(R.id.tv_date)
    public TextView tvDate;

    @BindView(R.id.tv_likes_count)
    public TextView tvLikesCount;

    @BindView(R.id.tv_likes_icon)
    public TextView tvLikesIcon;

    @BindView(R.id.tv_comments_count)
    public TextView tvCommentsCount;

    @BindView(R.id.tv_comments_icon)
    public TextView tvCommentsIcon;

    @BindView(R.id.tv_reposts_count)
    public TextView tvRepostCount;

    @BindView(R.id.tv_reposts_icon)
    public TextView tvRepostIcon;

    public static final String POST = "post";
    @BindView(R.id.rl_comments)
    public View rlComments;
    @BindView(R.id.rl_likes)
    public View rlLikes;

    @Inject
    Typeface mGoogleFontTypeface;
    @Inject
    WallApi mWallApi;
    @Inject
    MyFragmentManager myFragmentManager;

    private Resources mResources;
    private Context mContext;

    public NewsItemFooterHolder(@NonNull View itemView) {
        super(itemView);
        MyApplication.getsApplicationComponent().inject(this);

        ButterKnife.bind(this, itemView);

        mContext = itemView.getContext();
        mResources = itemView.getResources();

        // устанавливаем шрифт для полей иконок.
        tvLikesIcon.setTypeface(mGoogleFontTypeface);
        tvCommentsIcon.setTypeface(mGoogleFontTypeface);
        tvRepostIcon.setTypeface(mGoogleFontTypeface);
    }

    @Override
    public void bindViewHolder(NewsItemFooterViewModel item) {
        tvDate.setText(Utils.parseDate(item.getmDateLong(), mContext));

        bindLikes(item.getmLikes());
        bindComments(item.getmComments());
        bindRepost(item.getmReposts());


        rlComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myFragmentManager.addFragment((BaseActivity) view.getContext(),
                        CommentsFragment.newInstance(new Place(String.valueOf(item.getOwnerId()), String.valueOf(item.getmId()))),
                        R.id.main_wrapper);
            }
        });

        rlLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                like(item);
            }
        });
    }

    /*методы для установки иконок и счетчиков для комментариев, лайков и репостов в соответвиющие
    текстовые поля в footer'е записи*/
    private void bindLikes(LikeCounterViewModel likes) {
        tvLikesCount.setText(String.valueOf(likes.getCount()));
        tvLikesCount.setTextColor(mResources.getColor(likes.getTextColor()));
        tvLikesIcon.setTextColor(mResources.getColor(likes.getIconColor()));
    }

    private void bindComments(CommentCounterViewModel comments) {
        tvCommentsCount.setText(String.valueOf(comments.getCount()));
        tvCommentsCount.setTextColor(mResources.getColor(comments.getTextColor()));
        tvCommentsIcon.setTextColor(mResources.getColor(comments.getIconColor()));
    }

    private void bindRepost(RepostCounterViewModel repost) {
        tvRepostCount.setText(String.valueOf(repost.getCount()));
        tvRepostCount.setTextColor(mResources.getColor(repost.getTextColor()));
        tvRepostIcon.setTextColor(mResources.getColor(repost.getIconColor()));
    }

    @Override
    public void unbindViewHolder() {

        tvDate.setText(null);
        tvLikesCount.setText(null);
        tvCommentsCount.setText(null);
        tvRepostCount.setText(null);
    }

    public void saveToDb(RealmObject item) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(item));
    }

    public Observable<LikeCounterViewModel> likeObservable(int ownerId, int postId, Likes likes) {
        return Observable.create(new LikeEventOnSubscribe(POST, ownerId, postId, likes))

                .observeOn(Schedulers.io())
                .flatMap(count -> {

                    return mWallApi.getById(new WallGetByIdRequestModel(ownerId, postId).toMap());
                })
                .flatMap(full -> Observable.fromIterable(VkListHelper.getWallList(full.response)))
                .doOnNext(this::saveToDb)
                .map(wallItem -> new LikeCounterViewModel(wallItem.getLikes()));
    }

    @SuppressLint("CheckResult")
    public void like(NewsItemFooterViewModel item) {
        WallItem wallItem = getWallItemFromRealm(item.getmId());
        likeObservable(wallItem.getOwnerId(), wallItem.getId(), wallItem.getLikes())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(likes -> {
                    item.setmLikes(likes);
                    bindLikes(likes);
                }, error -> {
                    error.printStackTrace();
                });
    }

    public WallItem getWallItemFromRealm(int postId) {
        Realm realm = Realm.getDefaultInstance();
        WallItem wallItem = realm.where(WallItem.class)
                .equalTo("id", postId)
                .findFirst();

        return realm.copyFromRealm(wallItem);
    }
}
