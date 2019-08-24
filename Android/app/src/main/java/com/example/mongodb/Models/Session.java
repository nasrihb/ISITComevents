package com.example.mongodb.Models;

import com.google.gson.annotations.SerializedName;

public class Session {

    @SerializedName("email")
    private String email;

    @SerializedName("ip")
    private String ip;

    @SerializedName("localisation")
    private String localisation;

    @SerializedName("iemi")
    private String iemi;

    public String getIp() {
        return ip;
    }

    public String getLocalisation() {
        return localisation;
    }

    public String getIemi() {
        return iemi;
    }
}
