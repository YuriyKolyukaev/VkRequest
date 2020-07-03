package ru.vkinquiry.model.view;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vkinquiry.R;
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

    static class InfoLinkViewHolder extends BaseViewHolder<InfoLinksViewModel> {

        @BindView(R.id.rv_links)
        RelativeLayout rvLinks;

        public InfoLinkViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindViewHolder(InfoLinksViewModel infoLinksViewModel) {

        }

        @Override
        public void unbindViewHolder() {

        }
    }
}
