package etu.ihm.citycleaner.ui.mytrashs;

public class Trash {

    private int id, type, clutter;
    private double latitude, longitude;
    private String date;
    private String image;
    private int nbLike, groupId;

    public Trash() {

    }

    public Trash(int id, int type, int clutter, double latitude, double longitude, String date, String image, int groupId) {
        this.id = id;
        this.type = type;
        this.clutter = clutter;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.image = image;
        this.nbLike = 0;
        this.groupId = groupId;
    }

    public Trash(int id, int type, int clutter, double latitude, double longitude, String date, String image, int groupId, int nbLike) {
        this.id = id;
        this.type = type;
        this.clutter = clutter;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.image = image;
        this.nbLike = nbLike;
        this.groupId = groupId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getClutter() {
        return clutter;
    }

    public void setClutter(int clutter) {
        this.clutter = clutter;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setNbLike(int nbLike) {
        this.nbLike = nbLike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbLike(){
        return nbLike;
    }

    public void addLike(){
        ++nbLike;
    }

    public void removeLike(){
        --nbLike;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
