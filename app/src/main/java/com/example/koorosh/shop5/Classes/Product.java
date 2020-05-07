package com.example.koorosh.shop5.Classes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Koorosh on 29/09/2017.
 */

public class Product {


    @SerializedName("productId")
    public long productId;
    @SerializedName("name")
    public String name;
    @SerializedName("latinname")
    public String latinname;
    @SerializedName("price")
    public String price;
    @SerializedName("discount")
    public String discount;
    @SerializedName("image")
    public String image;
    @SerializedName("comment")
    public String comment;

}
