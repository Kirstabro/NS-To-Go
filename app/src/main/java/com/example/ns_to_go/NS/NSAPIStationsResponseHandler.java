package com.example.ns_to_go.NS;

import com.example.ns_to_go.Data.Departure;
import com.example.ns_to_go.Data.Station;

import java.util.ArrayList;

public interface NSAPIStationsResponseHandler {
    void stationsReceived(ArrayList<Station> station);
    void onError(String message);
}
