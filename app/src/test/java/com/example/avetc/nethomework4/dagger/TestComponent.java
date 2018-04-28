package com.example.avetc.nethomework4.dagger;

import com.example.avetc.nethomework4.dagger.model.TestRepoModule;
import com.example.avetc.nethomework4.mvp.presenter.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestRepoModule.class})
public interface TestComponent {
    void inject(MainPresenter mainPresenter);

}
