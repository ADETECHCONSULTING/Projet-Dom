package com.example.jean.test.modele;

/**
 * Created by JEAN on 05/02/2017.
 */

public class Annonce {
    private String id;
    private String url;
    private String title;
    private String type;
    private String content;
    private String price;
    private String property_type;
    private String city;
    //private String picture_url;

    /**
     * Constructeur de la classe Annonce
     * @param id
     * @param url
     * @param title
     * @param type
     * @param content
     * @param price
     * @param property_type
     * @param city
     */
    public Annonce(String id, String url, String title, String type, String content, String price, String property_type, String city /*String picture_url*/) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.type = type;
        this.content = content;
        this.price = price;
        this.property_type = property_type;
        this.city = city;
        //this.picture_url = picture_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProperty_type() {
        return property_type;
    }

    public void setProperty_type(String property_type) {
        this.property_type = property_type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /*public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }*/
}
