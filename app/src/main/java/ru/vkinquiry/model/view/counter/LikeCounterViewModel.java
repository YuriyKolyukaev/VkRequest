package ru.vkinquiry.model.view.counter;

import ru.vkinquiry.model.countable.Likes;

public class LikeCounterViewModel extends CounterViewModel {                                        // view счетчик лайков

    private Likes mLikes;

    public LikeCounterViewModel(Likes likes) {                                                      // класс должен вызывать конструктор супер класса для получения счетчика
        super(likes.getCount());

        this.mLikes = likes;

        if (mLikes.getUserLikes() == 1) {
            setAccentColor();
        }
    }

    public Likes getLikes() {
        return mLikes;
    }

}
