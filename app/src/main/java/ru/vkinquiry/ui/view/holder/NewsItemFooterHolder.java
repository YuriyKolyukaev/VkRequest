package ru.vkinquiry.ui.view.holder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vkinquiry.MyApplication;
import ru.vkinquiry.R;
import ru.vkinquiry.common.utils.Utils;
import ru.vkinquiry.model.view.NewsItemFooterViewModel;
import ru.vkinquiry.model.view.counter.CommentCounterViewModel;
import ru.vkinquiry.model.view.counter.LikeCounterViewModel;
import ru.vkinquiry.model.view.counter.RepostCounterViewModel;

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

    @Inject
    Typeface mGoogleFontTypeface;

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
}
