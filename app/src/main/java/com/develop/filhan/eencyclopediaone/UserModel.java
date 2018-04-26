package com.develop.filhan.eencyclopediaone;

/**
 * Created by ASUS on 26/04/2018.
 */

public class UserModel {
    private String fullname, ttl, province, role;
    private String displayname, email, password;

    //For Firebase Instance
    public UserModel() {
    }

    public UserModel(String fullname, String ttl, String province, String displayname, String email, String role) {
        this.fullname = fullname;
        this.ttl = ttl;
        this.province = province;
        this.displayname = displayname;
        this.email = email;
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
