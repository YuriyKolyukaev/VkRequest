package ru.vkinquiry.di.module;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {                                                                    // отвечает за предоставление контекста

    private Application mApplication;

    public ApplicationModule (Application application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return mApplication;
    }

    @Singleton
    @Provides
    Typeface provideGoogleTypeface(Context context){                                                // метод для внедрения шрифта
        return Typeface.createFromAsset(context.getAssets(), "MaterialIcons-Regular.ttf");     // теперь текст для счетчиков будет преобразован в иконки
    }

}
