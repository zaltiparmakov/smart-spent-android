package si.smartspent.smartspent.Location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

public class LocationService extends Service {
    private static final String TAG = LocationService.class.getName();
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;

    // enable GPS and Network Provider location listeners
    LocationListener[] mLocationListeners = new LocationListener[] {
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        public LocationListener(String provider) {
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            EventBus.getDefault().post(new LocationEvent(location));
            mLastLocation.set(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        if(mLocationManager == null) {
            mLocationManager = (LocationManager) getApplication().getSystemService(Context.LOCATION_SERVICE);
        }

        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_INTERVAL,
                    LOCATION_DISTANCE,
                    mLocationListeners[0]
            );
        } catch (SecurityException e) {
            Log.i(TAG, "fail to request location update. Ignoring..");
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "network provider does not exist, " + e.getMessage());
        }

        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    LOCATION_INTERVAL,
                    LOCATION_DISTANCE,
                    mLocationListeners[1]
            );
        } catch (SecurityException e) {
            Log.i(TAG, "failed to request location update. Ignoring..");
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "network provider does not exist, " + e.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mLocationManager != null) {
            for(int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception e) {
                    Log.i(TAG, "failed to remove location listeners. Ignoring..");
                }
            }
        }
    }
}
