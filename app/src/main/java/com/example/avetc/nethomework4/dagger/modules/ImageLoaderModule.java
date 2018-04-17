package com.example.avetc.nethomework4.dagger.modules;


import android.widget.ImageView;

import com.example.avetc.nethomework4.image.AAImageCache;
import com.example.avetc.nethomework4.image.GlideLoader;
import com.example.avetc.nethomework4.image.ICacheImage;
import com.example.avetc.nethomework4.image.IImageLoader;
import com.example.avetc.nethomework4.image.PaperImageCache;
import com.example.avetc.nethomework4.image.RealmImageCache;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module//(includes = {ImageCacheModule.class})
public class ImageLoaderModule {

    @Singleton
    @Provides
    public IImageLoader<ImageView> glideLoader(@Named("realm") ICacheImage cacheImage) {
        return new GlideLoader(cacheImage);
    }

    @Provides
    @Named("paper")
    ICacheImage paperCache() {
        return new PaperImageCache();
    }

    @Provides
    @Named("aa")
    ICacheImage passiveCache() {
        return new AAImageCache();
    }

    @Provides
    @Named("realm")
    ICacheImage realmCache() {
        return new RealmImageCache();
    }


}
