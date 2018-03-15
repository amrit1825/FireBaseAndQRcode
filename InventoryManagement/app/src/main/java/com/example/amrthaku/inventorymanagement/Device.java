package com.example.amrthaku.inventorymanagement;

/**
 * Created by amrthaku on 3/13/2018.
 */

public class Device {

    private String id;
    private String owner;
    private String manufacturer;
    private String imei;

    public Device() {
    }

    public Device(String id, String owner) {
        this.id = id;
        this.owner = owner;
    }

    public Device(String owner, String imei, String manufacturer) {
        this.owner = owner;
        this.imei = imei;
        this.manufacturer = manufacturer;
    }

    public Device(String id, String imei, String manufacturer, String owner) {
        this.id = id;
        this.imei = imei;
        this.manufacturer = manufacturer;
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
