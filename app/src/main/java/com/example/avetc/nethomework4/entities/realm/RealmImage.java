package com.example.avetc.nethomework4.entities.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class RealmImage extends RealmObject {
    @PrimaryKey
    private String url;
    private String path;



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
