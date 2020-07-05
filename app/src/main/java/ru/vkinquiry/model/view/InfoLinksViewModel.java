package ru.vkinquiry.model.view;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vkinquiry.MyApplication;
import ru.vkinquiry.R;
import ru.vkinquiry.common.manager.MyFragmentManager;
import ru.vkinquiry.ui.activity.BaseActivity;
import ru.vkinquiry.ui.fragment.InfoLinksFragment;
import ru.vkinquiry.ui.view.holder.BaseViewHolder;

public class InfoLinksViewModel extends BaseViewModel {
    @Override
    public LayoutTypes getType() {
        return LayoutTypes.InfoLinks;
    }

    @Override
    public ru.vkinquiry.ui.view.holder.BaseViewHolder onCreateViewHolder(View view) {
        return new InfoLinkViewHolder(view);
    }

    public static class InfoLinkViewHolder extends BaseViewHolder<InfoLinksViewModel> {

        @BindView(R.id.rv_links)
        RelativeLayout rvLinks;

        @Inject
        MyFragmentManager mFragmentManager;

        public InfoLinkViewHolder(@NonNull View itemView) {
            super(itemView);
            MyApplication.getsApplicationComponent().inject(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindViewHolder(InfoLinksViewModel infoLinksViewModel) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("CLICK_LINK", "click to InfoLinksViewModel");
                    mFragmentManager.addFragment((BaseActivity) view.getContext(), new InfoLinksFragment(),
                            R.id.main_wrapper);
                }
            });
        }

        @Override
        public void unbindViewHolder() {

        }
    }
}
