package ru.vkinquiry.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vkinquiry.MyApplication;
import ru.vkinquiry.R;
import ru.vkinquiry.model.view.NewsItemFooterViewModel;
import ru.vkinquiry.mvp.presenter.BaseFeedPresenter;
import ru.vkinquiry.mvp.presenter.OpenedPostPresenter;
import ru.vkinquiry.mvp.view.OpenedPostView;
import ru.vkinquiry.ui.view.holder.NewsItemFooterHolder;

public class OpenedPostFragment extends BaseFeedFragment implements OpenedPostView {


    @BindView(R.id.rv_footer)
    View mFooter;

    @InjectPresenter
    OpenedPostPresenter mPresenter;

    int id;

    public static OpenedPostFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt("id", id);
        OpenedPostFragment fragment = new OpenedPostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getsApplicationComponent().inject(this);

        if (getArguments() != null) {
            this.id = getArguments().getInt("id");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    protected int getMainContentLayout() {
        return R.layout.fragment_opened_wall_item;
    }

    @Override
    public int onCreateToolbarTitle() {
        return R.string.screen_name_opened_post;
    }


    @Override
    protected BaseFeedPresenter onCreateFeedPresenter() {
        mPresenter.setId(id);
        return mPresenter;
    }

    @Override
    public void setFooter(NewsItemFooterViewModel viewModel) {
        mFooter.setVisibility(View.VISIBLE);
        new NewsItemFooterHolder(mFooter).bindViewHolder(viewModel);
    }

}
