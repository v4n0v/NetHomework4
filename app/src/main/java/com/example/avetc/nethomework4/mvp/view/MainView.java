package com.example.avetc.nethomework4.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.example.avetc.nethomework4.entities.User;


public interface MainView extends MvpView {
    void setName(String username);

    void loadAvatar(String url);

    void updateList();

    void init();

    void toast(String msg);


}
