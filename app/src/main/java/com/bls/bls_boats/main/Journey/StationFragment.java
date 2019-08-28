package com.bls.bls_boats.main.Journey;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bls.bls_boats.main.DB.DBHelper;
import com.bls.bls_boats.main.Entity.Reason;
import com.bls.bls_boats.main.Entity.Route;
import com.bls.bls_boats.main.Entity.Vessel;
import com.bls.bls_boats.main.Entity.FrequencyRecord;
import com.bls.bls_boats.main.Entity.Station;
import com.bls.bls_boats.main.R;

import java.util.Calendar;
import java.util.List;

/**
 * A single station view(screen) in form of a fragment
 */
public class StationFragment extends Fragment {

    public static final String ROUTE = "route";
    public static final String ROUTE_ID = "routeid";
    public static final String STATION = "station";
    public static final String STATION_ID = "station_id";
    public static final String GROUP_ID = "group_id";
    public static final String VESSEL_ID = "vesselid";

    private int haltID;
    private int routeID;
    private int gruppenFrequenzNummer;

    private EditText eingestiegenEdit;
    private EditText ausgestiegenEdit;

    private Spinner reasonSpinner;

    private CheckBox bedientCheck;

    private TextView currentTotalText;

    private Route route;

    private Vessel vessel;

    private DBHelper db;
    private Station station;

    private Reason reason;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHelper(getActivity());


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (db != null) {
            if (isVisibleToUser) {
                updateTextPersonCountOnShip(calculatePersonCountOnShip());
            } else {
                saveErfassung();
            }
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.halt_fragment, container, false);
        Bundle args = getArguments();

        gruppenFrequenzNummer = args.getInt(GROUP_ID);

        TextView routeText = (TextView) rootView.findViewById(R.id.linie_name);
        routeText.setText(String.valueOf(args.getInt(ROUTE_ID)));

        TextView haltestelleText = (TextView) rootView.findViewById(R.id.halt_name);
        String haltestelleName = args.getString(STATION);
        haltestelleText.setText(haltestelleName);

        currentTotalText = (TextView) rootView.findViewById(R.id.current_total);

        haltID = args.getInt(STATION_ID);
        routeID = args.getInt(ROUTE_ID);
        eingestiegenEdit = (EditText) rootView.findViewById(R.id.eingestiegen);
        ausgestiegenEdit = (EditText) rootView.findViewById(R.id.ausgestiegen);

        reasonSpinner = (Spinner) rootView.findViewById(R.id.comment);
        reasonSpinner.setEnabled(false);

        List<Reason> grunde = db.getAllReasons();
        populateSpinner(reasonSpinner, grunde);
        bedientCheck = (CheckBox) rootView.findViewById(R.id.bedient);
        bedientCheck.setChecked(true);

        db = new DBHelper(getActivity());

        bedientCheck.setOnCheckedChangeListener(changeListener);

