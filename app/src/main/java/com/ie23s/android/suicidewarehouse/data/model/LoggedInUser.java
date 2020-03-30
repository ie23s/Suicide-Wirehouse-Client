package com.ie23s.android.suicidewarehouse.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String useranme;
    private String sesstion;

    public LoggedInUser(String useranme, String sesstion) {
        this.useranme = useranme;
        this.sesstion = sesstion;
    }

    public String getUseranme() {
        return useranme;
    }

    public String getSesstion() {
        return sesstion;
    }
}
