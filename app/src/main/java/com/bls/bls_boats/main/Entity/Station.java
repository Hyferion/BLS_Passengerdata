package com.bls.bls_boats.main.Entity;

public class Station {
    public static final String TABLE_NAME = "haltestelle";

    public static final String COLUMN_STATIONNUMBER = "haltestellenummer";
    public static final String COLUMN_STATIONNAME = "haltestellename";

    private int stationNumber;
    private String stationName;

    // Create Table SQL Query
    public static final String CREATE_TABLE =
            "CREATE TABLE "+TABLE_NAME+ "("
            + COLUMN_STATIONNUMBER + " INTEGER PRIMARY KEY,"
            + COLUMN_STATIONNAME + " VARCHAR(255))";

    public Station(){

    }

    public Station(int stationNumber, String stationName){
        this.stationNumber = stationNumber;
        this.stationName = stationName;
    }

    public int getStationNumber(){
        return stationNumber;
    }

    public String getStationName(){
        return stationName;
    }

    public void setStationNumber(int stationNumber){
        this.stationNumber = stationNumber;
    }

    public void setStationName(String stationName){
        this.stationName = stationName;
    }

    @Override
    public String toString(){
        return this.stationName;
    }
}
