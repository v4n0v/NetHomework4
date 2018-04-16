package com.example.avetc.nethomework4.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import com.example.avetc.nethomework4.mvp.model.repos.UserRepo;
import com.example.avetc.nethomework4.mvp.view.MainView;
import com.example.avetc.nethomework4.adapter.IListPresenter;
import com.example.avetc.nethomework4.adapter.IListRawView;
import com.example.avetc.nethomework4.entities.Repository;
import com.example.avetc.nethomework4.entities.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import timber.log.Timber;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private Scheduler scheduler;
    private UserRepo userRepo;


    public ListPresenter getListPresenter() {
        return listPresenter;
    }

    public void setListPresenter(ListPresenter listPresenter) {
        this.listPresenter = listPresenter;
    }

    private ListPresenter listPresenter;

    public MainPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
        userRepo = new UserRepo(UserRepo.RepoStrategy.REALM);
        listPresenter = new ListPresenter();
    }

    public void savePaper() {

    }

    public void loadPaper() {

    }

    public void saveRealm() {

    }

    public void loadRealm() {

    }

    public void onPermissionsGranted() {
        loadUserData();
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
                .subscribe(s -> Timber.d(s));
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
                        Timber.d("Current user: " + user.getLogin());
                        Timber.d("URL: " + user.getReposUrl());
                        getViewState().setName(user.getLogin());
                    getViewState().loadAvatar(user.getAvatarUrl());
                        getReposData(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("Error user request: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getReposData(User user) {
        userRepo.getRepos(user)
                .subscribeOn(Schedulers.io())
                .observeOn(scheduler)
                .subscribe(new Observer<List<Repository>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(List<Repository> repositories) {
                        Timber.d( "Repositories count = " + repositories.size());
                        listPresenter.items = repositories;
                        getViewState().updateList();
                    }

                    @Override
                    public void onError(Throwable e) {

                            Timber.e("Error repos request");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
