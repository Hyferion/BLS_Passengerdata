package com.bls.bls_boats.main.Journey;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bls.bls_boats.main.Entity.Station;
import com.bls.bls_boats.main.Entity.Route;

import java.util.List;

/**
 * Adapter which generates the fragment view with stations to swipe through.
 */
public class FragmentPagerAdapter extends FragmentStatePagerAdapter {

    private int count_stations;
    private Route route;
    private List<Station> stations;
    private int groupNumber;
    private int vesselId;

    public FragmentPagerAdapter(FragmentManager fm, Route route, List<Station> stations, int count_stations, int groupNumber, int vesselId) {
        super(fm);
        this.count_stations = count_stations;
        this.route = route;
        this.stations = stations;
        this.groupNumber = groupNumber;
        this.vesselId = vesselId;

    }

    @Override
    public Fragment getItem(int i) {

        if (i >= this.count_stations) {
            Fragment fragment = new OverviewFragment();
            Bundle args = new Bundle();
            args.putString(StationFragment.ROUTE, this.route.getRouteName());
            args.putInt(StationFragment.ROUTE_ID, this.route.getRouteNumber());
            args.putInt(StationFragment.GROUP_ID, groupNumber);
            fragment.setArguments(args);
            return fragment;

        } else {
            Station station = this.stations.get(i);
            Fragment fragment = new StationFragment();
            Bundle args = new Bundle();
            args.putInt(StationFragment.VESSEL_ID, this.vesselId);
            args.putString(StationFragment.ROUTE, this.route.getRouteName());
            args.putInt(StationFragment.ROUTE_ID, this.route.getRouteNumber());
            args.putString(StationFragment.STATION, station.getStationName());
            args.putInt(StationFragment.STATION_ID, station.getStationNumber());
            args.putInt(StationFragment.GROUP_ID, groupNumber);
            fragment.setArguments(args);
            return fragment;
        }
    }

    @Override
    public int getCount() {
        return this.count_stations + 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }


}
