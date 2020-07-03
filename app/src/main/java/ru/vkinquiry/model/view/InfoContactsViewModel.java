package ru.vkinquiry.model.view;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vkinquiry.R;
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

    static class InfoContactsViewHolder extends BaseViewHolder<InfoContactsViewModel> {

        @BindView(R.id.rv_contacts)
        RelativeLayout rvContacts;

        public InfoContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindViewHolder(InfoContactsViewModel infoContactsViewModel) {

        }

        @Override
        public void unbindViewHolder() {

        }
    }
}
