package com.develop.filhan.eencyclopediaone;

import java.io.Serializable;

/**
 * Created by ASUS on 24/03/2018.
 */

public class MenuModel implements Serializable{
    private int id;
    private String judul;
    private String daerah;
    private String deskripsi;
    private String gambar;

    public MenuModel(String judul, String daerah, String deskripsi, String gambar) {
        this.id = -1;
        this.judul = judul;
        this.daerah = daerah;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDaerah() {
        return daerah;
    }

    public void setDaerah(String daerah) {
        this.daerah = daerah;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
