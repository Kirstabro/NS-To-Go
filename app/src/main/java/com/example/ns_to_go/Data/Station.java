package com.example.ns_to_go.Data;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Station implements Serializable
{

    private StationType stationType;
    private String land;
    private double lat;
    private double lng;
    private String[] names;
    private String code;
    private String EVACode;
    private String UICCODE;

    public Station(StationType stationType, String land, double lat, double lng, String[] names, String code, String EVACode, String UICCODE)
    {
        this.stationType = stationType;
        this.land = land;
        this.lat = lat;
        this.lng = lng;
        this.names = names;
        this.code = code;
        this.EVACode = EVACode;
        this.UICCODE = UICCODE;
    }

    public Station()
    {
    }

    //region getters and setters
    public StationType getStationType()
    {
        return stationType;
    }

    public void setStationType(StationType stationType)
    {
        this.stationType = stationType;
    }

    public String getLand()
    {
        return land;
    }

    public void setLand(String land)
    {
        this.land = land;
    }

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public double getLng()
    {
        return lng;
    }

    public void setLng(double lng)
    {
        this.lng = lng;
    }

    public String[] getNames()
    {
        return names;
    }

    public void setNames(String[] names)
    {
        this.names = names;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getEVACode()
    {
        return EVACode;
    }

    public void setEVACode(String EVACode)
    {
        this.EVACode = EVACode;
    }

    public String getUICCODE()
    {
        return UICCODE;
    }

    public void setUICCODE(String UICCODE)
    {
        this.UICCODE = UICCODE;
    }

    public LatLng getLatLng(){
        return new LatLng(lat, lng);
    }
    //endregion
}
