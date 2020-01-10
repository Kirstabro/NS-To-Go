package com.example.ns_to_go;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ns_to_go.Adapters.SectionsPageAdapter;
import com.example.ns_to_go.Data.Departure;
import com.example.ns_to_go.Data.Station;
import com.example.ns_to_go.NS.NSAPIRequestHelper;
import com.example.ns_to_go.NS.NSAPIResponseHandler;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SectionsPageAdapter sectionsPageAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        this.sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        NSAPIRequestHelper nsapiRequestHelper = new NSAPIRequestHelper(this, new NSAPIResponseHandler() {
            @Override
            public void stationsReceived(ArrayList<Station> station) {
                Log.d("STATIONS", "STATIONSRECEIVED ________________________________________________________________________________________");
            }

            @Override
            public void departuresReceived(ArrayList<Departure> departure) {

            }

            @Override
            public void onError() {

            }
        });

        nsapiRequestHelper.getStations();

        viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);



    }

    public void askPermission(String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{permission},
                    1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "Location permission not accepted.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setupViewPager(ViewPager viewPager)
    {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
//        adapter.addFrament(new Fragment());
        viewPager.setAdapter(adapter);
    }

}
