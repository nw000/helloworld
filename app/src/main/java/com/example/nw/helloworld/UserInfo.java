package com.example.nw.helloworld;

/**
 * Created by ${wentong.chen} on 18/1/24.
 */

public class UserInfo {
    private final int id;
    private final String login;
    private final String avatar_url;

    public UserInfo(int id, String login, String avatar_url) {
        this.id = id;
        this.login = login;
        this.avatar_url = avatar_url;
    }

    public String getAvatarUrl() {
        if (avatar_url.isEmpty()) return avatar_url;
        return avatar_url.split("\\?")[0];
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    @Override public String toString() {
        return "id -> " + id + " login -> " + login;
    }
}
