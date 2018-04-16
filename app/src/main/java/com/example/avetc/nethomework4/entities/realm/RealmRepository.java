package com.example.avetc.nethomework4.entities.realm;

import com.example.avetc.nethomework4.entities.Repository;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;



public class RealmRepository extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private String htmlUrl;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }
}