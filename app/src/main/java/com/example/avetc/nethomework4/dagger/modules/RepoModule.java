package com.example.avetc.nethomework4.dagger.modules;

import com.example.avetc.nethomework4.mvp.model.api.ApiService;
import com.example.avetc.nethomework4.mvp.model.repos.UserRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;


@Module(includes = {ApiModule.class, StrategyModule.class})
public class RepoModule {

    @Provides
    public UserRepo userRepo(@Named("paper") UserRepo.RepoStrategy strategy, ApiService service){
        return new UserRepo(strategy, service);
    }



}
