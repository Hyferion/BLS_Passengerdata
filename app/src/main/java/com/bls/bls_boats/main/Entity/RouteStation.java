package com.bls.bls_boats.main.Entity;

public class RouteStation {
    public static final String TABLE_NAME = "linie_haltestelle";

    public static final String COLUMN_STATIONROUTENUMBER = "haltestellelinienummer";
    public static final String COLUMN_STATIONNUMBER = "haltestellenummer";
    public static final String COLUMN_ROUTENUMBER = "linienummer";

    private int stationNumber;
    private int routeNumber;

    // Create Table SQL Query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_STATIONROUTENUMBER + " INTEGER PRIMARY KEY,"
            + COLUMN_STATIONNUMBER + " INTEGER,"
            + COLUMN_ROUTENUMBER + " INTEGER,"
            + " FOREIGN KEY ("+ COLUMN_ROUTENUMBER +") REFERENCES "+Route.TABLE_NAME +"("+Route.COLUMN_ROUTENUMBER +"),"
            + " FOREIGN KEY ("+ COLUMN_STATIONNUMBER +") REFERENCES " + Station.TABLE_NAME + "("+Station.COLUMN_STATIONNUMBER +"));";

    public RouteStation(){

    }

    public RouteStation(int stationNumber, int routeNumber){
        this.stationNumber = stationNumber;
        this.routeNumber = routeNumber;
    }

    public int getStationNumber(){
        return stationNumber;
    }

    public int getRouteNumber(){
        return routeNumber;
    }

    public void setStationNumber(int stationNumber){
        this.stationNumber = stationNumber;
    }

    public void setRouteNumber(int routeNumber){
        this.routeNumber = routeNumber;
    }

    @Override
    public String toString(){
        return "LinieNr:"+this.routeNumber + " HaltestelleNr:"+this.stationNumber;
    }
}
