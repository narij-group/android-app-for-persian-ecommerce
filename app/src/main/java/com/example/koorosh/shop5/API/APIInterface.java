package com.example.koorosh.shop5.API;

import com.example.koorosh.shop5.Classes.ProductModel;
import com.example.koorosh.shop5.Classes.Result;
import com.example.koorosh.shop5.Classes.RetrofitModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Koorosh on 13/08/2017.
 */

public interface APIInterface {

    @GET("getBrands")
    Call<RetrofitModel> getBrands();

    @GET("getSlides")
    Call<RetrofitModel> getSlides();

    @GET("getSpecialOfferProducts")
    Call<ProductModel> getSpecialOfferProducts();

    @GET("getLatestProducts")
    Call<ProductModel> getLatestProducts();

    @FormUrlEncoded
    @POST("getOneProduct")
    Call<ProductModel> getOneProduct(@Field("product_id") long id);

    @GET("getColors")
    Call<RetrofitModel> getColors();

    @GET("getSiteInfo")
    Call<RetrofitModel> getSiteInfo();

    @GET("insertBlankProduct")
    Call<Result> insertBlankProduct2(@Query("page") String millisec);

    @FormUrlEncoded
    @POST("getProductImages")
    Call<RetrofitModel> getProductImages(@Field("product_id") long id);

    @FormUrlEncoded
    @POST("getGroups")
    Call<RetrofitModel> getGroups(@Field("userid") int userid);

    @FormUrlEncoded
    @POST("getSubGroups")
    Call<RetrofitModel> getSubGroups(@Field("userid") int userid);

    @FormUrlEncoded
    @POST("getSupperGroups")
    Call<RetrofitModel> getSupperGroups(@Field("userid") int userid);

    @FormUrlEncoded
    @POST("login")
    Call<Result> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("insertColor")
    Call<Result> insertColor(
            @Field("productid") int productid,
            @Field("colorname") String colorname,
            @Field("quantity") int quantity
    );

    @FormUrlEncoded
    @POST("insertProduct")
    Call<Result> insertProduct(
            @Field("productid") int productid,
            @Field("name") String name,
            @Field("latinname") String latinname,
            @Field("price") int price,
            @Field("brand") String brand,
            @Field("group") String group,
            @Field("subgroup") String subgroup,
            @Field("suppergroup") String suppergroup,
            @Field("user") int user
    );


    @FormUrlEncoded
    @POST("register")
    Call<Result> register(
            @Field("name") String name,
            @Field("family") String family,
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password,
            @Field("nationality_code") long nationality_code,
            @Field("mobile") String mobile,
            @Field("phone") String phone
    );
}
