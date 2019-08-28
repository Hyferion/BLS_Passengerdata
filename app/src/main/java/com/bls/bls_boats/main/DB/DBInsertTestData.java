package com.bls.bls_boats.main.DB;

import com.bls.bls_boats.main.DB.DBHelper;
import com.bls.bls_boats.main.Entity.Operator;
import com.bls.bls_boats.main.Entity.EnumRouteTyp;
import com.bls.bls_boats.main.Entity.Reason;
import com.bls.bls_boats.main.Entity.Route;
import com.bls.bls_boats.main.Entity.Station;
import com.bls.bls_boats.main.Entity.Vessel;
import com.bls.bls_boats.main.Entity.RouteStation;


/**
 * Create test data for database for testing purposes
 */
public class DBInsertTestData {

    public void insertTestData(DBHelper db){
        Operator testOperator = new Operator(1,"BLS BOATS");

        Vessel testVessel = new Vessel(1,1,"Berner Oberland");
        Vessel testVessel2 = new Vessel(2,1,"Bubenberg");
        Vessel testVessel3 = new Vessel(3,1,"Stadt Thun");
        Vessel testVessel4 = new Vessel(4,1,"Beatus");
        Vessel testVessel5 = new Vessel(5,1,"Brienz");
        Vessel testVessel6 = new Vessel(6,1,"Jungfrau");
        Vessel testVessel7 = new Vessel(7,1,"Lötschberg");

        Route testRoute = new Route(111,1,"111 Thun(See)", EnumRouteTyp.Kursfahrt);
        Route testRoute2 = new Route(109,1,"109 Thun (See)",EnumRouteTyp.Kursfahrt);
        Route testRoute3 = new Route(110,1,"110 Spiez Schiffstation",EnumRouteTyp.Kursfahrt);
        Route testRoute4 = new Route(16,1,"16 Interlaken West (See)",EnumRouteTyp.Kursfahrt);

        Station testStation1 = new Station(1,"Thun (See)");
        Station testStation2 = new Station(2,"Spiez Schiffstation");
        Station testStation3 = new Station(3,"Beatenbucht (See)");
        Station testStation4 = new Station(4,"Hünibach (See)");
        Station testStation5 = new Station(5,"Hilterfingen (See)");
        Station testStation6 = new Station(6,"Oberhofen am Thunersee");
        Station testStation7 = new Station(7,"Interlaken West (See)");
        Station testStation8 = new Station(8,"Neuhaus (Unterseen) (See)");
        Station testStation9 = new Station(9,"Beatushöhlen-Sundlauenen");
        Station testStation10 = new Station(10,"Leissigen(See)");
        Station testStation11 = new Station(11,"Beatenbucht(See)");

        RouteStation testLinieHaltestelle1 = new RouteStation(1,111);
        RouteStation testLinieHaltestelle2 = new RouteStation(2,111);
        RouteStation testLinieHaltestelle3 = new RouteStation(3,111);
        RouteStation testLinieHaltestelle4 = new RouteStation(1,109);
        RouteStation testLinieHaltestelle5 = new RouteStation(4,109);
        RouteStation testLinieHaltestelle6 = new RouteStation(5,109);
        RouteStation testLinieHaltestelle7 = new RouteStation(6,109);
        RouteStation testLinieHaltestelle8 = new RouteStation(2,109);
        RouteStation testLinieHaltestelle9 = new RouteStation(7,16);
        RouteStation testLinieHaltestelle10 = new RouteStation(8,16);
        RouteStation testLinieHaltestelle11 = new RouteStation(9,16);
        RouteStation testLinieHaltestelle12 = new RouteStation(10,16);
        RouteStation testLinieHaltestelle13 = new RouteStation(11,16);

        Reason reason = new Reason(1,"Schlechtes Wetter");
        Reason reason1 = new Reason(2, "Zu wenig Wasser");
        Reason reason2 = new Reason(3, "Keine Sicht");


        db.insertOperator(testOperator);
        db.insertVessel(testVessel);
        db.insertVessel(testVessel2);
        db.insertVessel(testVessel3);
        db.insertVessel(testVessel4);
        db.insertVessel(testVessel5);
        db.insertVessel(testVessel6);
        db.insertVessel(testVessel7);
        db.insertRoute(testRoute);
        db.insertRoute(testRoute3);
        db.insertRoute(testRoute4);
        db.insertRoute(testRoute2);
        db.insertStation(testStation1);
        db.insertStation(testStation2);
        db.insertStation(testStation3);
        db.insertStation(testStation4);
        db.insertStation(testStation5);
        db.insertStation(testStation6);
        db.insertStation(testStation7);
        db.insertStation(testStation8);
        db.insertStation(testStation9);
        db.insertStation(testStation10);
        db.insertStation(testStation11);
        db.insertRouteStation(testLinieHaltestelle1);
        db.insertRouteStation(testLinieHaltestelle2);
        db.insertRouteStation(testLinieHaltestelle3);
        db.insertRouteStation(testLinieHaltestelle4);
        db.insertRouteStation(testLinieHaltestelle5);
        db.insertRouteStation(testLinieHaltestelle6);
        db.insertRouteStation(testLinieHaltestelle7);
        db.insertRouteStation(testLinieHaltestelle8);
        db.insertRouteStation(testLinieHaltestelle9);
        db.insertRouteStation(testLinieHaltestelle10);
        db.insertRouteStation(testLinieHaltestelle11);
        db.insertRouteStation(testLinieHaltestelle12);
        db.insertRouteStation(testLinieHaltestelle13);
        db.insertReason(reason);
        db.insertReason(reason1);
        db.insertReason(reason2);
    }

}
