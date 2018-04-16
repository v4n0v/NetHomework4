package com.example.avetc.nethomework4.mvp.model.repos;

import com.example.avetc.nethomework4.entities.Repository;
import com.example.avetc.nethomework4.entities.User;
import com.example.avetc.nethomework4.mvp.model.api.ApiHolder;

import java.util.List;

import io.reactivex.Observable;


public class RawUserRepo extends AUserRepo {

    @Override
    protected Observable<List<Repository>> getReposOffline(User user) {
        return null;
    }

    @Override
    protected Observable<List<Repository>> getReposOnline(User user) {
        return ApiHolder.getApi().getRepos(user.getReposUrl());
    }

    @Override
    protected Observable<User> getUserOnline(String username) {
        return ApiHolder.getApi().getUser(username);
    }

    @Override
    protected Observable<User> getUserOffline(String username) {
        return null;
    }
}
