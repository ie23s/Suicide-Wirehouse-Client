package com.ie23s.android.suicidewarehouse.ui.login;

import android.content.Context;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ie23s.android.suicidewarehouse.R;
import com.ie23s.android.suicidewarehouse.data.DataCollector;
import com.ie23s.android.suicidewarehouse.data.LoginRepository;
import com.ie23s.android.suicidewarehouse.data.Result;
import com.ie23s.android.suicidewarehouse.data.model.LoggedInUser;

import java.io.IOException;

public class LoginViewModel extends ViewModel implements DataCollector.Receiver {

    private final DataCollector dataCollector;
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository, final Context context) {
        this.loginRepository = loginRepository;
        dataCollector = new DataCollector(context, false, this);
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        dataCollector.registerReceiver();
        dataCollector.sendData(new DataCollector.Data(101, username + ":" + password));
    }

    public void finishLogin(Result<LoggedInUser> result) {
        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getUseranme())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
            System.out.println("fdgfgdfgf");
        }

    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onReceive(DataCollector.Data data) {
        Result<LoggedInUser> result;
        String[] combination = data.getData().split(":");
        if (data.getStatus() == 200 && combination[0].equalsIgnoreCase("211")) {
            result = loginRepository.login(combination[0], combination[1]);
            System.out.println("sdf " + combination[0].equalsIgnoreCase("211"));
        } else if (data.getStatus() == 200 && !combination[0].equalsIgnoreCase("211")) {

            result = new Result.Error(new IOException(combination[1]));
        } else {
            result = new Result.Error(new IOException(data.getData()));
        }
        finishLogin(result);
        dataCollector.unregisterReceiver();

    }
}
