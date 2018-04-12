package com.example.avetc.nethomework4.model;

import com.example.avetc.nethomework4.entities.Repository;
import com.example.avetc.nethomework4.entities.User;
import com.example.avetc.nethomework4.model.api.ApiHolder;

import java.util.List;

import io.reactivex.Observable;


public class UserRepo {

    public Observable<User> getUser(String username) {
        return ApiHolder.getApi().getUser(username);
    }

    public Observable<List<Repository>> getRepos(String login) {
        return ApiHolder.getApi().getRepos(login);
    }
}
