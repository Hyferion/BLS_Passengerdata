package com.bls.bls_boats.main.Entity;

public class Vessel {
    public static final String TABLE_NAME = "fahrzeug";

    public static final String COLUMN_VESSELNUMBER = "fahrzeugnummer";
    public static final String COLUMN_OPERATORNUMBER = "betreibernummer";
    public static final String COLUMN_VESSELNAME = "fahrzeugname";

    private int vesselNumber;
    private int operatorNumber;
    private String vesselName;

    //Create Table SQL Query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_VESSELNUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_OPERATORNUMBER + " INTEGER,"
            + COLUMN_VESSELNAME + " VARCHAR(255),"
            + " FOREIGN KEY ("+ COLUMN_OPERATORNUMBER +") REFERENCES "+Operator.TABLE_NAME+"("+Operator.COLUMN_OPERATORNUMBER +"));";

    public Vessel(){

    }

    public Vessel(int vesselNumber, int operatorNumber, String vesselName){
        this.vesselNumber = vesselNumber;
        this.operatorNumber = operatorNumber;
        this.vesselName = vesselName;
    }

    public int getVesselNumber(){
        return vesselNumber;
    }

    public int getOperatorNumber(){
        return operatorNumber;
    }

    public String getVesselName(){
        return vesselName;
    }

    public void setOperatorNumber(int operatorNumber){
        this.operatorNumber = operatorNumber;
    }

    public void setVesselName(String vesselName){
        this.vesselName = vesselName;
    }

    @Override
    public String toString(){
        return this.vesselName;
    }
}
