package ru.vkinquiry.common.manager;


import androidx.annotation.IdRes;
import androidx.fragment.app.FragmentTransaction;

import java.util.Stack;

import ru.vkinquiry.R;
import ru.vkinquiry.ui.activity.BaseActivity;
import ru.vkinquiry.ui.fragment.BaseFragment;
import ru.vkinquiry.ui.fragment.NewsFeedFragment;

public class MyFragmentManager {                                                                    // Мой кастомный менеджер фрагментов.

    private static final int EMPTY_FRAGMENT_STACK_SIZE = 1;                                         // на экране всегда должен быть один фрагмент. Эта кантсанта служит чтобы его не удалить.
                                                                                                    // тоесть в стеке всегда должен оставаться как минимум один фрагмент.
    private Stack<BaseFragment> mFragmentStack = new Stack<>();                                     // переменная для стека

    private BaseFragment mCurrentFragment;                                                          // переменная для текущего фрагмента


    public void setFragment(BaseActivity activity, BaseFragment fragment, @IdRes int containerId) { // этот метод устанавливает корневой фрагмент
        if (activity != null && !isAlreadyContains(fragment)) {                                     // это условие проверяет - не существует ли данный фрагмент уже в стеке?
            FragmentTransaction transaction = createAddTransaction(activity, fragment, false);
            transaction.replace(containerId, fragment);
            commitAddTransaction(activity, fragment, transaction, false);
        }
    }

    public void addFragment(BaseActivity activity, BaseFragment fragment, @IdRes int containerId) { // этот метод будет добавлять фрагменты поверх корневого
        if (activity != null && !isAlreadyContains(fragment)) {
            FragmentTransaction transaction = createAddTransaction(activity, fragment, true);
            transaction.add(containerId, fragment);
            commitAddTransaction(activity, fragment, transaction, true);
        }
    }

    private FragmentTransaction createAddTransaction(BaseActivity activity, BaseFragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {                                                                       // этот метод будет создавать fragmentTransaction и в
            fragmentTransaction.addToBackStack(fragment.getTag());                                  // зависимости от значения параметра addToBackStack будет добавлять или
        }                                                                                           // не добавлять фрагмент в BackStack.

        return fragmentTransaction;
    }

    private void commitAddTransaction(BaseActivity activity, BaseFragment fragment,                 // этот метод будет совершать транзакцию добавления фрагментов
                                      FragmentTransaction transaction, boolean addToBackStack) {
        if (transaction != null) {
            mCurrentFragment = fragment;

            if (!addToBackStack) {                                                                  // если фрагменты не добавляются в BackStack, очищаем фрагмент BackStack.
                mFragmentStack = new Stack<>();
            }

            mFragmentStack.add(fragment);

            commitTransaction(activity, transaction);
        }
    }

    public boolean removeCurrentFragment(BaseActivity activity) {                                   // метод удаления текущего фрагмента
        return removeFragment(activity, mCurrentFragment);
    }

    public boolean removeFragment(BaseActivity activity, BaseFragment fragment) {                   // метод для удаления фрагмента
        boolean canRemove = fragment !=null && mFragmentStack.size() > EMPTY_FRAGMENT_STACK_SIZE;   // значение переменной canRemove будет зависеть от условий, что fragment не равен 0 и размер стека больше чем минимальный,
                                                                                                    // чтобы случайно не удалить корневой фрагмент
        if (canRemove) {
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

            mFragmentStack.pop();                                                                   // методом pop удаляем последний фрагмент из списка.
            mCurrentFragment = mFragmentStack.lastElement();                                        // присваиваем последний после удаления элемент переменной mCurrentFragment.

            transaction.remove(fragment);
            commitTransaction(activity, transaction);
        }

        return canRemove;
    }

    private void commitTransaction(BaseActivity activity, FragmentTransaction transaction) {        // этот метод совершает любые транзакци вне зависимости от того добавляются фрагменты или удаляются.
        transaction.commit();

        activity.fragmentOnScreen(mCurrentFragment);
    }

    public boolean isAlreadyContains(BaseFragment baseFragment) {                                   // метод проверки - существует ли уже в стеке текущий фрагмент.
        if (baseFragment == null) {
            return false;
        }
        return mCurrentFragment != null && mCurrentFragment.getClass().getName().equals(baseFragment.getClass().getName());
    }
}

