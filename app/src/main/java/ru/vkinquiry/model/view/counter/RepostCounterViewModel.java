package ru.vkinquiry.model.view.counter;

import ru.vkinquiry.model.countable.Reposts;

public class RepostCounterViewModel extends CounterViewModel{

    private Reposts mReposts;

    public RepostCounterViewModel(Reposts reposts) {
        super(reposts.getCount());

        this.mReposts = reposts;

        if (mReposts.getUserReposted() == 0) {
            setAccentColor();
        }
    }

    public Reposts getReposts() {
        return mReposts;
    }
}
