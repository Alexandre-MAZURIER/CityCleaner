package etu.ihm.citycleaner.ui.mytrashs;

import java.util.Date;

public class Trash {

    private int type;
    private String address;
    private String city;
    private int zip;
    private String country;
    private Date date;

    public Trash(int type, String address, String city, int zip, String country, Date date){
        this.type = type;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.country = country;
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
