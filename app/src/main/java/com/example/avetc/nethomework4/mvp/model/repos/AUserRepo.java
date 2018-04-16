package com.example.avetc.nethomework4.mvp.model.repos;

import com.example.avetc.nethomework4.common.NetworkStatus;
import com.example.avetc.nethomework4.entities.Repository;
import com.example.avetc.nethomework4.entities.User;

import java.util.List;

import io.reactivex.Observable;

public abstract class AUserRepo implements IUserRepo {


    @Override
    public Observable<User> getUser(String username) {

        if (!NetworkStatus.isOffline()) {
            return getUserOnline(username);
        }
        else {
            return getUserOffline(username);
        }

    }


    @Override
    public Observable<List<Repository>> getRepos(User user) {
        if (!NetworkStatus.isOffline()) {
           return getReposOnline(user);
        } else {
           return getReposOffline(user);
        }
    }

    protected abstract Observable<List<Repository>> getReposOffline(User user);

    protected abstract Observable<List<Repository>> getReposOnline(User user);

    protected abstract Observable<User> getUserOnline(String username);

    protected abstract Observable<User> getUserOffline(String username);
}
