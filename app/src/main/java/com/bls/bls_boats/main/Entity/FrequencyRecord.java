package com.bls.bls_boats.main.Entity;

import java.util.Date;

public class FrequencyRecord {
    public static final String TABLE_NAME = "frequenzerfassung";

    public static final String COLUMN_FREQUENCYRECORDNUMBER = "frequenzerfassungnummer";
    public static final String COLUMN_FREQUENCYRECORDGROUPNUMBER = "frequenzerfassunggruppenummer";
    public static final String COLUMN_VESSELNUMBER = "fahrzeugnummer";
    public static final String COLUMN_ROUTENUMBER = "linienummer";
    public static final String COLUMN_STATIONNUMBER = "haltestellenummer";
    public static final String COLUMN_ARRIVALTIME = "arrivalTime";
    public static final String COLUMN_DEPARTUETIME = "departueTime";
    public static final String COLUMN_BOARDINGCOUNT = "anzahlzugestiegen";
    public static final String COLUMN_DEBOARDINGCOUNT = "anzahlausgestiegen";
    public static final String COLUMN_CREATEDATE = "createDate";
    public static final String COLUMN_REASON = "reason";
    public static final String COLUMN_NOTOPERATED = "notOperated";
    public static final String COLUMN_STATUS = "status";


    private int frequencyRecordNumber;
    private int frequencyRecordGroupNumber;
    private int vesselNumber;
    private int routeNumber;
    private int stationNumber;
    private String stationName;
    private Date arrivalTime;
    private Date departueTime;
    private int boardingCount;
    private int deboardingCount;
    private Date createDate;
    private int reason;
    private String reasonText;
    private boolean notOperated;
    private int status;

    // Create Table SQL Query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_FREQUENCYRECORDNUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_FREQUENCYRECORDGROUPNUMBER + " INTEGER,"
                    + COLUMN_VESSELNUMBER + " INTEGER,"
                    + COLUMN_ROUTENUMBER + " INTEGER,"
                    + COLUMN_STATIONNUMBER + " INTEGER,"
                    + COLUMN_ARRIVALTIME + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + COLUMN_DEPARTUETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + COLUMN_BOARDINGCOUNT + " INTEGER,"
                    + COLUMN_DEBOARDINGCOUNT + " INTEGER,"
                    + COLUMN_REASON + " INTEGER,"
                    + COLUMN_NOTOPERATED + " BOOLEAN DEFAULT 0,"
                    + COLUMN_CREATEDATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + COLUMN_STATUS + " INTEGER DEFAULT 0,"
                    + " FOREIGN KEY (" + COLUMN_REASON + ") REFERENCES " + Reason.TABLE_NAME + "(" + Reason.COLUMN_ID + "),"
                    + " FOREIGN KEY (" + COLUMN_VESSELNUMBER + ") REFERENCES " + Vessel.TABLE_NAME + "(" + Vessel.COLUMN_VESSELNUMBER + "),"
                    + " FOREIGN KEY (" + COLUMN_ROUTENUMBER + ") REFERENCES " + Route.TABLE_NAME + "(" + Route.COLUMN_ROUTENUMBER + "),"
                    + " FOREIGN KEY (" + COLUMN_STATIONNUMBER + ") REFERENCES " + Station.TABLE_NAME + "(" + Station.COLUMN_STATIONNUMBER + "));";

    public FrequencyRecord() {

    }

    public FrequencyRecord(int frequencyRecordGroupNumber, int vesselNumber, int routeNumber, int stationNumber, Date arrivalTime, Date departueTime, int boardingCount, int deboardingCount, int status, int reason, boolean notOperated) {
        this.frequencyRecordGroupNumber = frequencyRecordGroupNumber;
        this.vesselNumber = vesselNumber;
        this.routeNumber = routeNumber;
        this.stationNumber = stationNumber;
        this.arrivalTime = arrivalTime;
        this.departueTime = departueTime;
        this.boardingCount = boardingCount;
        this.deboardingCount = deboardingCount;
        this.status = status;
        this.reason = reason;
        this.notOperated = notOperated;
    }

    public int getFrequencyRecordNumber() {
        return frequencyRecordNumber;
    }

    public String getStationName(){return stationName;}

    public String getReasonText(){return reasonText;}

    public int getVesselNumber() {
        return vesselNumber;
    }

    public int getRouteNumber() {
        return routeNumber;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public int getBoardingCount() {
        return boardingCount;
    }

    public int getDeboardingCount() {
        return deboardingCount;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public Date getDepartueTime() {
        return departueTime;
    }

    public int getStatus() {
        return status;
    }

    public int getReason() {
        return reason;
    }

    public boolean getNotOperated() {
        return notOperated;
    }

    public int getFrequencyRecordGroupNumber() {
        return frequencyRecordGroupNumber;
    }

    public void setFrequencyRecordNumber(int frequenzerfassungNummer) {
        this.frequencyRecordNumber = frequenzerfassungNummer;
    }

    public void setVesselNumber(int vesselNumber) {
        this.vesselNumber = vesselNumber;
    }

    public void setRouteNumber(int routeNumber) {
        this.routeNumber = routeNumber;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDepartueTime(Date departueTime) {
        this.departueTime = departueTime;
    }

    public void setBoardingCount(int boardingCount) {
        this.boardingCount = boardingCount;
    }

    public void setStationName(String stationName){
        this.stationName = stationName;
    }

    public void setReasonText(String reasonText){
        this.reasonText = reasonText;
    }

    public void setDeboardingCount(int deboardingCount) {
        this.deboardingCount = deboardingCount;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }

    public void setNotOperated(boolean notOperated) {
        this.notOperated = notOperated;
    }

    public void setFrequencyRecordGroupNumber(int frequencyRecordGroupNumber) {
        this.frequencyRecordGroupNumber = frequencyRecordGroupNumber;
    }

    public void setCreateDate(Date date) {
        this.createDate = date;
    }

    public Date getCreateDate() {
        return this.createDate;
    }


    @Override
    public String toString() {
        return "Vessel:" + this.vesselNumber + " Route:" + this.routeNumber + " HaltestelleNr:" + this.stationNumber + " AnzahlEin:" + this.boardingCount + " AnzahlAus:" + this.deboardingCount;
    }
}
