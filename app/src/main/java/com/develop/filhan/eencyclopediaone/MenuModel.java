package com.develop.filhan.eencyclopediaone;

/**
 * Created by ASUS on 24/03/2018.
 */

public class MenuModel {
    private String judul;
    private String daerah;
    private String deskripsi;
    private String gambar;

    public MenuModel(String judul, String daerah, String deskripsi, String gambar) {
        this.judul = judul;
        this.daerah = daerah;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
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
