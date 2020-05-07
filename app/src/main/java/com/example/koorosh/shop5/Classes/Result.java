package com.example.koorosh.shop5.Classes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Koorosh on 14/08/2017.
 */

public class Result {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    public Result(Boolean error, String message, String status) {
        this.error = error;
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

}
