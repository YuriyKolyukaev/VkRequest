package ru.vkinquiry.ui.activity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.LayoutRes;
import androidx.appcompat.widget.Toolbar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vkinquiry.MyApplication;
import ru.vkinquiry.R;
import ru.vkinquiry.common.manager.MyFragmentManager;
import ru.vkinquiry.ui.fragment.BaseFragment;

public abstract class BaseActivity extends MvpAppCompatActivity {

    @BindView(R.id.progress)
    protected ProgressBar mProgressBar;

    @Inject
    MyFragmentManager myFragmentManager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    public FloatingActionButton mFab;

    // переопределяем метод с одним параметром Bundle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // добавляем layout методом setContentView
        setContentView(R.layout.activity_base);

        ButterKnife.bind(this);

        MyApplication.getsApplicationComponent().inject(this);

        // подключаем toolbar
        setSupportActionBar(toolbar);

        FrameLayout parent = findViewById(R.id.main_wrapper);
        /*инфлейтим (надуваем) layout в дочерних классах, которые будут переопределять
        getMainContentLayout*/
        getLayoutInflater().inflate(getMainContentLayout(), parent);
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    // аннотация предполагает что метод getMainContentLayout будет возвращать ссылку на этот layout.
    @LayoutRes
    protected abstract int getMainContentLayout();

    /*Вызывается при смене фрагмента. Этот метод нужен для того чтобы менять заголовок Toolbar
и видимость кнопки floating action button.*/
    public void fragmentOnScreen(BaseFragment baseFragment) {
        setToolBarTitle(baseFragment.createToolbarTitle(this));
        setupFabVisibility(baseFragment.needFab());
    }

    // метод установки заголовка toolBar'a
    public void setToolBarTitle(String title) {
        // перед установкой заголовка проверка на null.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public void setupFabVisibility(boolean needFab) {
        if (mFab == null) return;

        if (needFab) {
            mFab.show();
        } else {
            mFab.hide();
        }
    }

    // Метод для установки корневого фрагмента
    public void setContent(BaseFragment fragment) {
        myFragmentManager.setFragment(this, fragment, R.id.main_wrapper);
    }

    // Метод для установки дополнительных фрагментов
    public void addContent(BaseFragment fragment) {
        myFragmentManager.addFragment(this, fragment, R.id.main_wrapper);
    }

    // метод для удаления текущего фрагмента
    public boolean removeCurrentFragment() {
        return myFragmentManager.removeCurrentFragment(this);
    }

    // метод для удаления фрагмента
    public boolean removeFragment(BaseFragment fragment) {
        return myFragmentManager.removeFragment(this, fragment);
    }

    @Override
    public void onBackPressed() {
        removeCurrentFragment();
    }
}
