package com.example.avetc.nethomework4;

import com.arellomobile.mvp.MvpView;

/**
 * Created by avetc on 11.04.2018.
 */

public interface MainView extends MvpView {
    void setName(String username);

    void loadAvatar(String url);

    void updateList();

    void init();
}
