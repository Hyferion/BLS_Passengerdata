package com.bls.bls_boats.main.ExtraJourney;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.bls.bls_boats.main.DB.DBHelper;
import com.bls.bls_boats.main.Entity.Vessel;
import com.bls.bls_boats.main.Entity.SpecialFrequencyRecord;
import com.bls.bls_boats.main.MenuActivity;
import com.bls.bls_boats.main.R;
import com.bls.bls_boats.main.REST.RestHelper;
import com.bls.bls_boats.main.Settings.EnumPreferences;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Activity for the ExtraJourney, which only records time and distance
 */
public class ExtraJourneyActivity extends Activity {

    private Button saveBtn;
    private TextView startTime;
    private Date starTime;
    private DBHelper db;
    private Vessel vessel;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extrafahrt);

        db = new DBHelper(this);
        TextView boatName = (TextView) findViewById(R.id.Boat_name);
        Chronometer chrono = (Chronometer) findViewById(R.id.time_driven);
        saveBtn = (Button) findViewById(R.id.save_btn3);
        startTime = (TextView) findViewById(R.id.start_time_time);

        starTime = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy", Locale.GERMANY);
        startTime.setText(format.format(starTime));

        sp = getSharedPreferences(EnumPreferences.getBOATPREFERENCES(), 0);
        int boat_id = sp.getInt("boat_id", 1);

        vessel = db.getVessel(boat_id);

        boatName.setText(vessel.getVesselName());

        chrono.start();

        //REST-API DOES NOT WORK YET
        saveBtn.setOnClickListener(saveListener);
    }

    private View.OnClickListener saveListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            final SpecialFrequencyRecord frequenzerfassungSpezial = safeErfassung();
            Intent intent = new Intent(ExtraJourneyActivity.this, MenuActivity.class);
            startActivity(intent);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    try {
                        RestHelper restHelper = new RestHelper(new URL(sp.getString(EnumPreferences.GETENDPOINT.toString(), "")), new URL(sp.getString(EnumPreferences.POSTENDPOINT.toString(), "")), sp.getString(EnumPreferences.BEARERTOKEN.toString(), ""), getApplicationContext());
                        restHelper.openConnection();
                        List frequenzList = new ArrayList();
                        frequenzList.add(frequenzerfassungSpezial);

                        String json = restHelper.frequenzErfassungenToJSON(frequenzList, vessel.getVesselName());
                        restHelper.postRequest(json);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });

        }
    };

    private SpecialFrequencyRecord safeErfassung() {
        Date endTime = Calendar.getInstance().getTime();

        SpecialFrequencyRecord frequenzerfassungSpezial = new SpecialFrequencyRecord();
        frequenzerfassungSpezial.setStartTime(starTime);
        frequenzerfassungSpezial.setEndTime(endTime);
        frequenzerfassungSpezial.setVesselNumber(vessel.getVesselNumber());
        frequenzerfassungSpezial.setRouteNumber(1);
        frequenzerfassungSpezial.setStatus(1);

        db.insertSpecialFrequencyRecord(frequenzerfassungSpezial);

        return frequenzerfassungSpezial;
    }
}
