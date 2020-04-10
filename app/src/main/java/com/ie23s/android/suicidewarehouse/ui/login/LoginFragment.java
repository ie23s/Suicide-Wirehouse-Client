package com.ie23s.android.suicidewarehouse.ui.login;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ie23s.android.suicidewarehouse.R;
import com.ie23s.android.suicidewarehouse.ui.LoginActivity;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private LoginActivity loginActivity;

    private TextView mLoginResult;

    private LoginFragment(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public static LoginFragment newInstance(LoginActivity loginActivity) {
        return new LoginFragment(loginActivity);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel

        mViewModel = ViewModelProviders.of(this, new LoginViewModelFactory(loginActivity.getDataCollector()))
                .get(LoginViewModel.class);

        final EditText usernameEditText = Objects.requireNonNull(getView()).findViewById(R.id.username);
        final EditText passwordEditText = getView().findViewById(R.id.password);
        final Button loginButton = getView().findViewById(R.id.login);
        final ProgressBar loadingProgressBar = getView().findViewById(R.id.loading);
        mLoginResult = getView().findViewById(R.id.login_result);

        mViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        mViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
                return;
            }
            if (loginResult.getSuccess() != null) {
                updateUiWithUser(loginResult.getSuccess());
            }
            loginActivity.setResult(Activity.RESULT_OK);

            //Complete and destroy login activity once successful
            loginActivity.finish();
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
            return false;
        });

        loginButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            mLoginResult.setVisibility(View.GONE);
            mViewModel.login(usernameEditText.getText().toString(),
                    passwordEditText.getText().toString());
        });
    }

    private void updateUiWithUser(LoggedInUserView success) {

    }

    private void showLoginFailed(@StringRes Integer error) {
        mLoginResult.setTextColor(Color.RED);
        mLoginResult.setText(loginActivity.getResources().getText(error));
        mLoginResult.setVisibility(View.VISIBLE);
    }

}
