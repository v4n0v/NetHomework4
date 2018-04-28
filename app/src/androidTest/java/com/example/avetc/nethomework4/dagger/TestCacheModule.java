package com.example.avetc.nethomework4.dagger;

import com.example.avetc.nethomework4.dagger.modules.StrategyModule;
import com.example.avetc.nethomework4.mvp.model.repos.UserRepo;

import javax.inject.Named;

import dagger.Module;

/**
 * Created by v4n0v on 26.04.18.
 */

@Module(includes = StrategyModule.class)
public class TestCacheModule {

    UserRepo.RepoStrategy strategy (@Named("paper") UserRepo.RepoStrategy strategy){
        return strategy;
    }

}
