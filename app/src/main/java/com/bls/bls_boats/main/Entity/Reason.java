package com.bls.bls_boats.main.Entity;

public class Reason {
    public static final String TABLE_NAME = "grund";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";


    private int id;
    private String name;


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_NAME + " VARCHAR(255));";

    public Reason() {

    }

    public Reason(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
