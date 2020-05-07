package com.example.koorosh.shop5;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Koorosh on 22/08/2017.
 */


public interface UploadImageInterface {
    @Multipart
    @POST("/Images/index.php")
    Call<UploadObject> uploadFile(@Part MultipartBody.Part file, @Part("name") RequestBody name, @Part("product") RequestBody product);
}
