package si.smartspent.smartspent.Location;

import android.location.Location;

import com.google.android.gms.location.places.PlaceLikelihood;

public class LocationEvent {
    Location location;

    public LocationEvent(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return location.getLongitude() + " " + location.getLongitude();
    }
}
