package com.example.mainactivity;

public class PaylasimModel {
    private String uid, baslik, icerik, url;
    private boolean image;
    public PaylasimModel(){
        super();
    }
    public PaylasimModel(String uid, String baslik, String icerik, String url, boolean image) {
        this.uid = uid;
        this.baslik = baslik;
        this.icerik = icerik;
        this.url = url;
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }
}
