package com.bls.bls_boats.main.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.bls.bls_boats.main.Entity.EnumStatus;
import com.bls.bls_boats.main.Entity.Operator;
import com.bls.bls_boats.main.Entity.EnumRouteTyp;
import com.bls.bls_boats.main.Entity.Reason;
import com.bls.bls_boats.main.Entity.Route;
import com.bls.bls_boats.main.Entity.SpecialFrequencyRecord;
import com.bls.bls_boats.main.Entity.Station;
import com.bls.bls_boats.main.Entity.Vessel;
import com.bls.bls_boats.main.Entity.SpecialFrequencyRecord;
import com.bls.bls_boats.main.Entity.FrequencyRecord;
import com.bls.bls_boats.main.Entity.RouteStation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Helper class for writing and reading from the SQLite database
 */
public class DBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 15;

    // Database Name
    public static final String DATABASE_NAME = "bls_boats";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Table Operator
        db.execSQL(Operator.CREATE_TABLE);
        // Create Table Vessel
        db.execSQL(Vessel.CREATE_TABLE);
        // Create Table Route
        db.execSQL(Route.CREATE_TABLE);
        // Create Table Station
        db.execSQL(Station.CREATE_TABLE);
        // Create Table Linie_Haltestele
        db.execSQL(RouteStation.CREATE_TABLE);
        // Create Table Frequenzerfassung
        db.execSQL(FrequencyRecord.CREATE_TABLE);
        // Create Table SpecialFrequencyRecord
        db.execSQL(SpecialFrequencyRecord.CREATE_TABLE);
        // Create Table Reason
        db.execSQL(Reason.CREATE_TABLE);
    }

    //Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + SpecialFrequencyRecord.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FrequencyRecord.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RouteStation.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Vessel.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Station.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Route.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Operator.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Reason.TABLE_NAME);

        // Create Tables again
        onCreate(db);
    }

    public void insertOperator(Operator operator) {
        // get writeable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Operator.COLUMN_OPERATORNAME, operator.getOperatorName());

        db.insert(Operator.TABLE_NAME, null, values);

        db.close();
    }

    public void insertVessel(Vessel vessel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Vessel.COLUMN_OPERATORNUMBER, vessel.getOperatorNumber());
        values.put(Vessel.COLUMN_VESSELNAME, vessel.getVesselName());

        db.insert(Vessel.TABLE_NAME, null, values);

        db.close();
    }

    public void insertRoute(Route route) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Route.COLUMN_OPERATORNUMBER, route.getOperatorNumber());
        values.put(Route.COLUMN_ROUTENUMBER, route.getRouteNumber());
        values.put(Route.COLUMN_ROUTENAME, route.getRouteName());
        values.put(Route.COLUMN_ROUTETYP, route.getRouteTyp().getRouteTyp());

        db.insert(Route.TABLE_NAME, null, values);

        db.close();
    }

    public void insertStation(Station station) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Station.COLUMN_STATIONNAME, station.getStationName());
        values.put(Station.COLUMN_STATIONNUMBER, station.getStationNumber());

        db.insert(Station.TABLE_NAME, null, values);

        db.close();
    }

    public void insertRouteStation(RouteStation routeStation) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(RouteStation.COLUMN_ROUTENUMBER, routeStation.getRouteNumber());
        values.put(RouteStation.COLUMN_STATIONNUMBER, routeStation.getStationNumber());

        db.insert(RouteStation.TABLE_NAME, null, values);

        db.close();
    }

    public void insertFrequencyRecord(FrequencyRecord frequencyRecord) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        values.put(FrequencyRecord.COLUMN_FREQUENCYRECORDGROUPNUMBER, frequencyRecord.getFrequencyRecordGroupNumber());
        values.put(FrequencyRecord.COLUMN_VESSELNUMBER, frequencyRecord.getVesselNumber());
        values.put(FrequencyRecord.COLUMN_ROUTENUMBER, frequencyRecord.getRouteNumber());
        values.put(FrequencyRecord.COLUMN_STATIONNUMBER, frequencyRecord.getStationNumber());
        values.put(FrequencyRecord.COLUMN_ARRIVALTIME, dateFormat.format(frequencyRecord.getArrivalTime()));
        values.put(FrequencyRecord.COLUMN_DEPARTUETIME, dateFormat.format(frequencyRecord.getDepartueTime()));
        values.put(FrequencyRecord.COLUMN_BOARDINGCOUNT, frequencyRecord.getBoardingCount());
        values.put(FrequencyRecord.COLUMN_DEBOARDINGCOUNT, frequencyRecord.getDeboardingCount());
        values.put(FrequencyRecord.COLUMN_REASON, frequencyRecord.getReason());
        values.put(FrequencyRecord.COLUMN_NOTOPERATED, frequencyRecord.getNotOperated());

        db.insert(FrequencyRecord.TABLE_NAME, null, values);

        db.close();
    }

    public void insertSpecialFrequencyRecord(SpecialFrequencyRecord specialFrequencyRecord) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        values.put(SpecialFrequencyRecord.COLUMN_VESSELNUMBER, specialFrequencyRecord.getVesselNumber());
        values.put(SpecialFrequencyRecord.COLUMN_ROUTENUMBER, specialFrequencyRecord.getRouteNumber());
        values.put(SpecialFrequencyRecord.COLUMN_DISTANCE, specialFrequencyRecord.getDistanz());
        values.put(SpecialFrequencyRecord.COLUMN_STARTTIME, dateFormat.format(specialFrequencyRecord.getStartTime()));
        values.put(SpecialFrequencyRecord.COLUMN_ENDTIME, dateFormat.format(specialFrequencyRecord.getEndTime()));

        db.insert(SpecialFrequencyRecord.TABLE_NAME, null, values);

        db.close();

    }

    public void insertReason(Reason reason) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Reason.COLUMN_ID, reason.getId());
        values.put(Reason.COLUMN_NAME, reason.getName());

        db.insert(Reason.TABLE_NAME, null, values);

        db.close();
    }

    public Operator getOperator(int operatorNumber) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Operator.TABLE_NAME,
                new String[]{Operator.COLUMN_OPERATORNUMBER, Operator.COLUMN_OPERATORNAME},
                Operator.COLUMN_OPERATORNUMBER + "=?",
                new String[]{String.valueOf(operatorNumber)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            Operator operator = new Operator(
                    cursor.getInt(cursor.getColumnIndex(Operator.COLUMN_OPERATORNUMBER)),
                    cursor.getString(cursor.getColumnIndex(Operator.COLUMN_OPERATORNAME)));

            cursor.close();

            return operator;
        }

        return null;
    }

    public Vessel getVessel(int vesselNumber) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Vessel.TABLE_NAME,
                new String[]{Vessel.COLUMN_VESSELNUMBER, Vessel.COLUMN_OPERATORNUMBER, Vessel.COLUMN_VESSELNAME},
                Vessel.COLUMN_VESSELNUMBER + "=?",
                new String[]{String.valueOf(vesselNumber)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            Vessel vessel = new Vessel(
                    cursor.getInt(cursor.getColumnIndex(Vessel.COLUMN_VESSELNUMBER)),
                    cursor.getInt(cursor.getColumnIndex(Vessel.COLUMN_OPERATORNUMBER)),
                    cursor.getString(cursor.getColumnIndex(Vessel.COLUMN_VESSELNAME)));

            cursor.close();

            return vessel;
        }
        return null;
    }

    public Station getStation(int stationNumber) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Station.TABLE_NAME,
                new String[]{Station.COLUMN_STATIONNUMBER, Station.COLUMN_STATIONNAME},
                Station.COLUMN_STATIONNUMBER + "=?",
                new String[]{String.valueOf(stationNumber)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            Station station = new Station(
                    cursor.getInt(cursor.getColumnIndex(Station.COLUMN_STATIONNUMBER)),
                    cursor.getString(cursor.getColumnIndex(Station.COLUMN_STATIONNAME)));

            cursor.close();

            return station;
        }
        return null;
    }

    public Route getRoute(int routeNumber) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Route.TABLE_NAME,
                new String[]{Route.COLUMN_ROUTENUMBER, Route.COLUMN_OPERATORNUMBER, Route.COLUMN_ROUTENAME, Route.COLUMN_ROUTETYP},
                Route.COLUMN_ROUTENUMBER + "=?",
                new String[]{String.valueOf(routeNumber)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            Route route = new Route(
                    cursor.getInt(cursor.getColumnIndex(Route.COLUMN_ROUTENUMBER)),
                    cursor.getInt(cursor.getColumnIndex(Route.COLUMN_OPERATORNUMBER)),
                    cursor.getString(cursor.getColumnIndex(Route.COLUMN_ROUTENAME)),
                    EnumRouteTyp.getEnumByString(cursor.getString(cursor.getColumnIndex(Route.COLUMN_ROUTETYP))));

            cursor.close();

            return route;
        }
        return null;
    }

    public List<Reason> getReasons(int id) {
        List<Reason> returnList = new ArrayList<>();

        //SELECT QUERY
        String selectQuery = "SELECT * FORM " + Reason.TABLE_NAME + "WHERE" + Reason.COLUMN_ID + " = " + String.valueOf(id);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                Reason reason = new Reason(cursor.getInt(cursor.getColumnIndex(Reason.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Reason.COLUMN_NAME)));
                returnList.add(reason);
            } while (cursor.moveToNext());
        }
        db.close();
        return returnList;
    }

    public List<FrequencyRecord> getFrequencyRecord(int routeNumber, int vesselNumber) {
        List<FrequencyRecord> returnList = new ArrayList<FrequencyRecord>();

        //SELECT QUERY
        String selectQuery = "SELECT "+
                FrequencyRecord.TABLE_NAME + "."+ FrequencyRecord.COLUMN_FREQUENCYRECORDGROUPNUMBER +", "+
                FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_VESSELNUMBER + ", "+
                FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_ROUTENUMBER + ", "+
                FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_STATIONNUMBER + ", "+
                FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_ARRIVALTIME + ", "+
                FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_DEPARTUETIME + ", "+
                FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_BOARDINGCOUNT + ", "+
                FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_DEBOARDINGCOUNT + ", " +
                FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_STATUS + ", "+
                FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_REASON + ", "+
                FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_NOTOPERATED + ", "+
                Station.TABLE_NAME + "." + Station.COLUMN_STATIONNAME +", "+
                Reason.TABLE_NAME + "." + Reason.COLUMN_NAME+
                " FROM " + FrequencyRecord.TABLE_NAME +
                " LEFT JOIN " +
                Station.TABLE_NAME +
                " ON " +
                FrequencyRecord.TABLE_NAME + "."+ FrequencyRecord.COLUMN_STATIONNUMBER + "=" + Station.TABLE_NAME+"."+Station.COLUMN_STATIONNUMBER +
                " LEFT JOIN " +
                Reason.TABLE_NAME +
                " ON " +
                FrequencyRecord.TABLE_NAME + "." + FrequencyRecord.COLUMN_REASON + "=" +Reason.TABLE_NAME +"."+Reason.COLUMN_ID +
                " WHERE " + FrequencyRecord.TABLE_NAME + "."+ FrequencyRecord.COLUMN_ROUTENUMBER + " = " + String.valueOf(routeNumber) +
                " AND " +FrequencyRecord.TABLE_NAME + "."+ FrequencyRecord.COLUMN_VESSELNUMBER + " = " + String.valueOf(vesselNumber);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                FrequencyRecord requencyRecord = new FrequencyRecord(
                        cursor.getInt(cursor.getColumnIndex(FrequencyRecord.COLUMN_FREQUENCYRECORDGROUPNUMBER)),
                        cursor.getInt(cursor.getColumnIndex(FrequencyRecord.COLUMN_VESSELNUMBER)),
                        cursor.getInt(cursor.getColumnIndex(FrequencyRecord.COLUMN_ROUTENUMBER)),
                        cursor.getInt(cursor.getColumnIndex(FrequencyRecord.COLUMN_STATIONNUMBER)),
                        ParseStringToDate(cursor.getString(cursor.getColumnIndex(FrequencyRecord.COLUMN_ARRIVALTIME))),
                        ParseStringToDate(cursor.getString(cursor.getColumnIndex(FrequencyRecord.COLUMN_DEPARTUETIME))),
                        cursor.getInt(cursor.getColumnIndex(FrequencyRecord.COLUMN_BOARDINGCOUNT)),
                        cursor.getInt(cursor.getColumnIndex(FrequencyRecord.COLUMN_DEBOARDINGCOUNT)),
                        cursor.getInt(cursor.getColumnIndex(FrequencyRecord.COLUMN_STATUS)),
                        cursor.getInt(cursor.getColumnIndex(FrequencyRecord.COLUMN_REASON)),
                        (cursor.getInt(cursor.getColumnIndex(FrequencyRecord.COLUMN_NOTOPERATED)) == 1));
                requencyRecord.setReasonText(cursor.getString(cursor.getColumnIndex(Reason.COLUMN_NAME)));
                requencyRecord.setStationName(cursor.getString(cursor.getColumnIndex(Station.COLUMN_STATIONNAME)));
                returnList.add(requencyRecord);
            } while (cursor.moveToNext());
        }

        db.close();

        return returnList;
    }

    public List<SpecialFrequencyRecord> getFrequencyRecordSpecial(int routeNumber, int vesselNumber) {
        List<SpecialFrequencyRecord> returnList = new ArrayList<SpecialFrequencyRecord>();

        //SELECT QUERY
        String selectQuery = "SELECT * FROM " + SpecialFrequencyRecord.TABLE_NAME
                + " WHERE " + SpecialFrequencyRecord.COLUMN_ROUTENUMBER + " = " + String.valueOf(routeNumber)
                + " AND " + SpecialFrequencyRecord.COLUMN_VESSELNUMBER + " = " + String.valueOf(vesselNumber);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SpecialFrequencyRecord specialFrequencyRecord = new SpecialFrequencyRecord(
                        cursor.getInt(cursor.getColumnIndex(SpecialFrequencyRecord.COLUMN_VESSELNUMBER)),
                        cursor.getInt(cursor.getColumnIndex(SpecialFrequencyRecord.COLUMN_ROUTENUMBER)),
                        cursor.getDouble(cursor.getColumnIndex(SpecialFrequencyRecord.COLUMN_DISTANCE)),
                        ParseStringToDate(cursor.getString(cursor.getColumnIndex(SpecialFrequencyRecord.COLUMN_STARTTIME))),
                        ParseStringToDate(cursor.getString(cursor.getColumnIndex(SpecialFrequencyRecord.COLUMN_ENDTIME))),
                        cursor.getInt(cursor.getColumnIndex(SpecialFrequencyRecord.COLUMN_STATUS))
                );
                returnList.add(specialFrequencyRecord);
            } while (cursor.moveToNext());
        }

        db.close();

        return returnList;
    }

    public List<Vessel> getAllVessels() {
        List<Vessel> returnList = new ArrayList<Vessel>();

        //SELECT QUERY
        String selectQuery = "SELECT * FROM " + Vessel.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Vessel vessel = new Vessel(
                        cursor.getInt(cursor.getColumnIndex(Vessel.COLUMN_VESSELNUMBER)),
                        cursor.getInt(cursor.getColumnIndex(Vessel.COLUMN_OPERATORNUMBER)),
                        cursor.getString(cursor.getColumnIndex(Vessel.COLUMN_VESSELNAME))
                );
                returnList.add(vessel);
            } while (cursor.moveToNext());
        }
        db.close();

        return returnList;
    }

    public List<Reason> getAllReasons() {
        List<Reason> returnList = new ArrayList<>();

        //SELECT QUERY
        String selectQuery = "SELECT * FROM " + Reason.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Reason reason = new Reason(
                        cursor.getInt(cursor.getColumnIndex(Reason.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Reason.COLUMN_NAME))
                );
                returnList.add(reason);
            } while (cursor.moveToNext());
        }
        db.close();
        return returnList;
    }

    public List<Route> getAllRoutes() {
        List<Route> returnList = new ArrayList<Route>();

        //SELECT QUERY
        String selectQuery = "SELECT * FROM " + Route.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Route route = new Route(
                        cursor.getInt(cursor.getColumnIndex(Route.COLUMN_ROUTENUMBER)),
                        cursor.getInt(cursor.getColumnIndex(Route.COLUMN_OPERATORNUMBER)),
                        cursor.getString(cursor.getColumnIndex(Route.COLUMN_ROUTENAME)),
                        EnumRouteTyp.getEnumByString(cursor.getString(cursor.getColumnIndex(Route.COLUMN_ROUTETYP)))
                );
                returnList.add(route);
            } while (cursor.moveToNext());
        }

        db.close();

        return returnList;
    }

    public List<Station> getAllStations() {
        List<Station> returnList = new ArrayList<Station>();

        //SELECT QUERY
        String selectQuery = "SELECT * FROM " + Station.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Station station = new Station(
                        cursor.getInt(cursor.getColumnIndex(Station.COLUMN_STATIONNUMBER)),
                        cursor.getString(cursor.getColumnIndex(Station.COLUMN_STATIONNAME))
                );
                returnList.add(station);
            } while (cursor.moveToNext());
        }

        db.close();

        return returnList;
    }

    public List<Station> getStationsFromRoute(int routeNumber) {
        List<Station> returnList = new ArrayList<Station>();

        //SELECT QUERY
        String selectQuery = "SELECT " + Station.TABLE_NAME + "." + Station.COLUMN_STATIONNUMBER + ", " + Station.TABLE_NAME + "." + Station.COLUMN_STATIONNAME
                + " FROM " + Station.TABLE_NAME + " INNER JOIN " + RouteStation.TABLE_NAME
                + " ON " + Station.TABLE_NAME + "." + Station.COLUMN_STATIONNUMBER + " = " + RouteStation.TABLE_NAME + "." + RouteStation.COLUMN_STATIONNUMBER
                + " WHERE " + RouteStation.TABLE_NAME + "." + RouteStation.COLUMN_ROUTENUMBER + "=" + String.valueOf(routeNumber);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Station station = new Station(
                        cursor.getInt(cursor.getColumnIndex(Station.COLUMN_STATIONNUMBER)),
                        cursor.getString(cursor.getColumnIndex(Station.COLUMN_STATIONNAME))
                );
                returnList.add(station);
            } while (cursor.moveToNext());
        }

        db.close();

        return returnList;
    }

    public void deleteFrequencyOlderThan(int days) {
        //DELETE QUERY Frequenzerfassung & Frequenzerfassungspezial
        String deleteFrequencyRecord = "DELETE FROM " + FrequencyRecord.TABLE_NAME + " WHERE " + FrequencyRecord.COLUMN_CREATEDATE + "<=date('now','-" + String.valueOf(days) + " day')";
        String deleteSpecialFrequencyRecord = "DELETE FROM " + SpecialFrequencyRecord.TABLE_NAME + " WHERE " + SpecialFrequencyRecord.COLUMN_CREATEDATE + "<=date('now','-" + String.valueOf(days) + " day')";

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(deleteFrequencyRecord);
        db.execSQL(deleteSpecialFrequencyRecord);

        db.close();
    }

    public int getMaxFrequencyRecourdGroupNumber() {
        int returnMaxNumber = 0;
        //SELECT MAX FREQUENZERFASSUNGGRUPPENUMMER
        String selectQuery = "SELECT IFNULL(MAX(" + FrequencyRecord.COLUMN_FREQUENCYRECORDGROUPNUMBER + "),1) FROM " + FrequencyRecord.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            returnMaxNumber = cursor.getInt(0);
        }

        db.close();

        return returnMaxNumber;
    }

    public Date ParseStringToDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public List<FrequencyRecord> getFrequencyFromGroupNr(int groupNumber){
        List<FrequencyRecord> returnList = new ArrayList<FrequencyRecord>();

        //SELECT QUERY
        String selectQuery = "SELECT "+
                FrequencyRecord.TABLE_NAME + "."+ FrequencyRecord.COLUMN_FREQUENCYRECORDGROUPNUMBER +", "+
                FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_VESSELNUMBER + ", "+
                FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_ROUTENUMBER + ", "+
                FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_STATIONNUMBER + ", "+
                FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_ARRIVALTIME + ", "+
                FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_DEPARTUETIME + ", "+
                FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_BOARDINGCOUNT + ", "+
                FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_DEBOARDINGCOUNT + ", " +
                FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_STATUS + ", "+
                FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_REASON + ", "+
                FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_NOTOPERATED + ", "+
                Station.TABLE_NAME + "." + Station.COLUMN_STATIONNAME +", "+
                Reason.TABLE_NAME + "." + Reason.COLUMN_NAME+
                " FROM " + FrequencyRecord.TABLE_NAME +
                " LEFT JOIN "+
                Station.TABLE_NAME +
                " ON " +
                FrequencyRecord.TABLE_NAME + "."+ FrequencyRecord.COLUMN_STATIONNUMBER + "=" + Station.TABLE_NAME+"."+Station.COLUMN_STATIONNUMBER +
                " LEFT JOIN "+
                Reason.TABLE_NAME +
                " ON " +
                FrequencyRecord.TABLE_NAME + "." + FrequencyRecord.COLUMN_REASON + "=" +Reason.TABLE_NAME +"."+Reason.COLUMN_ID +
                " WHERE " + FrequencyRecord.TABLE_NAME + "."+FrequencyRecord.COLUMN_FREQUENCYRECORDGROUPNUMBER +" = "+ String.valueOf(groupNumber);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                FrequencyRecord frequencyRecord = new FrequencyRecord(
                        cursor.getInt(cursor.getColumnIndex(FrequencyRecord.COLUMN_FREQUENCYRECORDGROUPNUMBER)),
                        cursor.getInt(cursor.getColumnIndex(FrequencyRecord.COLUMN_VESSELNUMBER)),
                        cursor.getInt(cursor.getColumnIndex(FrequencyRecord.COLUMN_ROUTENUMBER)),
                        cursor.getInt(cursor.getColumnIndex(FrequencyRecord.COLUMN_STATIONNUMBER)),
                        ParseStringToDate(cursor.getString(cursor.getColumnIndex(FrequencyRecord.COLUMN_ARRIVALTIME))),
                        ParseStringToDate(cursor.getString(cursor.getColumnIndex(FrequencyRecord.COLUMN_DEPARTUETIME))),
                        cursor.getInt(cursor.getColumnIndex(FrequencyRecord.COLUMN_BOARDINGCOUNT)),
                        cursor.getInt(cursor.getColumnIndex(FrequencyRecord.COLUMN_DEBOARDINGCOUNT)),
                        cursor.getInt(cursor.getColumnIndex(FrequencyRecord.COLUMN_STATUS)),
                        cursor.getInt(cursor.getColumnIndex(FrequencyRecord.COLUMN_REASON)),
                        (cursor.getInt(cursor.getColumnIndex(FrequencyRecord.COLUMN_NOTOPERATED)) == 1));
                frequencyRecord.setReasonText(cursor.getString(cursor.getColumnIndex(Reason.COLUMN_NAME)));
                frequencyRecord.setStationName(cursor.getString(cursor.getColumnIndex(Station.COLUMN_STATIONNAME)));
                returnList.add(frequencyRecord);
            } while (cursor.moveToNext());
        }

        db.close();

        return returnList;
    }

    public void deleteFrequencyRecord(int groupNumber, int stationNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FrequencyRecord.TABLE_NAME,FrequencyRecord.COLUMN_FREQUENCYRECORDGROUPNUMBER +"=? and "+FrequencyRecord.COLUMN_STATIONNUMBER +"=?",new String[]{String.valueOf(groupNumber),String.valueOf(stationNumber)});

        db.close();
    }

    public void updateFrequencyRecord(FrequencyRecord frequencyRecord){
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        values.put(FrequencyRecord.COLUMN_FREQUENCYRECORDGROUPNUMBER, frequencyRecord.getFrequencyRecordGroupNumber());
        values.put(FrequencyRecord.COLUMN_VESSELNUMBER, frequencyRecord.getVesselNumber());
        values.put(FrequencyRecord.COLUMN_ROUTENUMBER, frequencyRecord.getRouteNumber());
        values.put(FrequencyRecord.COLUMN_STATIONNUMBER, frequencyRecord.getStationNumber());
        values.put(FrequencyRecord.COLUMN_ARRIVALTIME, dateFormat.format(frequencyRecord.getArrivalTime()));
        values.put(FrequencyRecord.COLUMN_DEPARTUETIME, dateFormat.format(frequencyRecord.getDepartueTime()));
        values.put(FrequencyRecord.COLUMN_BOARDINGCOUNT, frequencyRecord.getBoardingCount());
        values.put(FrequencyRecord.COLUMN_DEBOARDINGCOUNT, frequencyRecord.getDeboardingCount());
        values.put(FrequencyRecord.COLUMN_REASON, frequencyRecord.getReason());
        values.put(FrequencyRecord.COLUMN_NOTOPERATED, frequencyRecord.getNotOperated());

        SQLiteDatabase db = this.getWritableDatabase();

        db.update(FrequencyRecord.TABLE_NAME,values,FrequencyRecord.COLUMN_FREQUENCYRECORDGROUPNUMBER +"=? and "+FrequencyRecord.COLUMN_STATIONNUMBER +"=?",new String[]{String.valueOf(frequencyRecord.getFrequencyRecordGroupNumber()),String.valueOf(frequencyRecord.getStationNumber())});

        db.close();
    }

    public void setFrequencyRecordsStatus(List<FrequencyRecord> frequencyRecords, EnumStatus status) {
        for (FrequencyRecord record : frequencyRecords) {
            record.setStatus(status.getStatus());
            updateFrequencyRecord(record);
        }
    }
}
