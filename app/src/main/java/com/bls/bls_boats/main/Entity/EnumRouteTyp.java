package com.bls.bls_boats.main.Entity;

public enum EnumRouteTyp {
    Kursfahrt("Kursfahrt"),Sonderfahrt("Sonderfahrt"),Extrafahrt("Extrafahrt"),Dienstfahrt("Dienstfahrt");

    private final String routeTyp;

    EnumRouteTyp(String routeTyp){this.routeTyp = routeTyp;}

    public String getRouteTyp(){return routeTyp;}

    public static EnumRouteTyp getEnumByString(String code){
        for(EnumRouteTyp e : EnumRouteTyp.values()){
            if(code == e.routeTyp) return e;
        }
        return null;
    }
}
