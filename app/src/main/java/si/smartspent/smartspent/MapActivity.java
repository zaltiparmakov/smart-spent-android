package si.smartspent.smartspent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import android.support.v4.content.ContextCompat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import si.smartspent.smartspent.Location.LocationEvent;
import si.smartspent.smartspent.Location.LocationService;

public class MapActivity extends DrawerActivity implements OnMapReadyCallback {
    private static final String TAG = MapActivity.class.getName();
    private GoogleMap mMap;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int REQUEST_PLACE_PICKER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_maps, frameLayout);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            startService(new Intent(this, LocationService.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startService(new Intent(this, LocationService.class));
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService(new Intent(this, LocationService.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LocationEvent event) {
        double lat = event.getLocation().getLatitude();
        double lon = event.getLocation().getLongitude();
        LatLng latLng = new LatLng(lat, lon);

        // TODO: get place by id from latlng, and write the title
//        mMap.addMarker(new MarkerOptions().position(latLng).title("Title"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        onMapReady(mMap);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // check permissions
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        mMap.animateCamera(zoom);

        /**
         * MOCK LOCATIONS
         *
         * TODO: Locations should be retrieved from the server
         */
        List<LatLng> list = new ArrayList<>();
        list.add(new LatLng(-37.1886, 145.708));
        list.add(new LatLng(-37.8361, 144.845));
        list.add(new LatLng(-38.4034, 144.192));
        list.add(new LatLng(-38.7597, 143.67));
        list.add(new LatLng(-36.9672, 141.083));

        int[] colors = {
                Color.rgb(102, 225, 0), // green
                Color.rgb(255, 0, 0)    // red
        };

        float[] startPoints = {
                0.2f, 1f
        };

        Gradient gradient = new Gradient(colors, startPoints);


        HeatmapTileProvider heatmapTileProvider = new HeatmapTileProvider.Builder()
                .data(list)
                .gradient(gradient)
                .build();

        TileOverlay tileOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(heatmapTileProvider));

        heatmapTileProvider.setOpacity(0.7);
        tileOverlay.clearTileCache();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(list.get(0)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
}
