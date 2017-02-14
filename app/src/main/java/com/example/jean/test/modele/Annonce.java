package com.example.jean.test.modele;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JEAN on 05/02/2017.
 */

public class Annonce implements Parcelable {
    private String id;
    private String url;
    private String title;
    private String type;
    private String content;
    private String price;
    private String city;
    private String picture_url;
    private String country;
    private String priceAttrib;

    /**
     * Constructeur de la classe Annonce
     */
    public Annonce() {

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPriceAttrib() {
        return priceAttrib;
    }

    public void setPriceAttrib(String priceAttrib) {
        this.priceAttrib = priceAttrib;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
