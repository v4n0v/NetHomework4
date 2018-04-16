package com.example.avetc.nethomework4.mvp.model.repos;

import com.example.avetc.nethomework4.entities.Repository;
import com.example.avetc.nethomework4.entities.User;

import java.util.List;

import io.reactivex.Observable;



public interface IUserRepo {
    Observable<User> getUser(String username);
    Observable<List<Repository>> getRepos(User user);

}
