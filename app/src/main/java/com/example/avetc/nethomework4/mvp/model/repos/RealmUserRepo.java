package com.example.avetc.nethomework4.mvp.model.repos;

import com.example.avetc.nethomework4.entities.Repository;
import com.example.avetc.nethomework4.entities.User;
import com.example.avetc.nethomework4.entities.realm.RealmRepository;
import com.example.avetc.nethomework4.entities.realm.RealmUser;
import com.example.avetc.nethomework4.mvp.model.api.ApiHolder;
import com.example.avetc.nethomework4.mvp.model.api.ApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.realm.Realm;
import timber.log.Timber;


public class RealmUserRepo extends AUserRepo {

    public RealmUserRepo(ApiService apiService) {
        super(apiService);
    }

    @Override
    protected void readUserFromCache(String username, ObservableEmitter<User> e) {
        Realm realm = Realm.getDefaultInstance();
        RealmUser realmUser = realm.where(RealmUser.class).equalTo("login", username).findFirst();
        if (realmUser == null) {
            e.onError(new RuntimeException("No user in realm"));
        } else {
            e.onNext(new User(realmUser.getLogin(), realmUser.getAvatarUrl(), realmUser.getReposUrl()));
        }
        e.onComplete();
        Timber.d("user loaded from cache");
    }

    @Override
    protected void writeUserToCache(String username, User user) {
        Realm realm = Realm.getDefaultInstance();
        RealmUser realmUser = realm.where(RealmUser.class).equalTo("login", username).findFirst();
        if (realmUser == null) {
            realm.executeTransaction(realm1 -> {
                RealmUser newRealmUser = realm.createObject(RealmUser.class, user.getLogin());
                newRealmUser.setAvatarUrl(user.getAvatarUrl());
                newRealmUser.setReposUrl(user.getReposUrl());
            });

        } else {
            realm.executeTransaction(realm1 ->
                    realmUser.setAvatarUrl(user.getAvatarUrl())
            );
        }
        realm.close();
        Timber.d("user was written to cache");
    }

    @Override
    protected void readReposFromCache(User user, ObservableEmitter<List<Repository>> e) {
        Realm realm = Realm.getDefaultInstance();
        RealmUser realmUser = realm.where(RealmUser.class).equalTo("login", user.getLogin()).findFirst();
        if (realmUser == null) {
            e.onError(new RuntimeException("No user in realm"));
        } else {
            List<Repository> repos = new ArrayList<>();
            Timber.d("size = " + repos.size());
            for (RealmRepository repo : realmUser.getRepos()) {
                repos.add(new Repository(repo.getId(), repo.getName(), repo.getHtmlUrl()));
            }
            e.onNext(repos);
            Timber.d("repos loaded from cache");
        }
        e.onComplete();
    }

    @Override
    protected void writeReposToCache(User user, List<Repository> repos) {
        Realm realm = Realm.getDefaultInstance();

        // берем пользователя из базы
        RealmUser realmUser = realm.where(RealmUser.class).equalTo("login", user.getLogin()).findFirst();
        // если его нет, создаем нового исходя из входящих данных
        if (realmUser == null) {
            realm.executeTransaction(innerRealm -> {
                RealmUser newRealmUser = realm.createObject(RealmUser.class, user.getLogin());
                newRealmUser.setAvatarUrl(user.getAvatarUrl());
                newRealmUser.setReposUrl(user.getReposUrl());
            });
        }
        // берем пользователя, он уже не может быть пустым
        realmUser = realm.where(RealmUser.class).equalTo("login", user.getLogin()).findFirst();
        // создаем временного пользователя и очищаем список репозиториев
        RealmUser finalRealmUser = realmUser;
        realm.executeTransaction(innerRealm -> {
            finalRealmUser.getRepos().deleteAllFromRealm();
            // заполняем полученным от сервера списком
            for (Repository repo : repos) {
                RealmRepository realmRepository = realm.createObject(RealmRepository.class, repo.getId());
                realmRepository.setName(repo.getName());
                realmRepository.setHtmlUrl(repo.getHtmlUrl());
                finalRealmUser.getRepos().add(realmRepository);
            }
        });
        Timber.d("repos was written to cache");


        realm.close();
    }


}
