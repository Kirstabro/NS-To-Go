package com.example.ns_to_go.NS;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.ns_to_go.Data.Departure;
import com.example.ns_to_go.Data.Station;
import com.example.ns_to_go.Data.StationType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NSAPIStationsRequestHelper {

    private static NSAPIStationsRequestHelper sInstance = null;
    private String baseUrl = "https://gateway.apiportal.ns.nl/reisinformatie-api/api/v2/";
    private NSAPIStationsResponseHandler responseHandler;
    private RequestQueue queue;


    public NSAPIStationsRequestHelper(Context context, NSAPIStationsResponseHandler responseHandler){
        this.responseHandler = responseHandler;
        queue = Volley.newRequestQueue(context);
    }

    public static NSAPIStationsRequestHelper getInstance(Context context, NSAPIStationsResponseHandler responseHandler){
        if (sInstance == null){
            sInstance = new NSAPIStationsRequestHelper(context, responseHandler);
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

}
