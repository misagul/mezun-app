package com.example.mainactivity;

import com.google.firebase.firestore.auth.User;

public class UserModel {
    private String ad, soyad, girisYili, mezunYili, mail, profilUrl;

    public UserModel(){

    }

    public UserModel(String ad, String soyad, String girisYili, String mezunYili, String mail, String profilUrl){
        this.ad = ad;
        this.soyad = soyad;
        this.girisYili = girisYili;
        this.mezunYili = mezunYili;
        this.mail = mail;
        this.profilUrl = profilUrl;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getGirisYili() {
        return girisYili;
    }

    public void setGirisYili(String girisYili) {
        this.girisYili = girisYili;
    }

    public String getMezunYili() {
        return mezunYili;
    }

    public void setMezunYili(String mezunYili) {
        this.mezunYili = mezunYili;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getProfilUrl() {
        return profilUrl;
    }

    public void setProfilUrl(String profilUrl) {
        this.profilUrl = profilUrl;
    }
}
