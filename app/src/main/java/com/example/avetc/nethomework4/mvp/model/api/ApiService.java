package com.example.avetc.nethomework4.mvp.model.api;

import com.example.avetc.nethomework4.entities.Repository;
import com.example.avetc.nethomework4.entities.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiService {
    @GET("/users/{user}")
    Observable<User> getUser(@Path("user") String username);

    @GET
    Observable<List<Repository>> getRepos(@Url String url);
}
