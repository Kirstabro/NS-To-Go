package com.example.ns_to_go.Activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.StackView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.ns_to_go.Data.Database;
import com.example.ns_to_go.Data.Station;
import com.example.ns_to_go.Location.DirectionApiListener;
import com.example.ns_to_go.Location.DirectionApiManager;
import com.example.ns_to_go.Location.LocationTracker;
import com.example.ns_to_go.Location.LocationTrackerListener;
import com.example.ns_to_go.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionApiListener, LocationTrackerListener,  GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    private DirectionApiManager directionApiManager;
    private LatLng userLocation;

    private Database database;

    private LocationTracker locationTracker;

    private Station nearestStation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        directionApiManager = new DirectionApiManager(this, this);
        locationTracker = new LocationTracker(this, this, (LocationManager) getSystemService(Context.LOCATION_SERVICE));

        this.database = new Database(this);

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

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setOnMarkerClickListener(this);

    }

    @Override
    public boolean onMarkerClick(Marker marker)
    {
        if (! marker.equals(userLocation))
        {
            Intent intent = new Intent(this, DeparturesActivity.class);
            intent.putExtra("STATION", (Station)marker.getTag());
            startActivity(intent);
        }
            return false;

    }

    @Override
    public void routeLineAvailable(PolylineOptions polylineOptions) {
        mMap.addPolyline(polylineOptions);
    }

    @Override
    public void onResponseError(Error error) {
        Toast.makeText(this, "Could not connect with Directions Api", Toast.LENGTH_SHORT);
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (userLocation == null) {
            userLocation = latLng;

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(15)
                    .build();

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            setNearestStation();
            setNearestStationMarker();

            setAllStationMarkers();
            directionApiManager.generateDirections((latLng), new LatLng(nearestStation.getLat(), nearestStation.getLng()));

        }
        userLocation = latLng;

    }

    public void centerCamera(View view) {
        if (userLocation == null)
            return;
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(userLocation)
                .zoom(15)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void setNearestStation()
    {
        ArrayList<Station> stations;
        stations = database.readValues();
        float[] results = new float[3];
        Station closestStation = null;
        float closestAmountOfMeters = Float.MAX_VALUE;
        for(Station s : stations)
        {
            Location.distanceBetween(userLocation.latitude, userLocation.longitude, s.getLat(), s.getLng(), results);

            if (results[0] < closestAmountOfMeters){
                closestAmountOfMeters = results[0];
                closestStation = s;
            }
        }


        //TODO: Check which station is the nearest
        nearestStation = closestStation;
        Log.d("CLOSEST STATION", nearestStation.getNames()[2]);
        Log.d("CLOSEST STATION", "lat: " + nearestStation.getLat() + ", lng: " + nearestStation.getLng());
    }

    public void setNearestStationMarker()
    {
        Marker marker = mMap.addMarker (new MarkerOptions()
                .position(nearestStation.getLatLng())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.station_icon_large))
                .title(nearestStation.getNames()[2]));

        marker.setTag(nearestStation);


    }

    public void setAllStationMarkers()
    {
        ArrayList<Station> stations;
        stations = database.readValues();

        for (Station s : stations)
        {
            if(! s.equals(nearestStation))
            {
                Marker marker = mMap.addMarker (new MarkerOptions()
                        .position(s.getLatLng())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.station_icon_small))
                        .title(s.getNames()[2]));

                marker.setTag(s);
            }
        }
    }
}
