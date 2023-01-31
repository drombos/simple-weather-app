package org.example.http.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceFactory {
    private final static GsonConverterFactory DESERIALIZER = GsonConverterFactory.create();
    protected final String baseUrl;
    protected final Retrofit retrofit;

    public RetrofitServiceFactory(String baseUrl) {
        this.baseUrl = baseUrl;

        this.retrofit = initRetrofit();
    }

    public <T> T createService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }

    private Retrofit initRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(DESERIALIZER)
                .build();
    }
}