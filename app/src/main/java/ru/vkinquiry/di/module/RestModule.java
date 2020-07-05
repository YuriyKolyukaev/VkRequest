package ru.vkinquiry.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.vkinquiry.rest.RestClient;
import ru.vkinquiry.rest.api.AccountApi;
import ru.vkinquiry.rest.api.BoardApi;
import ru.vkinquiry.rest.api.GroupsApi;
import ru.vkinquiry.rest.api.UsersApi;
import ru.vkinquiry.rest.api.VideoApi;
import ru.vkinquiry.rest.api.WallApi;

@Module
public class RestModule {                                                                           // ... и инициализирует API сервисы
    private RestClient mRestClient;

    public RestModule() {
        mRestClient = new RestClient();
    }

    @Provides
    @Singleton
    public VideoApi provideVideoApi() {
        return mRestClient.createService(VideoApi.class);
    }

    @Singleton
    @Provides
    public RestClient provideRestClient() {
        return mRestClient;
    }

    @Singleton
    @Provides
    public WallApi provideWallApi() {
        return mRestClient.createService(WallApi.class);
    }


    @Provides
    @Singleton
    public UsersApi provideUsersApi() {
        return mRestClient.createService(UsersApi.class);
    }

    @Provides
    @Singleton
    public GroupsApi provideGroupsApi() {
        return mRestClient.createService(GroupsApi.class);
    }

    @Provides
    @Singleton
    public BoardApi provideBoardApi() {
        return mRestClient.createService(BoardApi.class);
    }

    @Provides
    @Singleton
    public AccountApi provideAccountApi() {
        return mRestClient.createService(AccountApi.class);
    }

}
