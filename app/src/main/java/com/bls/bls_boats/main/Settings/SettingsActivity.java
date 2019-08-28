package com.bls.bls_boats.main.Settings;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.bls.bls_boats.main.DB.DBHelper;
import com.bls.bls_boats.main.Entity.Vessel;
import com.bls.bls_boats.main.ExtraJourney.ExtraJourneyActivity;
import com.bls.bls_boats.main.Journey.SpecialJourneyActivity;
import com.bls.bls_boats.main.MenuActivity;
import com.bls.bls_boats.main.R;

import java.util.List;


/**
 * Screen for the Settings
 */
public class SettingsActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private SharedPreferences.Editor editor;
    private EditText numberOfDays;
    private EditText getEndpoint;
    private EditText bearerToken;
    private EditText postEndpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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
                        Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);
                        startActivity(intent);

                    case R.id.start:
                        Intent startIntent = new Intent(SettingsActivity.this, MenuActivity.class);
                        startActivity(startIntent);
                    case R.id.specialJourney:
                        Intent specialIntent = new Intent(SettingsActivity.this, SpecialJourneyActivity.class);
                        startActivity(specialIntent);
                        break;
                    case R.id.extraJourney:
                        Intent extraIntent = new Intent(SettingsActivity.this, ExtraJourneyActivity.class);
                        startActivity(extraIntent);
                        break;
                    default:
                        return true;
                }
                return true;

            }
        });


        Spinner boatListSpinner = (Spinner) findViewById(R.id.boat_lists);
        numberOfDays = (EditText) findViewById(R.id.numberofdays);
        getEndpoint = (EditText) findViewById(R.id.GetEndpoint);
        bearerToken = (EditText) findViewById(R.id.bearerToken);
        postEndpoint = (EditText) findViewById(R.id.PostEndpoint);

        DBHelper db = new DBHelper(this);

        SharedPreferences preferences = getSharedPreferences(EnumPreferences.getBOATPREFERENCES(), 0);
        editor = getSharedPreferences(EnumPreferences.getBOATPREFERENCES(), 0).edit();

        List<Vessel> boats = db.getAllVessels();

        populateSpinner(boatListSpinner, boats);


        int position = preferences.getInt(EnumPreferences.POSITIONOFVESSEL.toString(), 1);
        boatListSpinner.setSelection(position);
        boatListSpinner.setOnItemSelectedListener(selectedListener);


        postEndpoint.setText(preferences.getString(EnumPreferences.POSTENDPOINT.toString(), ""));
        postEndpoint.addTextChangedListener(watcher);

        bearerToken.setText(preferences.getString(EnumPreferences.BEARERTOKEN.toString(), ""));
        bearerToken.addTextChangedListener(watcher);

        getEndpoint.setText(preferences.getString(EnumPreferences.GETENDPOINT.toString(), ""));
        getEndpoint.addTextChangedListener(watcher);

        numberOfDays.setText(String.valueOf(preferences.getInt(EnumPreferences.NUMBEROFDAYS.toString(), 1)));
        numberOfDays.addTextChangedListener(watcher);


    }

    public void populateSpinner(Spinner spinner, List data) {
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }


    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!s.toString().equals("")) {
                if (numberOfDays.getText().hashCode() == s.hashCode()) {
                    editor.putInt(EnumPreferences.NUMBEROFDAYS.toString(), Integer.parseInt(s.toString()));
                    editor.apply();
                }
                if (getEndpoint.getText().hashCode() == s.hashCode()) {
                    editor.putString(EnumPreferences.GETENDPOINT.toString(), s.toString());
                    editor.apply();
                }
                if (postEndpoint.getText().hashCode() == s.hashCode()) {
                    editor.putString(EnumPreferences.POSTENDPOINT.toString(), s.toString());
                    editor.apply();
                }
                if (bearerToken.getText().hashCode() == s.hashCode()) {
                    editor.putString(EnumPreferences.BEARERTOKEN.toString(), s.toString());
                    editor.apply();
                }
            }
        }

    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    private AdapterView.OnItemSelectedListener selectedListener = new AdapterView.OnItemSelectedListener()

    {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                   int position, long id) {
            Vessel item = (Vessel) parentView.getItemAtPosition(position);
            editor.putInt(EnumPreferences.VESSELID.toString(), item.getVesselNumber());
            editor.putInt(EnumPreferences.POSITIONOFVESSEL.toString(), position);
            editor.apply();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
