package com.example.avetc.nethomework4.dagger.model;

import com.example.avetc.nethomework4.entities.User;
import com.example.avetc.nethomework4.mvp.model.repos.UserRepo;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;

/**
 * Created by v4n0v on 25.04.18.
 */
@Module
public class TestRepoErrorModule {
    @Provides
    public UserRepo userRepo(UserRepo repo){
        User user= new User("v4n0v", null, null);
        //UserRepo repo = Mockito.mock(UserRepo.class);
        Mockito.when(repo.getUser("v4n0v")).thenReturn(Observable.error(new RuntimeException("ERROR!")));
        return repo;
    }
}
