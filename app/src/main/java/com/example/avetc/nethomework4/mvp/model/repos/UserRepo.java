package com.example.avetc.nethomework4.mvp.model.repos;

import com.example.avetc.nethomework4.entities.Repository;
import com.example.avetc.nethomework4.entities.User;
import com.example.avetc.nethomework4.mvp.model.api.ApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


public class UserRepo implements IUserRepo {
    public enum RepoStrategy {RAW, REALM, PAPER}

    @Inject
    ApiService apiService;

    public UserRepo(RepoStrategy strategy, ApiService apiService) {
        this.apiService=apiService;
        initStrategy(strategy);


    }

    private AUserRepo strategy;

    public void setStrategy(RepoStrategy strategy) {
        initStrategy(strategy);
    }

    @Override
    public Observable<User> getUser(String username) {
        return strategy.getUser(username);
    }

    @Override
    public Observable<List<Repository>> getRepos(User user) {
        return strategy.getRepos(user);
    }

    private void initStrategy(RepoStrategy strategy) {
        switch (strategy) {
            case PAPER:
                this.strategy = new PaperUserRepo(apiService);
                break;
            case REALM:
                this.strategy = new RealmUserRepo(apiService);
                break;
            case RAW:
                this.strategy = new RawUserRepo(apiService);
                break;
            default:
                this.strategy = new RawUserRepo(apiService);
                break;
        }
    }

}
