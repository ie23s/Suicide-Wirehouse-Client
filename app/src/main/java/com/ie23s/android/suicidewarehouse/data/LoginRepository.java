package com.ie23s.android.suicidewarehouse.data;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ie23s.android.suicidewarehouse.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    private MutableLiveData<Result<LoggedInUser>> result = new MutableLiveData<>();

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public void login(String username, String password) {
        // handle login
        // Get a handler that can be used to post to the main thread
        Handler mainHandler = new Handler(Looper.getMainLooper());

        new Thread(() -> {
            Result<LoggedInUser> result = dataSource.login(username, password);
            if (result instanceof Result.Success) {
                setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
            }

            Runnable myRunnable = new MyRunnable(result);
            mainHandler.post(myRunnable);
            //this.result.setValue(result);

        }).start();
    }

    public LiveData<Result<LoggedInUser>> getResultState() {
        return result;
    }

    private class MyRunnable implements Runnable {
        private Result<LoggedInUser> result;

        public MyRunnable(Result<LoggedInUser> result) {
            this.result = result;
        }

        @Override
        public void run() {
            LoginRepository.this.result.setValue(result);
        }
    }
}
