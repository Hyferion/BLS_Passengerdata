package com.bls.bls_boats.main.Journey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.bls.bls_boats.main.DB.DBHelper;
import com.bls.bls_boats.main.Entity.Route;
import com.bls.bls_boats.main.Journey.FragmentPagerAdapter;
import com.bls.bls_boats.main.Journey.StationFragment;
import com.bls.bls_boats.main.R;

import java.util.List;

/**
 * The activity for the Standard and Special Journey, generates the FragmentPagerAdapter which generates
 * the fragment view. This fragment view includes the StationFragment and the OverviewFragment
 */
public class JourneyActivity extends FragmentActivity {
    FragmentPagerAdapter fragmentPagerAdapter;
    ViewPager mViewPager;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        int lineId = intent.getIntExtra(StationFragment.ROUTE, 0);

        int groupNumber = intent.getIntExtra(StationFragment.GROUP_ID, 0);

        int vesselId = intent.getIntExtra(StationFragment.VESSEL_ID,0);

        final DBHelper db = new DBHelper(this);

        Route route = db.getRoute(lineId);
        List halts = db.getStationsFromRoute(lineId);

        int count = halts.size();

        mViewPager = (ViewPager) findViewById(R.id.pager);
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), route, halts, count, groupNumber, vesselId);
        mViewPager.setAdapter(fragmentPagerAdapter);

    }

}

