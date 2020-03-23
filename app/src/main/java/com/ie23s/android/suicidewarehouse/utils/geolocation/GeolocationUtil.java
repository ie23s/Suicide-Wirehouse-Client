package com.ie23s.android.suicidewarehouse.utils.geolocation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

public class GeolocationUtil {
    private Activity activity;
    private LocationListener locationListener;

    private LocationManager locationManager;
    private int delay;

    public GeolocationUtil(Activity activity, LocationListener locationListener, int delay) {
        this.activity = activity;
        this.locationListener = locationListener;
        this.delay = delay;

    }

    public void init() {
        locationManager = (LocationManager)
                activity.getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED &&
                activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
    }
}
