package com.example.mongodb.Models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("email")
    private String email;
    @SerializedName("info")
    private String info;
    @SerializedName("adress")
    private String adress;
    @SerializedName("tel")
    private String  tel;
    @SerializedName("linkdin")
    private String linkdin;
    @SerializedName("name")
    private String name;
    @SerializedName("password")
    private String password;
    @SerializedName("salt")
    private String salt;
    @SerializedName("email2")
    private String email2;

    public String getEmail() {
        return email;
    }

    public String getInfo() {
        return info;
    }

    public String getAdress() {
        return adress;
    }

    public String getTel() {
        return tel;
    }

    public String getLinkdin() {
        return linkdin;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getEmail2() {
        return email2;
    }


}
