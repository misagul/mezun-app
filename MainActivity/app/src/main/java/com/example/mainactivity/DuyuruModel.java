package com.example.mainactivity;

import java.util.Date;

public class DuyuruModel {
    private String yazar, icerik, uid;
    private Date tarih;

    public DuyuruModel(){
        super();
    }

    public DuyuruModel(String uid, String yazar, String icerik, Date tarih) {
        this.uid = uid;
        this.yazar = yazar;
        this.icerik = icerik;
        this.tarih = tarih;
    }

    public String getYazar() {
        return yazar;
    }

    public void setYazar(String yazar) {
        this.yazar = yazar;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public Date getTarih() {
        return tarih;
    }

    public void setTarih(Date tarih) {
        this.tarih = tarih;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
