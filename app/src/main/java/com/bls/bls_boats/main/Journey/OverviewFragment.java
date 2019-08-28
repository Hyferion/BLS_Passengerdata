package com.bls.bls_boats.main.Journey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bls.bls_boats.main.DB.DBHelper;
import com.bls.bls_boats.main.Entity.EnumStatus;
import com.bls.bls_boats.main.Entity.Route;
import com.bls.bls_boats.main.Entity.Station;
import com.bls.bls_boats.main.Entity.Vessel;
import com.bls.bls_boats.main.Entity.FrequencyRecord;
import com.bls.bls_boats.main.Journey.StationFragment;
import com.bls.bls_boats.main.MenuActivity;
import com.bls.bls_boats.main.R;
import com.bls.bls_boats.main.REST.RestHelper;
import com.bls.bls_boats.main.Settings.EnumPreferences;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * This is the OverviewFragment which we add at the end of each StandardJourney
 */
public class OverviewFragment extends Fragment {

    private TextView boatText;
    private TextView routeText;
    private TextView totalIn;
    private TextView totalOut;
    private TableLayout tl;
    private DBHelper db;
    private Bundle args;
    private int groupFrequencyNumber;

    private List<FrequencyRecord> frequencyRecords;

    private SharedPreferences sp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_overview, container, false);
        args = getArguments();

        tl = (TableLayout) rootView.findViewById(R.id.tablers);
        boatText = (TextView) rootView.findViewById(R.id.boat_name);
        routeText = (TextView) rootView.findViewById(R.id.linie_name);
        totalIn = (TextView) rootView.findViewById(R.id.total_ein);
        totalOut = (TextView) rootView.findViewById(R.id.total_aus);
        Button finishButton = (Button) rootView.findViewById(R.id.button);

        groupFrequencyNumber = args.getInt(StationFragment.GROUP_ID);
        sp = this.getActivity().getSharedPreferences(EnumPreferences.getBOATPREFERENCES(), 0);

        db = new DBHelper(getActivity());

        finishButton.setOnClickListener(finishListener);

        return rootView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            createOverview();
        } else {
            removeOverview();
        }
    }

    public void createOverview() {
        frequencyRecords = db.getFrequencyFromGroupNr(groupFrequencyNumber);

        int boatId = getBoatFromSettings();

        Vessel vessel = db.getVessel(boatId);
        Route route = db.getRoute(args.getInt(StationFragment.ROUTE_ID));

        boatText.setText(vessel.getVesselName());
        routeText.setText(route.getRouteName());

        insertErfassungeninTable();
    }


    private void insertErfassungeninTable() {
        int total_ein = 0;
        int total_aus = 0;

        for (int i = 0; i < frequencyRecords.size(); i++) {
            FrequencyRecord freq = frequencyRecords.get(i);
            TableRow row = new TableRow(getActivity());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            Station station = db.getStation(freq.getStationNumber());

            TextView halt = new TextView(getActivity());
            halt.setText(station.getStationName());
            halt.setTextSize(30);
            halt.setTextColor(Color.BLACK);
            halt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.4f));

            TextView ein = new TextView(getActivity());
            ein.setText(String.valueOf(freq.getBoardingCount()));
            total_ein = total_ein + freq.getBoardingCount();
            ein.setTextSize(30);
            ein.setTextColor(Color.BLACK);
            ein.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.3f));

            TextView aus = new TextView(getActivity());
            aus.setText(String.valueOf(freq.getDeboardingCount()));
            total_aus = total_aus + freq.getDeboardingCount();
            aus.setTextSize(30);
            aus.setTextColor(Color.BLACK);
            ein.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.3f));

            row.addView(halt);
            row.addView(ein);
            row.addView(aus);
            tl.addView(row, 4 + i);
        }

        totalIn.setText(String.valueOf(total_ein));
        totalIn.setTextSize(30);
        totalOut.setText(String.valueOf(total_aus));
        totalOut.setTextSize(30);
    }

    private int getBoatFromSettings() {
        return sp.getInt(EnumPreferences.VESSELID.toString(), 1);
    }

    private View.OnClickListener finishListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), MenuActivity.class);
            startActivity(intent);
            //Android does not allow network operations on the main thread
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        RestHelper restHelper = new RestHelper(new URL(sp.getString(EnumPreferences.GETENDPOINT.toString(), "")), new URL(sp.getString(EnumPreferences.POSTENDPOINT.toString(), "")), sp.getString(EnumPreferences.BEARERTOKEN.toString(), ""), getContext());
                        restHelper.openConnection();
                        int boatId = getBoatFromSettings();
                        Vessel vessel = db.getVessel(boatId);
                        String json = restHelper.frequenzErfassungenToJSON(db.getFrequencyFromGroupNr(groupFrequencyNumber), vessel.getVesselName());
                        restHelper.postRequest(json);
                        db.setFrequencyRecordsStatus(db.getFrequencyFromGroupNr(groupFrequencyNumber), EnumStatus.Übertragen);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };




    public void removeOverview() {
        if (tl != null) {
            //Starts at 4 because we need the first 4 rows to stay where they are
            tl.removeViews(4, frequencyRecords.size());
        }
    }
}