        return rootView;
    }


    public List<FrequencyRecord> getFrequencyRecordsFromDb() {
        return db.getFrequencyFromGroupNr(gruppenFrequenzNummer);
    }

    public void updateTextPersonCountOnShip(int personCountOnShip) {
        currentTotalText.setText(String.valueOf(personCountOnShip));
    }


    public int calculatePersonCountOnShip() {
        List<FrequencyRecord> frequencyRecords = getFrequencyRecordsFromDb();

        int totalIn = 0;
        int totalOut = 0;

        for (int i = 0; i < frequencyRecords.size(); i++) {
            FrequencyRecord freq = frequencyRecords.get(i);

            totalIn = totalIn + freq.getBoardingCount();
            totalOut = totalOut + freq.getDeboardingCount();
        }

        return totalIn - totalOut;
    }

    private CompoundButton.OnCheckedChangeListener changeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                eingestiegenEdit.setEnabled(true);
                ausgestiegenEdit.setEnabled(true);
                eingestiegenEdit.setText("");
                ausgestiegenEdit.setText("");
                reasonSpinner.setEnabled(false);
            } else {
                eingestiegenEdit.setEnabled(false);
                ausgestiegenEdit.setEnabled(false);
                eingestiegenEdit.setText("0");
                ausgestiegenEdit.setText("0");
                reasonSpinner.setEnabled(true);
            }

        }
    };


    private void saveErfassung() {
        station = (Station) db.getStation(haltID);

        vessel = (Vessel) db.getVessel(1);
        route = (Route) db.getRoute(routeID);
        reason = (Reason) reasonSpinner.getSelectedItem();

        try {
            FrequencyRecord aktuellefrequezerfassung = getFrequenzerfassungIfExists(station);
            updateFrequenzerfassung(aktuellefrequezerfassung);
        } catch (Resources.NotFoundException e) {
            createFrequenzerfassung();
        }
    }

    public void updateFrequenzerfassung(FrequencyRecord erfassung) {
        if (!eingestiegenEdit.getText().toString().equals("") && !ausgestiegenEdit.getText().toString().equals("")) {
            erfassung.setVesselNumber(vessel.getVesselNumber());
            erfassung.setRouteNumber(route.getRouteNumber());
            erfassung.setStationNumber(station.getStationNumber());
            erfassung.setBoardingCount(Integer.parseInt(eingestiegenEdit.getText().toString()));
            erfassung.setDeboardingCount(Integer.parseInt(ausgestiegenEdit.getText().toString()));
            erfassung.setDepartueTime(Calendar.getInstance().getTime());
            erfassung.setArrivalTime(Calendar.getInstance().getTime());
            erfassung.setReason(reason.getId());
            erfassung.setNotOperated(bedientCheck.isChecked());
            erfassung.setFrequencyRecordGroupNumber(gruppenFrequenzNummer);

            db.updateFrequencyRecord(erfassung);
            showSaveToast("Erfolgreich aktualisiert");
        }

    }


    public void createFrequenzerfassung() {
        if (!eingestiegenEdit.getText().toString().equals("") && !ausgestiegenEdit.getText().toString().equals("")) {
            FrequencyRecord erfassung = new FrequencyRecord();
            erfassung.setVesselNumber(vessel.getVesselNumber());
            erfassung.setRouteNumber(route.getRouteNumber());
            erfassung.setStationNumber(station.getStationNumber());
            erfassung.setBoardingCount(Integer.parseInt(eingestiegenEdit.getText().toString()));
            erfassung.setDeboardingCount(Integer.parseInt(ausgestiegenEdit.getText().toString()));
            erfassung.setDepartueTime(Calendar.getInstance().getTime());
            erfassung.setArrivalTime(Calendar.getInstance().getTime());
            erfassung.setReason(reason.getId());
            erfassung.setNotOperated(bedientCheck.isChecked());
            erfassung.setFrequencyRecordGroupNumber(gruppenFrequenzNummer);

            db.insertFrequencyRecord(erfassung);
            showSaveToast("Erfolgreich gespeichert");
        }
    }


    private void showSaveToast(String message) {
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG);
        View view = toast.getView();

        //Gets the actual oval background of the Toast then sets the colour filter
        view.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

        //Gets the TextView from the Toast so it can be edited
        TextView toastText = view.findViewById(android.R.id.message);
        toastText.setTextColor(Color.WHITE);
        toastText.setTextSize(24);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

        toast.show();
    }


    public FrequencyRecord getFrequenzerfassungIfExists(Station station) {
        List<FrequencyRecord> frequenzen = db.getFrequencyFromGroupNr(gruppenFrequenzNummer);

        for (FrequencyRecord freq : frequenzen) {
            if (freq.getStationNumber() == station.getStationNumber()) {
                return freq;
            }
        }
        throw new Resources.NotFoundException();
    }

    public void populateSpinner(Spinner spinner, List data) {
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                R.layout.spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }


}


