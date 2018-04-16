package com.example.avetc.nethomework4;

import android.app.Application;

import io.paperdb.Paper;
import io.realm.Realm;
import timber.log.Timber;


public class App extends Application
{
    private static App instance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        Timber.plant(new Timber.DebugTree());
        Paper.init(this);
        Realm.init(this);
    }

    public static App getInstance()
    {
        return instance;
    }
}
