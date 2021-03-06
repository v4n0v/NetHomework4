package com.example.avetc.nethomework4.entities;

public class Repository  {

    public Repository(int id, String name, String htmlUrl) {
        this.id = id;
        this.name = name;
        this.htmlUrl = htmlUrl;
    }

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
