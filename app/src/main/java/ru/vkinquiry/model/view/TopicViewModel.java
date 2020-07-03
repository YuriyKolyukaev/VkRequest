package ru.vkinquiry.model.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vkinquiry.R;
import ru.vkinquiry.model.Topic;
import ru.vkinquiry.ui.view.holder.BaseViewHolder;

public class TopicViewModel extends BaseViewModel {

    private int mid;
    private int mGroupId;
    private String mTitle;
    private String mCommentsCount;

    public TopicViewModel() {
    }

    public TopicViewModel(Topic topic) {
        this.mid = topic.getId();
        this.mGroupId = topic.getGroupId();
        this.mTitle = topic.getTitle();
        this.mCommentsCount = topic.getComments() + " сообщений";
    }

    @Override
    public LayoutTypes getType() {
        return LayoutTypes.Topic;
    }

    @Override
    protected ru.vkinquiry.ui.view.holder.BaseViewHolder onCreateViewHolder(View view) {
        return new TopicViewHolder(view);
    }

    public int getMid() {
        return mid;
    }

    public int getmGroupId() {
        return mGroupId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmCommentsCount() {
        return mCommentsCount;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public void setmGroupId(int mGroupId) {
        this.mGroupId = mGroupId;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setCommentsCount(String mCommentsCount) {
        this.mCommentsCount = mCommentsCount;
    }

    public static class TopicViewHolder extends BaseViewHolder<TopicViewModel> {

        @BindView(R.id.tv_title)
        public TextView tvTitle;

        @BindView(R.id.tv_comments_count)
        public TextView tvCommentsCount;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindViewHolder(TopicViewModel topicViewModel) {
            tvTitle.setText(topicViewModel.getmTitle());
            tvCommentsCount.setText((topicViewModel.getmCommentsCount()));

        }

        @Override
        public void unbindViewHolder() {
            tvTitle.setText(null);
            tvCommentsCount.setText(null);
        }
    }

}
