package com.example.avetc.nethomework4.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;


import com.example.avetc.nethomework4.MainView;
import com.example.avetc.nethomework4.adapter.IListPresenter;
import com.example.avetc.nethomework4.adapter.IListRawView;
import com.example.avetc.nethomework4.entities.Repository;
import com.example.avetc.nethomework4.entities.User;
import com.example.avetc.nethomework4.model.UserRepo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * Created by avetc on 11.04.2018.
 */

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    String TAG = "MainPresenter";

    Scheduler scheduler;
    UserRepo userRepo;

    public ListPresenter getListPresenter() {
        return listPresenter;
    }

    public void setListPresenter(ListPresenter listPresenter) {
        this.listPresenter = listPresenter;
    }

    private ListPresenter listPresenter;

    public MainPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
        userRepo = new UserRepo();
        listPresenter = new ListPresenter();
    }

    class ListPresenter implements IListPresenter {
        List<Repository> items = new ArrayList<>();


        @Override
        public void bindView(IListRawView view) {
            view.setText(items.get(view.getPos()));
        }

        @Override
        public int getViewCount() {
            return items.size();
        }

        @Override
        public void selectItem(int pos) {

        }
    }


    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
        loadUserData();
//        getUserFromOk();
    }

    public void getUserFromOk() {
        Single<String> single = Single.fromCallable(() -> {
            OkHttpClient client = new OkHttpClient();
            Request req = new Request.Builder()
                    .url("https://api.github.com/users/v4n0v")
                    .build();
            return client.newCall(req).execute().body().string();
        });

        single.subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(s -> {
                    Log.d(TAG, s);

                });
    }


    private void loadUserData() {
        userRepo.getUser("v4n0v")
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                        Log.d(TAG, user.getLogin());
                        Log.d(TAG, user.getReposUrl());
                        getViewState().setName(user.getLogin());
                        getViewState().loadAvatar(user.getAvatarUrl());

                        getReposData(user.getReposUrl());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error request");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void getReposData(String login){
        userRepo.getRepos(login)
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(new Observer<List<Repository>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Repository> repositories) {
                        Log.d(TAG, "Repository size = "+repositories.size());
                        for (Repository repo:repositories) {
                            Log.d(TAG, "Repository name = "+repo.getName());
                        }
                        listPresenter.items = repositories;
                        getViewState().updateList();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}
