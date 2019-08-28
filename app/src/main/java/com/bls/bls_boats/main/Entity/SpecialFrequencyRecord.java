package com.bls.bls_boats.main.Entity;

import java.util.Date;

public class SpecialFrequencyRecord {

    public static final String TABLE_NAME = "frequenzerfassungspezial";

    public static final String COLUMN_SPECIALFREQUENCYRECORDNUMBER = "frequenzerfassungsspezialnummer";
    public static final String COLUMN_VESSELNUMBER = "fahrzeugnummer";
    public static final String COLUMN_ROUTENUMBER = "liniennummer";
    public static final String COLUMN_DISTANCE = "distanz";
    public static final String COLUMN_STARTTIME = "startzeit";
    public static final String COLUMN_ENDTIME = "endzeit";
    public static final String COLUMN_CREATEDATE = "erfassungsdatum";
    public static final String COLUMN_STATUS = "status";

    private int vesselNumber;
    private int routeNumber;
    private double distanz;
    private Date startTime;
    private Date endTime;
    private int status;

    //Create Query
    public static final String CREATE_TABLE =
            "CREATE TABLE "+TABLE_NAME+"("
            + COLUMN_SPECIALFREQUENCYRECORDNUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_VESSELNUMBER + " INTEGER,"
            + COLUMN_ROUTENUMBER + " INTEGER,"
            + COLUMN_DISTANCE + " REAL,"
            + COLUMN_STARTTIME + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COLUMN_ENDTIME + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COLUMN_CREATEDATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COLUMN_STATUS + " INTEGER DEFAULT 0,"
            + " FOREIGN KEY ("+ COLUMN_VESSELNUMBER +") REFERENCES "+Vessel.TABLE_NAME+"("+Vessel.COLUMN_VESSELNUMBER +"),"
            + " FOREIGN KEY ("+ COLUMN_ROUTENUMBER +") REFERENCES "+Route.TABLE_NAME+"("+Route.COLUMN_ROUTENUMBER +"));";

    public SpecialFrequencyRecord(){

    }

    public SpecialFrequencyRecord(int vesselNumber, int routeNumber, double distance, Date startTime, Date endTime, int status){
        this.vesselNumber = vesselNumber;
        this.routeNumber = routeNumber;
        this.distanz = distance;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public int getVesselNumber(){return vesselNumber;}

    public int getRouteNumber(){return routeNumber;}

    public double getDistanz() {return distanz;}

    public Date getStartTime(){return startTime;}

    public Date getEndTime(){return endTime;}

    public int getStatus(){return status;}

    public void setVesselNumber(int vesselNumber){this.vesselNumber = vesselNumber;}

    public void setRouteNumber(int routeNumber){this.routeNumber = routeNumber;}

    public void setDistanz(double distanz){this.distanz = distanz;}

    public void setStartTime(Date startTime){this.startTime = startTime;}

    public void setEndTime(Date endTime){this.endTime = endTime;}

    public void setStatus(int status){this.status = status;}
}
