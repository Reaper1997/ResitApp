package com.example.steven.resitapp;

/**
 * Created by Steven on 6/28/2017.
 */

public class Cart {

    private String Title;
    private String Image;
    private String Price;
    private String Description;
    private String uid;



    public Cart(){

    }



    public Cart(String title, String image, String price, String description,String uid) {
        Title = title;
        Image = image;
        Price = price;
        Description = description;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImage() {

        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
