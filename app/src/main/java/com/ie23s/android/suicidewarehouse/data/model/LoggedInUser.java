package com.ie23s.android.suicidewarehouse.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private int userId;
    private String displayName;
    private String sessionID;

    public LoggedInUser(int userId, String displayName, String sessionID) {
        this.userId = userId;
        this.displayName = displayName;
        this.sessionID = sessionID;
    }

    public int getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSessionID() {
        return sessionID;
    }
}
