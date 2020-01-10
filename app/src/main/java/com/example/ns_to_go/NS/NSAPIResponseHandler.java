package com.example.ns_to_go.NS;

import com.example.ns_to_go.Data.Departure;
import com.example.ns_to_go.Data.Station;

import java.util.ArrayList;

public interface NSAPIResponseHandler {
    void stationsReceived(ArrayList<Station> station);
    void departuresReceived(ArrayList<Departure> departure);
    void onError(String message);
}
