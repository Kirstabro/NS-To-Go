package com.example.ns_to_go.Activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.ns_to_go.Data.Database;
import com.example.ns_to_go.Data.Station;
import com.example.ns_to_go.Location.DirectionApiListener;
import com.example.ns_to_go.Location.DirectionApiManager;
import com.example.ns_to_go.Location.LocationTracker;
import com.example.ns_to_go.Location.LocationTrackerListener;
import com.example.ns_to_go.Notifications;
import com.example.ns_to_go.R;
import com.google.android.gms.common.data.DataBuffer;
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
import com.google.android.material.snackbar.Snackbar;
import java.util.Calendar;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionApiListener, LocationTrackerListener,  GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    private DirectionApiManager directionApiManager;
    private Marker userLocation;

    private Database database;

    private LocationTracker locationTracker;

    private Notifications notifications;

    private Station nearestStation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        notifications = new Notifications(this);
        directionApiManager = new DirectionApiManager(this, this);
        locationTracker = new LocationTracker(this, this, (LocationManager) getSystemService(Context.LOCATION_SERVICE), this.notifications);

        database = new Database(this);

        nearestStation = new Station();
        setNearestStation();



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

        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setOnMarkerClickListener(this);

        setNearestStationMarker();


    }

    @Override
    public boolean onMarkerClick(Marker marker)
    {
        if (! marker.equals(userLocation))
        {
            Intent intent = new Intent(this, DeparturesActivity.class);
            intent.putExtra("STATION", nearestStation);
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
            userLocation = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("User"));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(15)
                    .build();

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            directionApiManager.generateDirections((latLng), new LatLng(nearestStation.getLat(), nearestStation.getLng()));

        }
        userLocation.setPosition(latLng);
    }

    public void centerCamera(View view) {
        if (userLocation == null)
            return;
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(userLocation.getPosition())
                .zoom(15)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void setNearestStation()
    {
        ArrayList<Station> stations = new ArrayList<>();
        stations = database.readValues();

        for(Station s : stations)
        {

        }

        //TODO: Check which station is the nearest
        nearestStation.setLat(51.595);
        nearestStation.setLng(4.78);
    }

    public void setNearestStationMarker()
    {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.595, 4.78))
                .title("station"));


    }
}
