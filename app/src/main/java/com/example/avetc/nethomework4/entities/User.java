package com.example.avetc.nethomework4.entities;

/**
 * Created by avetc on 11.04.2018.
 */

public class User {
//    @SerializedName("login")
//    @Expose
    private String login;
    private String avatarUrl;
    private String reposUrl;

    public String getReposUrl() {
        return reposUrl;
    }

    public void setReposUrl(String reposUrl) {
        this.reposUrl = reposUrl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
