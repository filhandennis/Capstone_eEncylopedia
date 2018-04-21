package com.develop.filhan.eencyclopediaone;

/**
 * Created by ASUS on 22/04/2018.
 */

public class ProvinceModel {
    private int id;
    private String nama;
    private String d;

    public ProvinceModel(int id, String nama, String d) {
        this.id = id;
        this.nama = nama;
        this.d = d;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }
}
