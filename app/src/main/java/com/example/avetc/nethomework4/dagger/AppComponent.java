package com.example.avetc.nethomework4.dagger;

import com.example.avetc.nethomework4.dagger.modules.AppModule;
import com.example.avetc.nethomework4.dagger.modules.ImageLoaderModule;
import com.example.avetc.nethomework4.dagger.modules.RepoModule;
import com.example.avetc.nethomework4.mvp.presenter.MainPresenter;
import com.example.avetc.nethomework4.mvp.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;
@Singleton
@Component(modules = {AppModule.class, RepoModule.class, ImageLoaderModule.class})
public interface AppComponent {
    void inject(MainPresenter presenter);
    void inject(MainActivity activity);
}
