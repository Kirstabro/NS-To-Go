package com.example.ns_to_go.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ns_to_go.Adapters.DeparturesAdapter;
import com.example.ns_to_go.Data.Departure;
import com.example.ns_to_go.Data.Station;
import com.example.ns_to_go.NS.NSAPIDeparturesRequestHelper;
import com.example.ns_to_go.NS.NSAPIDeparturesResponseHandler;
import com.example.ns_to_go.R;

import java.util.ArrayList;

public class DeparturesActivity extends AppCompatActivity implements NSAPIDeparturesResponseHandler
{

    private RecyclerView recyclerView;
    private DeparturesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Departure> departures;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departures);

        recyclerView = findViewById(R.id.recyclerview);
        departures = new ArrayList<>();

        NSAPIDeparturesRequestHelper requestHelper = new NSAPIDeparturesRequestHelper(this,this);
        requestHelper.getDepartures((Station)getIntent().getSerializableExtra("STATION"));

        adapter = new DeparturesAdapter(departures);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void departuresReceived(ArrayList<Departure> departure)
    {
        departures = departure;

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String message)
    {

    }
}
