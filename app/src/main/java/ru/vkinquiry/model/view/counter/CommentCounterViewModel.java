package ru.vkinquiry.model.view.counter;

import ru.vkinquiry.model.countable.Comments;

public class CommentCounterViewModel extends CounterViewModel {

    private Comments mComments;

    public CommentCounterViewModel(Comments comments) {
        super(comments.getCount());

        this.mComments = comments;
    }

    public Comments getmComments() {
        return mComments;
    }
}
