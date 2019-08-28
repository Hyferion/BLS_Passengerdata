package com.bls.bls_boats.main.Journey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.bls.bls_boats.main.DB.DBHelper;
import com.bls.bls_boats.main.Entity.Route;
import com.bls.bls_boats.main.Entity.Vessel;
import com.bls.bls_boats.main.ExtraJourney.ExtraJourneyActivity;
import com.bls.bls_boats.main.MenuActivity;
import com.bls.bls_boats.main.R;
import com.bls.bls_boats.main.Settings.EnumPreferences;
import com.bls.bls_boats.main.Settings.SettingsActivity;

import java.util.List;

/**
 * Entrypoint for starting a SpecialJourney Activity, differs from the Standardactivity in terms of vessel selection
 * and route selection
 */
public class SpecialJourneyActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    private DBHelper db;
    private SharedPreferences preferences;
    private Spinner routeSpinner;
    private Spinner vesselSpinner;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_joruney);

        db = new DBHelper(this);

        preferences = getSharedPreferences(EnumPreferences.getBOATPREFERENCES(), 0);
        editor = getSharedPreferences(EnumPreferences.getBOATPREFERENCES(), 0).edit();

        Button startBtn = (Button) findViewById(R.id.save_btn2);
        routeSpinner = (Spinner) findViewById(R.id.linie_spinner_start);
        vesselSpinner = (Spinner) findViewById(R.id.boatSpinner);

        populateSpinners(vesselSpinner, db.getAllVessels());
        populateSpinners(routeSpinner, db.getAllRoutes());

        startBtn.setOnClickListener(startListener);
        vesselSpinner.setOnItemSelectedListener(selectedListener);

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
                        Intent intent = new Intent(SpecialJourneyActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.start:
                        Intent startIntent = new Intent(SpecialJourneyActivity.this, MenuActivity.class);
                        startActivity(startIntent);
                        break;
                    case R.id.specialJourney:
                        Intent specialIntent = new Intent(SpecialJourneyActivity.this, MenuActivity.class);
                        startActivity(specialIntent);
                        break;
                    case R.id.extraJourney:
                        Intent extraIntent = new Intent(SpecialJourneyActivity.this, ExtraJourneyActivity.class);
                        startActivity(extraIntent);
                        break;

                    default:
                        return true;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }


    private View.OnClickListener startListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startSpecialjourney();
        }
    };


    private void startSpecialjourney() {
        Intent intent = new Intent(SpecialJourneyActivity.this, JourneyActivity.class);
        intent.putExtra("fahrt", "Special");
        Route route = (Route) routeSpinner.getSelectedItem();
        intent.putExtra(StationFragment.ROUTE, route.getRouteNumber());
        intent.putExtra(StationFragment.VESSEL_ID, preferences.getInt(EnumPreferences.VESSELID.toString(), 1));
        intent.putExtra(StationFragment.GROUP_ID, db.getMaxFrequencyRecourdGroupNumber() + 1);
        startActivity(intent);
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

    public void populateSpinners(Spinner spinner, List data) {
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
}
