package com.develop.filhan.eencyclopediaone;

/**
 * Created by ASUS on 04/05/2018.
 */

public class CommentModel {
    private int menuId;
    private String user;
    private String date;
    private String text;

    public CommentModel() {
    }

    public CommentModel(int menuId, String user, String date, String text) {
        this.menuId = menuId;
        this.user = user;
        this.date = date;
        this.text = text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
