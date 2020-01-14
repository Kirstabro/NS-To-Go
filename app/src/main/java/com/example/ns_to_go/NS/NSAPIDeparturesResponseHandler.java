package com.example.ns_to_go.NS;

import com.example.ns_to_go.Data.Departure;

import java.util.ArrayList;

public interface NSAPIDeparturesResponseHandler {
    void departuresReceived(ArrayList<Departure> departure);
    void onError(String message);
}
