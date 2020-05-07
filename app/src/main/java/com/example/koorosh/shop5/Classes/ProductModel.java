package com.example.koorosh.shop5.Classes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Koorosh on 29/09/2017.
 */

public class ProductModel {
    @SerializedName("products")
    public
    ArrayList<Product> products = new ArrayList<>();
}
