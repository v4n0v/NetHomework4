package com.example.avetc.nethomework4.mvp.model.repos;

import com.example.avetc.nethomework4.entities.Repository;
import com.example.avetc.nethomework4.entities.User;
import com.example.avetc.nethomework4.mvp.model.api.ApiHolder;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.Observable;
import timber.log.Timber;


public class PaperUserRepo extends AUserRepo {


    @Override
    protected Observable<List<Repository>> getReposOffline(User user) {
        Timber.d("reading repos from cache");
        List<Repository> repos = Paper.book("repos").read("repos", new ArrayList<>());
        return Observable.just(repos);
    }

    @Override
    protected Observable<List<Repository>> getReposOnline(User user) {
        Timber.d("writing repos to cache");
        return ApiHolder.getApi().getRepos(user.getReposUrl()).map(repos -> {
            Paper.book("repos").write("repos", repos);
            return repos;
        });
    }

    @Override
    protected Observable<User> getUserOnline(String username) {
        Timber.d("writing user to cache");
        return ApiHolder.getApi().getUser(username).map(user -> {
            Paper.book("user").write("user", user);
            return user;
        });
    }

    @Override
    protected Observable<User> getUserOffline(String username) {
        Timber.d("reading user from cache");
        User user = Paper.book("user").read("user");
        return Observable.just(user);
    }
}
