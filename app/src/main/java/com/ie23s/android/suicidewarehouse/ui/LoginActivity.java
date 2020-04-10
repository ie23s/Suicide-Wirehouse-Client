package com.ie23s.android.suicidewarehouse.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ie23s.android.suicidewarehouse.MyIntentService;
import com.ie23s.android.suicidewarehouse.R;
import com.ie23s.android.suicidewarehouse.data.DataCollector;
import com.ie23s.android.suicidewarehouse.ui.login.LoginFragment;

public class LoginActivity extends AppCompatActivity {
    private DataCollector dataCollector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity);

        startService(MyIntentService.class);

        dataCollector = new DataCollector(this, false);
        dataCollector.registerReceiver();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance(this))
                    .commitNow();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        dataCollector.registerReceiver();
    }

    @Override
    public void onPause() {
        super.onPause();
        dataCollector.unregisterReceiver();
    }

    @Override
    public void finish() {
        dataCollector.unregisterReceiver();
        super.finish();
    }

    private void startService(Class<?> serviceClass) {
        Intent intent = new Intent(this, serviceClass);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(intent);
        } else {
            this.startService(intent);
        }
    }

    public DataCollector getDataCollector() {
        return dataCollector;
    }
}
