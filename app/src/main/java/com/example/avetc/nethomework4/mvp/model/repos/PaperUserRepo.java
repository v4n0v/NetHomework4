package com.example.avetc.nethomework4.mvp.model.repos;

import com.example.avetc.nethomework4.entities.Repository;
import com.example.avetc.nethomework4.entities.User;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.ObservableEmitter;


public class PaperUserRepo extends AUserRepo {



    @Override
    protected void readUserFromCache(String username, ObservableEmitter<User> e) {
        User user = Paper.book("user").read("user");
        if (user!=null){
            e.onNext(user);
        }
        e.onComplete();
    }


    @Override
    protected void writeUserToCache(String username, User user) {
        Paper.book("user").write("user", user);
    }

    @Override
    protected void readReposFromCache(User user, ObservableEmitter<List<Repository>> e) {
        List<Repository> repos = Paper.book("repos").read("repos", new ArrayList<>());
        if (repos!=null) {
            e.onNext(repos);
        }
        e.onComplete();
    }

    @Override
    protected void writeReposToCache(User user, List<Repository> repos) {
        Paper.book("repos").write("repos", repos);
    }
}
