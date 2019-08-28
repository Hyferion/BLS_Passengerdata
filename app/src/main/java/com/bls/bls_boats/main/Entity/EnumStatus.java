package com.bls.bls_boats.main.Entity;

public enum EnumStatus {
    Erfasst(0),Übertragen(1);

    private int status;

    EnumStatus(int status){this.status = status;}

    public int getStatus(){return this.status;}
}
