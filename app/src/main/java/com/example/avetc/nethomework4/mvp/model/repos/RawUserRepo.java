package com.example.avetc.nethomework4.mvp.model.repos;

import com.example.avetc.nethomework4.entities.Repository;
import com.example.avetc.nethomework4.entities.User;

import java.util.List;

import io.reactivex.ObservableEmitter;


public class RawUserRepo extends AUserRepo {

    @Override
    protected void readUserFromCache(String username, ObservableEmitter<User> e) {

    }

    @Override
    protected void writeUserToCache(String username, User user) {

    }

    @Override
    protected void readReposFromCache(User user, ObservableEmitter<List<Repository>> e) {

    }

    @Override
    protected void writeReposToCache(User user, List<Repository> repos) {

    }
}
