package com.bls.bls_boats.unit_tests;

import com.bls.bls_boats.main.Entity.EnumStatus;
import com.bls.bls_boats.main.Settings.EnumPreferences;

import org.junit.Assert;
import org.junit.Test;

public class EnumTests {

    @Test
    public void checkPreferencesStrings(){
        Assert.assertEquals(EnumPreferences.BEARERTOKEN.toString(), "bearerToken");
        Assert.assertEquals(EnumPreferences.GETENDPOINT.toString(), "getEndpoint");
        Assert.assertEquals(EnumPreferences.POSTENDPOINT.toString(), "postEndpoint");
        Assert.assertEquals(EnumPreferences.NUMBEROFDAYS.toString(), "numberOfDays");
        Assert.assertEquals(EnumPreferences.VESSELID.toString(), "vesselid");
        Assert.assertEquals(EnumPreferences.POSITIONOFVESSEL.toString(), "position");
    }

    @Test
    public void checkStatusInt(){
        Assert.assertEquals(EnumStatus.Übertragen.getStatus(), 1);
        Assert.assertEquals(EnumStatus.Erfasst.getStatus(),0);
    }
}
