package com.example.ns_to_go.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Departure {

    private String direction;
    private LocalDateTime plannedTime;
    private LocalDateTime actualTime;
    private String plannedTrack;
    private boolean cancelled;
    private String trainType;
    private ArrayList<String> routeStations;

    public Departure(String direction, String plannedTime, String actualTime, String plannedTrack, boolean cancelled, String trainType, ArrayList<String> routeStations){
        this.direction = direction;
        this.plannedTime = getLocalDatTimeFromString(plannedTime);
        this.actualTime = getLocalDatTimeFromString(actualTime);
        this.plannedTrack = plannedTrack;
        this.cancelled = cancelled;
        this.trainType = trainType;
        this.routeStations = routeStations;
    }

    public Departure(String direction, LocalDateTime plannedTime, LocalDateTime actualTime, String plannedTrack, boolean cancelled, String trainType, ArrayList<String> routeStations) {
        this.direction = direction;
        this.plannedTime = plannedTime;
        this.actualTime = actualTime;
        this.plannedTrack = plannedTrack;
        this.cancelled = cancelled;
        this.trainType = trainType;
        this.routeStations = routeStations;
    }

    public LocalDateTime getLocalDatTimeFromString(String localDateTime){
        int year = Integer.parseInt(localDateTime.substring(0,4));
        int month = Integer.parseInt(localDateTime.substring(5,7));
        int day = Integer.parseInt(localDateTime.substring(8,10));
        LocalDate localDate = LocalDate.of(year, month, day);
        int hour = Integer.parseInt(localDateTime.substring(11, 13));
        int minute = Integer.parseInt(localDateTime.substring(14,16));
        LocalTime localTime = LocalTime.of(hour, minute);
        return LocalDateTime.of(localDate, localTime);
    }

    //region getters & setters

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public LocalDateTime getPlannedTime() {
        return plannedTime;
    }

    public void setPlannedTime(LocalDateTime plannedTime) {
        this.plannedTime = plannedTime;
    }

    public LocalDateTime getActualTime() {
        return actualTime;
    }

    public void setActualTime(LocalDateTime actualTime) {
        this.actualTime = actualTime;
    }

    public String getPlannedTrack() {
        return plannedTrack;
    }

    public void setPlannedTrack(String plannedTrack) {
        this.plannedTrack = plannedTrack;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public ArrayList<String> getRouteStations() {
        return routeStations;
    }

    public void setRouteStations(ArrayList<String> routeStations) {
        this.routeStations = routeStations;
    }

    //endregion
}
