package com.bls.bls_boats.main.Entity;

public class Route {
    public static final String TABLE_NAME = "linie";

    public static final String COLUMN_ROUTENUMBER = "linienummer";
    public static final String COLUMN_OPERATORNUMBER = "betreibernummer";
    public static final String COLUMN_ROUTENAME = "liniename";
    public static final String COLUMN_ROUTETYP = "linietyp";

    private int routeNumber;
    private int operatorNumber;
    private String routeName;
    private EnumRouteTyp routeTyp;


    //Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ROUTENUMBER + " INTEGER PRIMARY KEY,"
            + COLUMN_OPERATORNUMBER +" INTEGER,"
            + COLUMN_ROUTENAME + " VARCHAR(255),"
            + COLUMN_ROUTETYP + " VARCHAR(255),"
            + " FOREIGN KEY ("+ COLUMN_OPERATORNUMBER +") REFERENCES "+Operator.TABLE_NAME+"("+Operator.COLUMN_OPERATORNUMBER +"));";

    public Route(){

    }

    public Route(int routeNumber, int operatorNumber, String routeName, EnumRouteTyp routeType){
        this.routeNumber = routeNumber;
        this.operatorNumber = operatorNumber;
        this.routeName = routeName;
        this.routeTyp = routeType;
    }

    public int getRouteNumber(){
        return routeNumber;
    }

    public int getOperatorNumber(){
        return operatorNumber;
    }

    public String getRouteName(){
        return routeName;
    }

    public EnumRouteTyp getRouteTyp(){return routeTyp;}

    public void setRouteNumber(int routeNumber){
        this.routeNumber = routeNumber;
    }

    public void setOperatorNumber(int operatorNumber){
        this.operatorNumber = operatorNumber;
    }

    public void setRouteName(String routeName){
        this.routeName = routeName;
    }

    public void setRouteTyp(EnumRouteTyp routeTyp){this.routeTyp = routeTyp;}

    @Override
    public String toString(){
        return this.routeName;
    }

}
