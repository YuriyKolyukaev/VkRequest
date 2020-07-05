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
import ru.vkinquiry.ui.fragment.InfoContactsFragment;
import ru.vkinquiry.ui.view.holder.BaseViewHolder;

public class InfoContactsViewModel extends BaseViewModel{
    @Override
    public LayoutTypes getType() {
        return LayoutTypes.InfoContacts;
    }

    @Override
    public ru.vkinquiry.ui.view.holder.BaseViewHolder onCreateViewHolder(View view) {
        return new InfoContactsViewHolder(view);
    }

    public static class InfoContactsViewHolder extends BaseViewHolder<InfoContactsViewModel> {

        @BindView(R.id.rv_contacts)
        RelativeLayout rvContacts;

        @Inject
        MyFragmentManager mFragmentManager;

        public InfoContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            MyApplication.getsApplicationComponent().inject(this);
        }

        @Override
        public void bindViewHolder(InfoContactsViewModel infoContactsViewModel) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("CLICK_LINK", "click to InfoLinksViewModel");
                    mFragmentManager.addFragment((BaseActivity) view.getContext(), new InfoContactsFragment(),
                            R.id.main_wrapper);
                }
            });
        }

        @Override
        public void unbindViewHolder() {

        }
    }
}
