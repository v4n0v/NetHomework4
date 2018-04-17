package com.example.avetc.nethomework4.image;

import com.example.avetc.nethomework4.entities.realm.RealmImage;

import java.io.File;

import io.realm.Realm;
import timber.log.Timber;

public class RealmImageCache implements ICacheImage {
    @Override
    public void writeToCache(File file, String url) {
        Realm realm = Realm.getDefaultInstance();
        RealmImage realmImage = realm.where(RealmImage.class).equalTo("url", url).findFirst();
        if (realmImage != null) {
            clearImageCache(realm);
        }
        realm.executeTransaction(realm1 -> {
            realm.delete(RealmImage.class);
            RealmImage newRealmImage = realm.createObject(RealmImage.class, url);
            newRealmImage.setPath(file.getAbsolutePath());
        });
        Timber.d("realm saved image: "+file);
    }

    private void clearImageCache(Realm realm) {
        realm.executeTransaction(realm1 -> realm1.delete(RealmImage.class));

    }



    @Override
    public File readFromCache(String url) {
        Realm realm = Realm.getDefaultInstance();
        RealmImage realmImage = realm.where(RealmImage.class).equalTo("url", url).findFirst();
        File file = new File(realmImage.getPath());
        Timber.d("realm saved image: "+file);
        return file;
    }


}
