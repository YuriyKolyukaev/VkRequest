package ru.vkinquiry.rest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {                                                                           // используется для инициализации ретрофита и сервисов API запросов

    private static final String VK_BASE_URL = "https://api.vk.com/method/";                         // константа для хранения URL запросов API

    private Retrofit mRetrofit;                                                                     // переменная класса ретрофит
    private OkHttpClient client;

    public RestClient() {                                                                           // инициализация переменной Retrofit с помощью конструктора с использованием Bilder'а

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        mRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())                          // добавили в ретрофит адаптер для RX Java
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(VK_BASE_URL)
                .build();
    }

    public <S> S createService(Class<S> serviceClass) {                                             // переменная для инициализации Rest API сервисов
    return mRetrofit.create(serviceClass);
    }
}
