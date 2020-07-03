package ru.vkinquiry.ui.view.holder;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vkinquiry.MyApplication;
import ru.vkinquiry.R;
import ru.vkinquiry.common.manager.MyFragmentManager;
import ru.vkinquiry.common.utils.UiHelper;
import ru.vkinquiry.model.view.NewsItemBodyViewModel;
import ru.vkinquiry.ui.activity.BaseActivity;
import ru.vkinquiry.ui.fragment.OpenedPostFragment;

public class NewsItemBodyHolder extends BaseViewHolder<NewsItemBodyViewModel> {

    @BindView(R.id.tv_text)
    public TextView mText;
    @BindView(R.id.tv_attachments)
    public TextView tvAttachments;

    @Inject
    protected Typeface mFontGoogle;

    @Inject
    MyFragmentManager mFragmentManager;

    public NewsItemBodyHolder(@NonNull View itemView) {
        super(itemView);
        MyApplication.getsApplicationComponent().inject(this);

        ButterKnife.bind(this, itemView);

        if (tvAttachments != null) {
            tvAttachments.setTypeface(mFontGoogle);
        }
    }

    @Override
    public void bindViewHolder(NewsItemBodyViewModel item) {
        mText.setText(item.getText());
        tvAttachments.setText(item.getmAttachmentString());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentManager.addFragment((BaseActivity) itemView.getContext(), OpenedPostFragment.newInstance(item.getId()),
                        R.id.main_wrapper);
            }
        });

        UiHelper.getInstance().setUpTextViewWithVisibility(mText, item.getText());
        UiHelper.getInstance().setUpTextViewWithVisibility(tvAttachments, item.getmAttachmentString());
    }

    @Override
    public void unbindViewHolder() {
        mText.setText(null);
        tvAttachments.setText(null);
        itemView.setOnClickListener(null);
    }
}