package com.example.avetc.nethomework4.dagger.modules;

import com.example.avetc.nethomework4.mvp.model.api.ApiService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Singleton
@Module
public class ApiModule {


    @Provides
    public ApiService api(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
    @Provides
    public Retrofit retrofit(@Named("gson") GsonConverterFactory gsonConverterFactory){
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .build();

    }

    @Provides @Named("gson")
    public GsonConverterFactory gsonConverterFactory(Gson gson){
        return  GsonConverterFactory.create(gson);
    }


    @Provides
    public Gson gson(){
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

}
