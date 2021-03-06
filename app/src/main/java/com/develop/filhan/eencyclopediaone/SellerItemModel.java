package com.develop.filhan.eencyclopediaone;

/**
 * Created by ASUS on 04/05/2018.
 */

public class SellerItemModel {
    private String uuid;
    private int menuId;
    private String itemname;
    private String address;
    private String latlon;
    private int minPrice, maxPrice;

    public SellerItemModel() {
    }

    public SellerItemModel(int menuId, String uuid, String itemname, String address, int minPrice, int maxPrice) {
        this.menuId = menuId;
        this.uuid = uuid;
        this.itemname = itemname;
        this.address = address;
        this.latlon = "";
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatlon() {
        return latlon;
    }

    public void setLatlon(String latlon) {
        this.latlon = latlon;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }
}
