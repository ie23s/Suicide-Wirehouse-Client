package com.ie23s.android.suicidewarehouse;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ie23s.android.suicidewarehouse.utils.SettingsUtil;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private TextView textView1;
    private boolean s = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.textview);

        //new ConnectionUtil().openConnection();
        // Thread(() -> System.out.println("dfsfsdfds" + new ConnectionUtil().openConnection())).start();
        //GeolocationUtil geolocationUtil = new GeolocationUtil(this, this, 1);
        //geolocationUtil.init();
        findViewById(R.id.button).setOnClickListener(l -> {
            if (s) {
                Intent intent = new Intent(this, MyIntentService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    this.startForegroundService(intent);
                } else {
                    this.startService(intent);
                }
            }
            s = false;

//            Intent intentq = new Intent(MyIntentService.ACTION_GET);
//            intentq.putExtra("name", "dsfds");
//            this.sendBroadcast(intentq);
        });

        SettingsUtil settingsUtil = new SettingsUtil(this);

    }

    @Override
    public void onLocationChanged(Location location) {
        textView1.setText(location.getAltitude() + " " + location.getLatitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
