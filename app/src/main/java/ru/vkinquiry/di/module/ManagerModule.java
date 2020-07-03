package ru.vkinquiry.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.vkinquiry.common.manager.MyFragmentManager;
import ru.vkinquiry.common.manager.NetworkManager;

@Module
public class ManagerModule {                                                                        // используется для предоставления менеджеров

    @Singleton
    @Provides
    MyFragmentManager provideMyFragmentManager()    {
            return new MyFragmentManager();
    }

    @Singleton
    @Provides
    NetworkManager provideNetworkManager() {
        return new NetworkManager();
    }
}
