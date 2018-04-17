package com.example.avetc.nethomework4.mvp.model.repos;

import com.example.avetc.nethomework4.entities.Repository;
import com.example.avetc.nethomework4.entities.User;
import com.example.avetc.nethomework4.mvp.model.api.ApiService;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.ObservableEmitter;
import timber.log.Timber;


public class PaperUserRepo extends AUserRepo {


    public PaperUserRepo(ApiService apiService) {
        super(apiService);
    }

    @Override
    protected void readUserFromCache(String username, ObservableEmitter<User> e) {
        User user = Paper.book("user").read("user");
        if (user!=null){
            Timber.d("paper read user "+user.getLogin());
            e.onNext(user);
        }
        e.onComplete();
    }


    @Override
    protected void writeUserToCache(String username, User user) {
        Paper.book("user").write("user", user);
        Timber.d("paper has wrote user "+user.getLogin());
    }

    @Override
    protected void readReposFromCache(User user, ObservableEmitter<List<Repository>> e) {
        List<Repository> repos = Paper.book("repos").read("repos", new ArrayList<>());
        if (repos!=null) {
            Timber.d("paper read "+repos.size()+" repos");
            e.onNext(repos);
        }
        e.onComplete();
    }

    @Override
    protected void writeReposToCache(User user, List<Repository> repos) {
        Paper.book("repos").write("repos", repos);
        Timber.d("paper has wrote "+repos.size()+" repos");
    }
}
