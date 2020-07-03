package ru.vkinquiry.model.view;

import android.view.View;

import ru.vkinquiry.model.WallItem;
import ru.vkinquiry.model.view.counter.CommentCounterViewModel;
import ru.vkinquiry.model.view.counter.LikeCounterViewModel;
import ru.vkinquiry.model.view.counter.RepostCounterViewModel;
import ru.vkinquiry.ui.view.holder.BaseViewHolder;
import ru.vkinquiry.ui.view.holder.NewsItemFooterHolder;

public class NewsItemFooterViewModel extends BaseViewModel {

    private int mId;
    private int ownerId;
    private long mDateLong;

    private LikeCounterViewModel mLikes;
    private CommentCounterViewModel mComments;
    private RepostCounterViewModel mReposts;

    public NewsItemFooterViewModel (WallItem wallItem) {
        this.mId = wallItem.getId();
        this.ownerId = wallItem.getOwnerId();
        this.mDateLong = wallItem.getDate();
        this.mLikes = new LikeCounterViewModel(wallItem.getLikes());
        this.mComments = new CommentCounterViewModel(wallItem.getComments());
        this.mReposts = new RepostCounterViewModel(wallItem.getReposts());
    }

    @Override
    public LayoutTypes getType() {
        return LayoutTypes.NewsFeedItemFooter;
    }

    @Override
    protected BaseViewHolder onCreateViewHolder(View view) {
        return new NewsItemFooterHolder(view);
    }

    public long getmDateLong() {
        return mDateLong;
    }

    public int getmId() {
        return mId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public LikeCounterViewModel getmLikes() {
        return mLikes;
    }

    public CommentCounterViewModel getmComments() {
        return mComments;
    }

    public RepostCounterViewModel getmReposts() {
        return mReposts;
    }

    public void setmDateLong(long mDateLong) {
        this.mDateLong = mDateLong;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setmLikes(LikeCounterViewModel mLikes) {
        this.mLikes = mLikes;
    }

    public void setmComments(CommentCounterViewModel mComments) {
        this.mComments = mComments;
    }

    public void setmReposts(RepostCounterViewModel mReposts) {
        this.mReposts = mReposts;
    }

    @Override
    public boolean isItemDecorator() {
        return true;
    }
}
