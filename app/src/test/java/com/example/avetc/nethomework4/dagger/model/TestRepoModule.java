package com.example.avetc.nethomework4.dagger.model;

import com.example.avetc.nethomework4.entities.User;
import com.example.avetc.nethomework4.mvp.model.repos.UserRepo;

import org.mockito.Mock;
import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;

@Module
public class TestRepoModule {
    @Provides
    public UserRepo userRepo(){
        User user= new User("v4n0v", null, null);
        UserRepo repo = Mockito.mock(UserRepo.class);
        Mockito.when(repo.getUser("v4n0v")).thenReturn(Observable.just(user));
        return repo;
    }
}
