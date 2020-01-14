package com.example.ns_to_go.NS;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ns_to_go.Data.Departure;
import com.example.ns_to_go.Data.Station;
import com.example.ns_to_go.Data.StationType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class NSAPIRequestHelper {

    private static NSAPIRequestHelper sInstance = null;
    private String baseUrl = "https://gateway.apiportal.ns.nl/reisinformatie-api/api/v2/";
    private NSAPIResponseHandler responseHandler;
    private RequestQueue queue;


    public NSAPIRequestHelper(Context context, NSAPIResponseHandler responseHandler){
        this.responseHandler = responseHandler;
        queue = Volley.newRequestQueue(context);
    }

    public static NSAPIRequestHelper getInstance(Context context, NSAPIResponseHandler responseHandler){
        if (sInstance == null){
            sInstance = new NSAPIRequestHelper(context, responseHandler);
        } else {
            sInstance.responseHandler = responseHandler;
        }
        return sInstance;
    }

    public void getStations(){
        try{
            NSAPIJsonObjectRequest stationsRequest = new NSAPIJsonObjectRequest(
                    Request.Method.GET,
                    baseUrl + "stations",
                    null,
                    (response) ->{
                        ArrayList<Station> stations = new ArrayList<Station>();
                        try {
                            JSONArray payload = response.getJSONArray("payload");
                            for(int i = 0 ; i < payload.length() ; i++){
                                JSONObject stationJson = payload.getJSONObject(i);

                                StationType stationType = StationType.valueOf(stationJson.getString("stationType"));
                                String land = stationJson.getString("land");
                                double lat = stationJson.getDouble("lat");
                                double lng = stationJson.getDouble("lng");
                                String[] names = new String[3];
                                names[0] = stationJson.getJSONObject("namen").getString("kort");
                                names[1] = stationJson.getJSONObject("namen").getString("middel");
                                names[2] = stationJson.getJSONObject("namen").getString("lang");
                                String code = stationJson.getString("code");
                                String evaCode = stationJson.getString("EVACode");
                                String uicCode = stationJson.getString("UICCode");

                                stations.add(new Station(stationType, land, lat, lng, names, code, evaCode, uicCode));
                            }

                            responseHandler.stationsReceived(stations);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    (error) ->{
                        responseHandler.onError("" + error.getNetworkTimeMs());
                    }

            );

            queue.add(stationsRequest);
        } catch(Exception e){

        }

    }

    public void getDepartures(Station station){
        String url = baseUrl + "departures?maxJourneys=25&lang=nl&uicCode=" + station.getUICCODE();
        ArrayList<Departure> departuresList = new ArrayList<>();
        try{

            NSAPIJsonObjectRequest departuresRequest = new NSAPIJsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    (response) ->{
                        try {
                            JSONObject payload = response.getJSONObject("payload");

                            JSONArray departures = payload.getJSONArray("departures");

                            for(int i = 0; i < departures.length(); i++){
                                JSONObject departure = departures.getJSONObject(i);

                                String direction = departure.getString("direction");
                                String plannedTime = departure.getString("plannedDateTime");
                                String actualTime = departure.getString("actualDateTime");
                                String plannedTrack = departure.getString("plannedTrack");
                                boolean cancelled = departure.getBoolean("cancelled");
                                String trainType = departure.getJSONObject("product").getString("longCategoryName");
                                ArrayList<String> routeStations = new ArrayList<>();
                                JSONArray routeStations1 = departure.getJSONArray("routeStations");
                                for(int j = 0 ; i < routeStations1.length(); j++){
                                    JSONObject routeStation = routeStations1.getJSONObject(j);
                                    routeStations.add(routeStation.getString("mediumName"));
                                }

                                departuresList.add(new Departure(direction, plannedTime, actualTime, plannedTrack, cancelled, trainType, routeStations));

                            }

                            responseHandler.departuresReceived(departuresList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    (error) -> {
                        responseHandler.onError(error.getMessage());
                    }
            );


            queue.add(departuresRequest);
        } catch (Exception e){

        }


    }

    public void getDepartures(String uicCode){
        String url = baseUrl + "departures?maxJourneys=25&lang=nl&uicCode=" + uicCode;
        ArrayList<Departure> departuresList = new ArrayList<>();
        try{

            NSAPIJsonObjectRequest departuresRequest = new NSAPIJsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    (response) ->{
                        try {
                            JSONObject payload = response.getJSONObject("payload");

                            JSONArray departures = payload.getJSONArray("departures");

                            for(int i = 0; i < departures.length(); i++){
                                JSONObject departure = departures.getJSONObject(i);

                                String direction = departure.getString("direction");
                                String plannedTime = departure.getString("plannedDateTime");
                                String actualTime = departure.getString("actualDateTime");
                                String plannedTrack = departure.getString("plannedTrack");
                                boolean cancelled = departure.getBoolean("cancelled");
                                String trainType = departure.getJSONObject("product").getString("longCategoryName");
                                ArrayList<String> routeStations = new ArrayList<>();
                                JSONArray routeStations1 = departure.getJSONArray("routeStations");
                                for(int j = 0 ; j < routeStations1.length(); j++){
                                    JSONObject routeStation = routeStations1.getJSONObject(j);
                                    routeStations.add(routeStation.getString("mediumName"));
                                }

                                departuresList.add(new Departure(direction, plannedTime, actualTime, plannedTrack, cancelled, trainType, routeStations));

                            }

                            responseHandler.departuresReceived(departuresList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    (error) -> {
                        responseHandler.onError(error.getMessage());
                    }
            );


            queue.add(departuresRequest);
        } catch (Exception e){

        }


    }

}
