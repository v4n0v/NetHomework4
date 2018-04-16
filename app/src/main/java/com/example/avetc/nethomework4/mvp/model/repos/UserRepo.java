package com.example.avetc.nethomework4.mvp.model.repos;

import com.example.avetc.nethomework4.entities.Repository;
import com.example.avetc.nethomework4.entities.User;
import java.util.List;
import io.reactivex.Observable;


public class UserRepo implements IUserRepo {
    public enum RepoStrategy {RAW, REALM, PAPER}

    public UserRepo(RepoStrategy strategy) {
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
                this.strategy = new PaperUserRepo();
                break;
            case REALM:
                this.strategy = new RealmUserRepo();
                break;
            default:
                this.strategy = new RawUserRepo();
                break;
        }
    }

}
