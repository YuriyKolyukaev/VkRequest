package ru.vkinquiry.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vkinquiry.R;
import ru.vkinquiry.common.BaseAdapter;
import ru.vkinquiry.common.manager.MyLinearLayoutManager;
import ru.vkinquiry.model.view.BaseViewModel;
import ru.vkinquiry.mvp.presenter.BaseFeedPresenter;
import ru.vkinquiry.mvp.view.BaseFeedView;

public abstract class BaseFeedFragment extends BaseFragment implements BaseFeedView {

    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;

    BaseAdapter mBaseAdapter;

    @BindView(R.id.swipe_refresh)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected ProgressBar mProgressBar;

    protected BaseFeedPresenter mBaseFeedPresenter;

    private boolean isWithEndlessList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i("TEST_LOG_NFF", "onViewCreated: view = " + view + " Bundle = " + savedInstanceState);
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        setUpSwipeToRefreshLayout(view);

        setUpRecyclerView(view);
        setUpAdapter(mRecyclerView);

        mBaseFeedPresenter = onCreateFeedPresenter();                                               // за создание Presenter'а отвечает класс наследник
        mBaseFeedPresenter.loadStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isWithEndlessList = true;
    }

    private void setUpRecyclerView(View rootView) {

        /* Устанавливаем для RecyclerView кастомный LinearLayoutManager, который умеет проверять
        * нужно ли загружать новые элементы*/
        MyLinearLayoutManager myLinearLayoutManager = new MyLinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(myLinearLayoutManager);

        if (isWithEndlessList) {
            /* Слушает список и оповещает когда он скроллится. */
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    if (myLinearLayoutManager.isOnNextPagePosition()) {
                        mBaseFeedPresenter.loadNext(mBaseAdapter.getRealItemCount());
                    }
                }
            });
        }
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private void setUpAdapter(RecyclerView recyclerView) {
        mBaseAdapter = new ru.vkinquiry.common.BaseAdapter();

        recyclerView.setAdapter(mBaseAdapter);
    }

    @Override
    protected int getMainContentLayout() {
        return R.layout.fragment_feed;
    }

//    @Override
//    public int onCreateToolbarTitle() {
//        return 0;
//    }

    private void setUpSwipeToRefreshLayout(View rootView) {
        mSwipeRefreshLayout.setOnRefreshListener(() -> onCreateFeedPresenter().loadRefresh());

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mProgressBar = getBaseActivity().getProgressBar();
    }

    @Override
    public void showRefreshing() {

    }

    @Override
    public void hideRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showListProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideListProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getBaseActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setItems(List<BaseViewModel> items) {
        Log.i("TEST_LOG", "BaseFeedFragment setItems " + items  + "adapter "  + mBaseAdapter.toString());
        mBaseAdapter.setItems(items);
    }

    @Override
    public void addItems(List<BaseViewModel> items) {
        Log.i("TEST_LOG", "BaseFeedFragment addItems " + items  + "adapter "  + mBaseAdapter.toString());
        mBaseAdapter.addItems(items);
    }

    protected abstract BaseFeedPresenter onCreateFeedPresenter();

    public void setWithEndlessList(boolean withEndlessList) {
        isWithEndlessList = withEndlessList;
    }
}
