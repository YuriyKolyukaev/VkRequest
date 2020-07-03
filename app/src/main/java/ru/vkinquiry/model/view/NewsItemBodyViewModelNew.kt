package ru.vkinquiry.model.view

import android.view.View
import ru.vkinquiry.model.WallItem
import ru.vkinquiry.ui.holder.BaseViewHolder
import ru.vkinquiry.ui.holder.NewsItemBodyHolder

class NewsItemBodyViewModelNew(val wallItem: WallItem) : BaseViewModel() {

    val mId: Int = wallItem.id
    val mText: String
    val mAttachmentString: String
    private val mIsRepost = wallItem.haveSharedRepost()

    private val a = 2
    private val b = 3


    init {
        if (mIsRepost) {
            mText = wallItem.sharedRepost.text
            mAttachmentString = wallItem.sharedRepost.attachmentsString
        } else {
            mText = wallItem.text
            mAttachmentString = wallItem.attachmentsString
        }
    }

    override fun getType(): LayoutTypes = LayoutTypes.NewsFeedItemBody

    override fun onCreateViewHolder(view: View?): ru.vkinquiry.ui.view.holder.BaseViewHolder<*>? {
        return NewsItemBodyHolder(view!!)
    }
}