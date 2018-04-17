package com.example.avetc.nethomework4.dagger.modules;

import com.example.avetc.nethomework4.App;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    App app(){
        return app;
    }

}
