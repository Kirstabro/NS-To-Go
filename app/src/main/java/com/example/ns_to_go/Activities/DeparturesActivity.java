package com.example.ns_to_go.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private Station selectedStation;

    SwipeRefreshLayout refreshLayout;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departures);

        recyclerView = findViewById(R.id.recyclerview);
        departures = new ArrayList<>();

        message = findViewById(R.id.departuresTextView);
        message.setText(R.string.departures);

        selectedStation = (Station)getIntent().getSerializableExtra("STATION");

        setTitle(selectedStation.getNames()[2]);

        NSAPIDeparturesRequestHelper requestHelper = new NSAPIDeparturesRequestHelper(this,this);
        requestHelper.getDepartures(selectedStation);

        adapter = new DeparturesAdapter(departures);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        refreshLayout = findViewById(R.id.refresh);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestHelper.getDepartures(selectedStation);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

    }

    @Override
    public void departuresReceived(ArrayList<Departure> departure)
    {
        departures.clear();

        for(Departure d : departure)
        {
            this.departures.add(d);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(String message)
    {

    }
}
