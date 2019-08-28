package com.bls.bls_boats.main.Settings;


/**
 * Enum for SharedPreferences Access
 */
public enum EnumPreferences {
    POSITIONOFVESSEL("position"),
    POSTENDPOINT("postEndpoint"),
    GETENDPOINT("getEndpoint"),
    BEARERTOKEN("bearerToken"),
    NUMBEROFDAYS("numberOfDays"),
    VESSELID("vesselid");


    private static final String BOATPREFERENCES = "boat_pref";
    private final String preferencesValue;

    EnumPreferences(String preferencesValue) {
        this.preferencesValue = preferencesValue;
    }

    @Override
    public String toString() {
        return preferencesValue;
    }


    public static String getBOATPREFERENCES() {
        return BOATPREFERENCES;
    }
}
