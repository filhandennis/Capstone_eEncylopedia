package com.develop.filhan.eencyclopediaone;

/**
 * Created by ASUS on 04/05/2018.
 */

public class ViewModel {
    private int menuId;
    private String user;
    private String date;

    public ViewModel() {
    }

    public ViewModel(int menuId, String user, String date) {
        this.menuId = menuId;
        this.user = user;
        this.date = date;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
