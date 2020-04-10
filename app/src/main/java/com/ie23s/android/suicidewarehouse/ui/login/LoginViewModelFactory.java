package com.ie23s.android.suicidewarehouse.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ie23s.android.suicidewarehouse.data.DataCollector;
import com.ie23s.android.suicidewarehouse.data.LoginDataSource;
import com.ie23s.android.suicidewarehouse.data.LoginRepository;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {
    private DataCollector dataCollector;

    public LoginViewModelFactory(DataCollector dataCollector) {
        this.dataCollector = dataCollector;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(LoginRepository.getInstance(new LoginDataSource(dataCollector)));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
