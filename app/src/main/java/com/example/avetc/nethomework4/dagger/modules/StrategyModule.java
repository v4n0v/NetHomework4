package com.example.avetc.nethomework4.dagger.modules;

import com.example.avetc.nethomework4.mvp.model.repos.UserRepo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;


@Module
public class StrategyModule {
    @Provides
    @Named("realm")
    public UserRepo.RepoStrategy realmStrategy(){
        return    UserRepo.RepoStrategy.REALM;
    }

    @Provides
    @Named("paper")
    public UserRepo.RepoStrategy paperStrategy(){
        return    UserRepo.RepoStrategy.PAPER;
    }
}
