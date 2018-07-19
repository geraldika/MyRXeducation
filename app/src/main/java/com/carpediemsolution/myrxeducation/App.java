package com.carpediemsolution.myrxeducation;

import android.app.Application;

import com.carpediemsolution.myrxeducation.api.API;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Юлия on 23.08.2017.
 */

public class App extends Application {

    private static API webApi;

    @Override
    public void onCreate() {
        super.onCreate();
        webApi = getRetfofitClient().create(API.class);
    }

    public static API getWebApi() {
        return webApi;
    }

    private Retrofit getRetfofitClient() {
        return new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                //.baseUrl("http://192.168.1.52:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}
