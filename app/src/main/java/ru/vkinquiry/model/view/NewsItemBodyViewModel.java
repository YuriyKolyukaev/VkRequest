package ru.vkinquiry.model.view;

import android.util.Log;
import android.view.View;

import ru.vkinquiry.model.WallItem;
import ru.vkinquiry.ui.view.holder.BaseViewHolder;
import ru.vkinquiry.ui.view.holder.NewsItemBodyHolder;

public class NewsItemBodyViewModel extends BaseViewModel {

    private int mId;
    private String mText;

    private String mAttachmentString;

    private boolean mIsRepost;

    public NewsItemBodyViewModel(WallItem wallItem) {
        mId = wallItem.getId();
        mIsRepost = wallItem.haveSharedRepost();

        if (mIsRepost) {
            mText = wallItem.getSharedRepost().getText();
            mAttachmentString = wallItem.getSharedRepost().getAttachmentsString();
        } else {
            mText = wallItem.getText();
            mAttachmentString = wallItem.getAttachmentsString();
        }
    }

    @Override
    public LayoutTypes getType() {
        Log.i("TEST_LOG", "");
        return LayoutTypes.NewsFeedItemBody;
    }

    @Override
    protected BaseViewHolder onCreateViewHolder(View view) {
        return new NewsItemBodyHolder(view);
    }

    public int getId() {
        return mId;
    }

    public String getText() {
        return mText;
    }

    public String getmAttachmentString() {
        return mAttachmentString;
    }

    @Override
    public boolean isItemDecorator() {
        return true;
    }
}
