package ru.vkinquiry.mvp.view;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import ru.vkinquiry.model.view.BaseViewModel;

/*отвечает за отображение списка стены, коментариев обсуждения и т.д.
 Методы этого интерфейса будут реализованы в классе BaseFeedFragment, а вызывать
 эти методы будеткласс BaseFeedPresenter*/
public interface BaseFeedView extends MvpView {

    // показ анимации загрузки, когда мы обновляем список
    void showRefreshing();

    //скрытие анимации загрузки
    void hideRefreshing();

   /* показ анимации загрузки в центре когда список появляется на экране (тоесть запуск приложения
    с последующим отображением стены). Также он будет появляться при открытии коментариев
    и переходя по вкладкам navigation drawer*/
    void showListProgress();

    // скрытие анимации загрузки...
    void hideListProgress();

    //  отображение ошибки
    void showError (String message);

    // замена существующего списка новым
    void setItems(List<BaseViewModel> items);

    // добавление новых элементов списка в конеу существующего
    void addItems (List<BaseViewModel> items);




}
