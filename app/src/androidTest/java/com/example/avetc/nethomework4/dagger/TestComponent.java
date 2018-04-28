package com.example.avetc.nethomework4.dagger;

import com.example.avetc.nethomework4.USerRepoInstumentedTest;
import com.example.avetc.nethomework4.dagger.modules.RepoModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RepoModule.class, TestCacheModule.class})
public interface TestComponent {
    void inject(USerRepoInstumentedTest test);
}
