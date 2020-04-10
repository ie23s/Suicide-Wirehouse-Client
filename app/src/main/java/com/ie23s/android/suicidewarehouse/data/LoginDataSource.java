package com.ie23s.android.suicidewarehouse.data;

import com.ie23s.android.suicidewarehouse.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private final DataCollector dataCollector;

    public LoginDataSource(DataCollector dataCollector) {
        this.dataCollector = dataCollector;
        dataCollector.setReceiver(data -> {

        });
    }

    public Result login(String username, String password) {
        dataCollector.sendData(new DataCollector.Data(101,
                username + ":" + password));

        DataCollector.Data data = dataCollector.next();

        String[] userData;

        if (data.getStatus() == 201) {
            userData = data.getData().split(":");


            LoggedInUser fakeUser =
                    new LoggedInUser(Integer.parseInt(userData[0]),
                            username, userData[1]);
            return new Result.Success<>(fakeUser);
        } else {
            return new Result.Error(new IOException(String.valueOf(data.getStatus())));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
