package ru.vkinquiry.model.view;

import android.view.View;

import ru.vkinquiry.model.WallItem;
import ru.vkinquiry.ui.view.holder.BaseViewHolder;
import ru.vkinquiry.ui.view.holder.NewsItemHeaderHolder;

public class NewsItemHeaderViewModel extends BaseViewModel {

    private int mId;

    private String mProfilePhoto;
    private String mProfileName;

    private boolean mIsRepost;                                                                      // логическая переменная для хранения информации о том является ли запись репостом
                                                                                                    // если да, то мы будем менять способ отображения header'а
    private String mRepostProfileName;                                                              // переменная для автора репоста

    public NewsItemHeaderViewModel(WallItem wallItem) {                                             // будем получать значения переменных через методы класса WallItem
        this.mId = wallItem.getId();
        this.mProfilePhoto = wallItem.getSenderPhoto();
        this.mProfileName = wallItem.getSenderName();

        this.mIsRepost = wallItem.haveSharedRepost();

        if (mIsRepost) {
            this.mRepostProfileName = wallItem.getSharedRepost().getSenderName();                   // переменной mRepostProfileName будем присваивать имя автора
        }                                                                                           // если переменная из Repost имеет значение true
    }

    @Override
    public LayoutTypes getType() {
        return LayoutTypes.NewsFeedItemHeader;
    }

    @Override
    protected BaseViewHolder onCreateViewHolder(View view) {
        return new NewsItemHeaderHolder(view);
    }

    public int getId() {
        return mId;
    }

    public String getProfilePhoto() {
        return mProfilePhoto;
    }

    public String getProfileName() {
        return mProfileName;
    }

    public boolean isRepost() {
        return mIsRepost;
    }

    public String getRepostProfileName() {
        return mRepostProfileName;
    }
}
