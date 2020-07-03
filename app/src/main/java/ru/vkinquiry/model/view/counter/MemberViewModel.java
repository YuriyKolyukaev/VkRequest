package ru.vkinquiry.model.view.counter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ru.vkinquiry.R;
import ru.vkinquiry.model.Member;
import ru.vkinquiry.model.view.BaseViewModel;
import ru.vkinquiry.ui.view.holder.BaseViewHolder;

public class MemberViewModel extends BaseViewModel {

    private int id;

    private int groupId;

    private String photo;

    private String mFullName;

    public MemberViewModel(Member member) {
        this.id = member.getId();
        this.groupId = member.getGroup_id();

        this.photo = member.getPhoto();
        this.mFullName = member.getFullName();

    }

    @Override
    public LayoutTypes getType() {
        return LayoutTypes.Member;
    }

    @Override
    protected ru.vkinquiry.ui.view.holder.BaseViewHolder onCreateViewHolder(View view) {
        return new MemberViewHolder(view);
    }

    static class MemberViewHolder extends BaseViewHolder<MemberViewModel> {

        @BindView(R.id.civ_profile_image)
        public CircleImageView civProfilePhoto;

        @BindView(R.id.tv_profile_name)
        public TextView civProfileName;


        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }

        @Override
        public void bindViewHolder(MemberViewModel memberViewModel) {
            Context context = itemView.getContext();

            Glide.with(context)
                    .load(memberViewModel.getPhoto())
                    .into(civProfilePhoto);
            civProfileName.setText(memberViewModel.getmFullName());
        }

        @Override
        public void unbindViewHolder() {
            civProfileName.setText(null);
            civProfilePhoto.setImageBitmap(null);
        }
    }

    public int getId() {
        return id;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getPhoto() {
        return photo;
    }

    public String getmFullName() {
        return mFullName;
    }
}
