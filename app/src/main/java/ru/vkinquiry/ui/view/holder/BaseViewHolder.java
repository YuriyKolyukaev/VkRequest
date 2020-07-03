package ru.vkinquiry.ui.view.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.vkinquiry.model.view.BaseViewModel;

public abstract class BaseViewHolder<Item extends BaseViewModel> extends RecyclerView.ViewHolder {  // BaseViewHolder при такой записи может работать только с BaseViewModel.

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bindViewHolder(Item item);                                                 // Будет использоваться для заполнения макета данными с модели Item.

    public abstract void unbindViewHolder();                                                        // нужен для того чтобы разгружать макет когда он не используется
}
