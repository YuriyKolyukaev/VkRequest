package ru.vkinquiry.ui.view.holder.attachment;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vkinquiry.MyApplication;
import ru.vkinquiry.R;
import ru.vkinquiry.common.manager.MyFragmentManager;
import ru.vkinquiry.model.view.attachment.ImageAttachmentViewModel;
import ru.vkinquiry.ui.view.holder.BaseViewHolder;

public class ImageAttachmentHolder extends BaseViewHolder<ImageAttachmentViewModel> {

    @BindView(R.id.iv_attachment_image)
    public ImageView image;

    @Inject
    MyFragmentManager mFragmentManager;

    public ImageAttachmentHolder(@NonNull View itemView) {
        super(itemView);

        MyApplication.getsApplicationComponent().inject(this);

        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindViewHolder(ImageAttachmentViewModel imageAttachmentViewModel) {

        Glide.with(itemView.getContext()).load(imageAttachmentViewModel.getPhotoUrl()).into(image);
    }

    @Override
    public void unbindViewHolder() {

        itemView.setOnClickListener(null);
        image.setImageBitmap(null);
    }
}
