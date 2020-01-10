package com.example.ns_to_go.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ns_to_go.Data.Database;
import com.example.ns_to_go.R;
import com.google.android.gms.common.data.DataBuffer;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button NL;
    Button ENG;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askPermission(Manifest.permission.ACCESS_FINE_LOCATION);
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




}
