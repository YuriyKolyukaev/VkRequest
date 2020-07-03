package ru.vkinquiry.common;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.vkinquiry.model.view.BaseViewModel;
import ru.vkinquiry.ui.view.holder.BaseViewHolder;

public class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder<BaseViewModel>> {

    private List<BaseViewModel> list = new ArrayList<>();                                           // Содержит все добавленные в адаптер элементы. Отсюда берутся данные для заполнения макета в методе onBindViewHolder

    private ArrayMap<Integer, BaseViewModel> mTypeInstances = new ArrayMap<>();                     // Нужен для того чтобы создавать ViewHolder конкретного типа в методе onCreateViewHolder.

    @NonNull
    @Override
    public BaseViewHolder<BaseViewModel> onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        Log.i("TEST_LOG", "onCreateViewHolder parent: " + parent.toString() + " viewType: " + viewType);
        return mTypeInstances.get(viewType).createViewHolder(parent);                               // Возвращаем полученный по типу модели метод createViewHolder с параметром parent.
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<BaseViewModel> holder, int position) {
        Log.i("TEST_LOG", "onBindViewHolder holder: " + holder.toString() + " ||| position: " + position);
        holder.bindViewHolder(getItem(position));
    }

    @Override
    public void onViewRecycled(@NonNull BaseViewHolder<BaseViewModel> holder) {
        super.onViewRecycled(holder);
        Log.i("TEST_LOG", "onViewRecycled holder: " + holder.toString());
        holder.unbindViewHolder();
    }

    @Override
    public int getItemCount() {
        Log.i("TEST_LOG", "getItemCount list.size: " + list.size());
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {                                                      // Будет возвращать тип View Item'а.
        Log.i("TEST_LOG", "getItemViewType position: " + position);
        Log.i("TEST_LOG", "getItemViewType getItem: " + getItem(position).getType().getValue());
        return getItem(position).getType().getValue();
    }

    public BaseViewModel getItem(int position) {
        Log.i("TEST_LOG", "getItem list.get(position): " + list.get(position));
        return list.get(position);
    }

    public void registerTypeInstance(BaseViewModel item) {                                          // При каждом добавлении нужно регистрировать тип Layout'а для этого создам метод registerTypeInstance
        Log.i("TEST_LOG", "registerTypeInstance item: " + item.toString());
        if (!mTypeInstances.containsKey(item.getType().getValue())) {                               // если список типов не содержит такой же тип то добавляем тип.
            Log.i("TEST_LOG", "mTypeInstances.containsKey = false: put done");
            mTypeInstances.put(item.getType().getValue(), item);
        }
    }

    public void addItems(List<? extends BaseViewModel> newsItems) {                                 // метод добавления элементов в список
        Log.i("TEST_LOG", "addItems newsItems: " + newsItems.toString());
        for (BaseViewModel newItems : newsItems) {
            registerTypeInstance(newItems);                                                         // Регистрируем тип
        }

        list.addAll(newsItems);                                                                     // добавляем в массив
        Log.i("TEST_LOG", "addItems list.addAll: done" + newsItems.toString());

        notifyDataSetChanged();
        Log.i("TEST_LOG", "addItems notifyDataSetChanged: done");
                                                                                                    // сообщаем адаптеру о том что список изменился, и сразу после этого адаптер проверяет корличество элементов и если оно
    }                                                                                               // больше нуля адаптер начитнает создавать столько ViewHolder'ов, сколько поместится на экране. После этого вызывается
                                                                                                    // метод onCreateViewHolder с параметром int vieType взятым из getItemViewType
    public void setItems(List<BaseViewModel> items) {                                               // метод для замены элементов в списке. Чистим список и добавляем новый элемент.
        Log.i("TEST_LOG", "setItems " + items);
        clearList();
        addItems(items);
    }

    public void clearList() {
        list.clear();
    }


    /* Будет перебирать все элементы списка, проверять - является ли элмент реальным и возвращает
    реальное количество элементов*/
    public int getRealItemCount() {
        int count = 0;
        for (int i = 0; i < getItemCount(); i++) {
            if (!getItem(i).isItemDecorator()) {
                count += 1;

            }
        }
        return count;
    }
}
