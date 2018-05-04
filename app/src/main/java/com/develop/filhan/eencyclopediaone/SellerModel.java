package com.develop.filhan.eencyclopediaone;

/**
 * Created by ASUS on 05/05/2018.
 */

public class SellerModel {
    private String userId;
    private String nama, address, status, latlon;

    public SellerModel() {
    }

    public SellerModel(String userId, String nama, String address, String status) {
        this.userId = userId;
        this.nama = nama;
        this.address = address;
        this.status = status;
        this.latlon="";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLatlon() {
        return latlon;
    }

    public void setLatlon(String latlon) {
        this.latlon = latlon;
    }
}
