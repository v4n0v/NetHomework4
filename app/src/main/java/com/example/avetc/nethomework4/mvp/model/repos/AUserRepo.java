package com.example.avetc.nethomework4.mvp.model.repos;

import com.example.avetc.nethomework4.common.NetworkStatus;
import com.example.avetc.nethomework4.entities.Repository;
import com.example.avetc.nethomework4.entities.User;
import com.example.avetc.nethomework4.mvp.model.api.ApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

public abstract class AUserRepo implements IUserRepo {


    public AUserRepo(ApiService apiService) {
        this.apiService = apiService;
    }

    @Inject
    ApiService apiService;


    @Override
    public Observable<User> getUser(String username) {

        if (!NetworkStatus.isOffline()) {
            return apiService.getUser(username).map(user -> {
                writeUserToCache(username, user);
                return user;
            });
        } else {
            return Observable.create(e -> readUserFromCache(username, e));
        }

    }

    protected abstract void readUserFromCache(String username, ObservableEmitter<User> e);

    protected abstract void writeUserToCache(String username, User user);


    @Override
    public Observable<List<Repository>> getRepos(User user) {
        if (!NetworkStatus.isOffline()) {
            return apiService.getRepos(user.getReposUrl()).map(repos -> {
                writeReposToCache(user, repos);
                return repos;
            });
        } else {
            return Observable.create(e -> readReposFromCache(user, e));
        }
    }

    protected abstract void readReposFromCache(User user, ObservableEmitter<List<Repository>> e);

    protected abstract void writeReposToCache(User user, List<Repository> repos);

//    protected abstract Observable<List<Repository>> getReposOffline(User user);
//
//    protected abstract Observable<List<Repository>> getReposOnline(User user);
//
//    protected abstract Observable<User> getUserOnline(String username);
//
//    protected abstract Observable<User> getUserOffline(String username);
}
