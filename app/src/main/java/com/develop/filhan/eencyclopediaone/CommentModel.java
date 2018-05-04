package com.develop.filhan.eencyclopediaone;

/**
 * Created by ASUS on 04/05/2018.
 */

public class CommentModel {
    private int menuId;
    private String user;
    private String email;
    private String date;
    private String text;

    public CommentModel() {
    }

    public CommentModel(int menuId, String user, String email, String date, String text) {
        this.menuId = menuId;
        this.user = user;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
