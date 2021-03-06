package com.example.ns_to_go.Location;

import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Interface used by DirectionApiManager for callbacks
 */
public interface DirectionApiListener {
    void routeLineAvailable(PolylineOptions polylineOptions);

    void onResponseError(Error error);
}
