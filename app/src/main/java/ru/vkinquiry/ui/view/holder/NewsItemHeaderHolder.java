package ru.vkinquiry.ui.view.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ru.vkinquiry.R;
import ru.vkinquiry.model.view.NewsItemHeaderViewModel;

public class NewsItemHeaderHolder extends BaseViewHolder<NewsItemHeaderViewModel> {                 // будет создавать и управлять полями Header'а

    @BindView(R.id.civ_profile_image)
    public CircleImageView civProfileImage;                                                        // переменная для аватара имени пользователя
    @BindView(R.id.tv_profile_name)
    public TextView tvName;                                                                        // переменная для аватара имени пользователя
    @BindView(R.id.iv_reposted_icon)
    public ImageView ivRepostedIcon;                                                               // переменная для аватара имени отправителя репоста
    @BindView(R.id.tv_repost_profile_name)
    public TextView tvRepostedProfileName;

    public NewsItemHeaderHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this,itemView);
    }

    @Override
    public void bindViewHolder(NewsItemHeaderViewModel item) {
        Context context = itemView.getContext();

        Glide.with(context)                                                                         // с помощью библиотеки Glide подгружаем и устанавливаем аватар отправителя
                .load(item.getProfilePhoto())
                .into(civProfileImage);
        tvName.setText(item.getProfileName());

        if  (item.isRepost()) {                                                                     // проверка - если это репост, делаем видимой иконку репоста и добаляем имя отправителя репоста
            ivRepostedIcon.setVisibility(View.VISIBLE);                                             // иначе скрываем иконку и очищапем текст
            tvRepostedProfileName.setText(item.getRepostProfileName());
        } else {
            ivRepostedIcon.setVisibility(View.GONE);
            tvRepostedProfileName.setText(null);
        }
    }

    @Override
    public void unbindViewHolder() {                                                                // очищаем аватар и текстовые поля
        civProfileImage.setImageBitmap(null);
        tvName.setText(null);
        tvRepostedProfileName.setText(null);
    }

}
