package com.example.avetc.nethomework4;



import android.app.Application;

import com.activeandroid.ActiveAndroid;

import com.example.avetc.nethomework4.dagger.AppComponent;
import com.example.avetc.nethomework4.dagger.DaggerAppComponent;
import com.example.avetc.nethomework4.dagger.modules.AppModule;


import io.paperdb.Paper;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;


public class App extends com.activeandroid.app.Application
//public class App extends Application
{
    private static App instance;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    private AppComponent appComponent;

    @Override
    public void onCreate()
    {
        super.onCreate();
       // ActiveAndroid.initialize(this);
        instance = this;
        Timber.plant(new Timber.DebugTree());
        Paper.init(this);
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);

        appComponent= DaggerAppComponent.builder()
                 .appModule(new AppModule(this))
                 .build();

    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        //ActiveAndroid.dispose();
    }

    public static App getInstance()
    {
        return instance;
    }
}
