package com.bls.bls_boats.main.Entity;

public class Operator {
    public static final String TABLE_NAME = "betreiber";

    public static final String COLUMN_OPERATORNUMBER = "betreibernummer";
    public static final String COLUMN_OPERATORNAME = "betreibername";

    private int operatorNumber;
    private String operatorName;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_OPERATORNUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_OPERATORNAME + " VARCHAR(255))";

    public Operator(){

    }

    public Operator(int operatorNumber, String operatorName){
        this.operatorNumber = operatorNumber;
        this.operatorName = operatorName;
    }

    public int getOperatorNumber(){
        return operatorNumber;
    }

    public String getOperatorName(){
        return operatorName;
    }

    public void setOperatorName(String betreiberName){
        this.operatorName = betreiberName;
    }

    @Override
    public String toString(){
        return this.operatorName;
    }
}
