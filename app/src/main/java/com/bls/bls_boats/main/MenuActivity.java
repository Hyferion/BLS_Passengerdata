package com.bls.bls_boats.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.bls.bls_boats.main.DB.DBHelper;
import com.bls.bls_boats.main.DB.DBInsertTestData;
import com.bls.bls_boats.main.Entity.EnumStatus;
import com.bls.bls_boats.main.Entity.FrequencyRecord;
import com.bls.bls_boats.main.Entity.Route;
import com.bls.bls_boats.main.Entity.Vessel;
import com.bls.bls_boats.main.ExtraJourney.ExtraJourneyActivity;
import com.bls.bls_boats.main.Journey.JourneyActivity;
import com.bls.bls_boats.main.Journey.SpecialJourneyActivity;
import com.bls.bls_boats.main.Journey.StationFragment;
import com.bls.bls_boats.main.REST.RestHelper;
import com.bls.bls_boats.main.Settings.EnumPreferences;
import com.bls.bls_boats.main.Settings.SettingsActivity;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Main Menu Activity and start screen of the app
 */
public class MenuActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    private Spinner routeSpinner;

    private DBHelper db;

    private SharedPreferences preferences;

    private String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        db = new DBHelper(this);

        preferences = getSharedPreferences(EnumPreferences.getBOATPREFERENCES(), 0);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.settings:
                        Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.start:
                        Intent startIntent = new Intent(MenuActivity.this, MenuActivity.class);
                        startActivity(startIntent);
                        break;
                    case R.id.specialJourney:
                        Intent specialIntent = new Intent(MenuActivity.this, SpecialJourneyActivity.class);
                        startActivity(specialIntent);
                        break;
                    case R.id.extraJourney:
                        Intent extraIntent = new Intent(MenuActivity.this, ExtraJourneyActivity.class);
                        startActivity(extraIntent);
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

        Button startBtn = (Button) findViewById(R.id.save_btn2);
        routeSpinner = (Spinner) findViewById(R.id.linie_spinner_start);

        // Drop DB
        Context context = getApplicationContext();
        context.deleteDatabase(DBHelper.DATABASE_NAME);

        // Insert test data
        DBInsertTestData testData = new DBInsertTestData();
        testData.insertTestData(db);

        //Delete Frequenzen which are older then days from settings
        db.deleteFrequencyOlderThan(preferences.getInt("numberOfDays", 1000));

        // Get all Lines and populate spinner
        List linien = db.getAllRoutes();
        populateSpinners(routeSpinner, linien);

        startBtn.setOnClickListener(startListener);

        //get Schedule from REST-API
        getCurrentSchedule();
        //Find not transmited Records and transmit them
        findNotTransmitedRecordsandTransmit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }


    public void getCurrentSchedule() {
        AsyncTask.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                try {
                    RestHelper restHelper = new RestHelper(new URL(preferences.getString(EnumPreferences.GETENDPOINT.toString(), "")), new URL(preferences.getString(EnumPreferences.POSTENDPOINT.toString(), "")), preferences.getString(EnumPreferences.BEARERTOKEN.toString(), ""), getApplicationContext());
                    restHelper.openConnection();
                    json = restHelper.getRequest();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        writeJsonToDB(json);
    }

    private void writeJsonToDB(String json) {
        //Really Hard to implement if we can't do a proper request
    }


    private View.OnClickListener startListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startStandardjourney();
        }
    };


    private void startStandardjourney() {
        Intent intent = new Intent(MenuActivity.this, JourneyActivity.class);
        intent.putExtra("fahrt", "Standard");
        Route route = (Route) routeSpinner.getSelectedItem();
        intent.putExtra(StationFragment.ROUTE, route.getRouteNumber());
        intent.putExtra(StationFragment.VESSEL_ID, preferences.getInt(EnumPreferences.VESSELID.toString(), 1));
        intent.putExtra(StationFragment.GROUP_ID, db.getMaxFrequencyRecourdGroupNumber() + 1);
        startActivity(intent);
    }


    private void startExtrafahrt() {
        Intent intent = new Intent(MenuActivity.this, ExtraJourneyActivity.class);
        intent.putExtra("fahrt", "Extrafahrt");
        Route route = (Route) routeSpinner.getSelectedItem();
        intent.putExtra(StationFragment.ROUTE, route.getRouteNumber());
        startActivity(intent);
    }

    public void populateSpinners(Spinner spinner, List data) {
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    public void findNotTransmitedRecordsandTransmit() {
        int maxFrequencyRecourdGroupNumber = db.getMaxFrequencyRecourdGroupNumber();

        for (int i = maxFrequencyRecourdGroupNumber; i > 0; i--) {
            List<FrequencyRecord> frequencyRecords = db.getFrequencyFromGroupNr(i);
            if (frequencyRecords.size() > 0) {
                if (frequencyRecords.get(0).getStatus() == EnumStatus.Erfasst.getStatus()) {
                    transmitNotTransmitedRecords(frequencyRecords);
                }
            }
        }
    }

    public void transmitNotTransmitedRecords(final List<FrequencyRecord> frequencyRecords) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    RestHelper restHelper = new RestHelper(new URL(preferences.getString(EnumPreferences.GETENDPOINT.toString(), "")), new URL(preferences.getString(EnumPreferences.POSTENDPOINT.toString(), "")), preferences.getString(EnumPreferences.BEARERTOKEN.toString(), ""), getApplicationContext());
                    restHelper.openConnection();
                    int boatId = frequencyRecords.get(0).getVesselNumber();
                    Vessel vessel = db.getVessel(boatId);
                    String json = restHelper.frequenzErfassungenToJSON(frequencyRecords, vessel.getVesselName());
                    restHelper.postRequest(json);
                    db.setFrequencyRecordsStatus(frequencyRecords, EnumStatus.Übertragen);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
