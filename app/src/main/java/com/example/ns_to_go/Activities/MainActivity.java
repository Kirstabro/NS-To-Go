package com.example.ns_to_go.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ns_to_go.Data.Database;
import com.example.ns_to_go.Data.Departure;
import com.example.ns_to_go.Data.Station;

import com.example.ns_to_go.NS.NSAPIStationsRequestHelper;
import com.example.ns_to_go.NS.NSAPIStationsResponseHandler;
import com.example.ns_to_go.R;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NSAPIStationsResponseHandler
{

    Button NL;
    Button ENG;
    Button start;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.StartBttn);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        database = new Database(this);

        if (! database.isTableFilled())
        {
            NSAPIStationsRequestHelper requestHelper = new NSAPIStationsRequestHelper(this,this);
            requestHelper.getStations();

        } else {
            start.setEnabled(true);
        }

        //region changeLanguage
        NL = findViewById(R.id.NLBttn);
        ENG = findViewById(R.id.ENGBttn);

        NL.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Locale locale2 = new Locale("default");
                Locale.setDefault(locale2);

                Configuration config2 = new Configuration();
                config2.locale = locale2;

                getBaseContext().getResources().updateConfiguration(
                        config2,getBaseContext().getResources().getDisplayMetrics());

            }
        });

        ENG.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Locale locale2 = new Locale("en");
                Locale.setDefault(locale2);

                Configuration config2 = new Configuration();
                config2.locale = locale2;

                getBaseContext().getResources().updateConfiguration(
                        config2,getBaseContext().getResources().getDisplayMetrics());

            }
        });

        //endregion

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

    public void openMap(View view)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("DATABASE", database);
        startActivity(intent);
    }


    @Override
    public void stationsReceived(ArrayList<Station> station)
    {
        database.resetTable();
        for(Station s : station)
        {
            database.insertValue(s);
        }

        start.setEnabled(true);

    }


    @Override
    public void onError(String message)
    {

    }
}