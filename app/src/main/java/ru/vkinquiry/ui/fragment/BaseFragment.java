package ru.vkinquiry.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.arellomobile.mvp.MvpAppCompatFragment;

import ru.vkinquiry.ui.activity.BaseActivity;

public abstract class BaseFragment extends MvpAppCompatFragment {

    @LayoutRes
    protected abstract int getMainContentLayout();                                                  // аннотация предполагает что метод getMainContentLayout будет возвращать ссылку на этот layout.

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getMainContentLayout(), container, false);
    }

    public String createToolbarTitle(Context context) {                                             // метод для отображения заголовка toolBar
        return context.getString(onCreateToolbarTitle());
    }

    @StringRes
    public abstract int onCreateToolbarTitle();                                                     // метод который зарпашивает заголовок toolBara у дочерних фрагментов. Он должен возвращать StringRes

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}
