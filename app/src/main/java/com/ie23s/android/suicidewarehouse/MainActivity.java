package com.ie23s.android.suicidewarehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.TextView;

import com.ie23s.android.suicidewarehouse.utils.ConnectionUtil;
import com.ie23s.android.suicidewarehouse.utils.geolocation.GeolocationCallback;
import com.ie23s.android.suicidewarehouse.utils.geolocation.GeolocationUtil;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.textview);
        new ConnectionUtil().openConnection();
        new Thread(() -> System.out.println("dfsfsdfds" + new ConnectionUtil().openConnection())).start();
        //GeolocationUtil geolocationUtil = new GeolocationUtil(this, this, 1);
        //geolocationUtil.init();
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
