package com.example.mongodb.Models;

import com.google.gson.annotations.SerializedName;

public class Event {private  String _id;
    private String titre;
    private String date;
    private String  heuredeb;
    private String heurefin;
    @SerializedName("email")
    private String email;
    @SerializedName("lieu")
    private String lieu;
    @SerializedName("responsable")
    private String  responsable;
    @SerializedName("description")
    private String description;

    public String get_id() {
        return _id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDate() {
        return date;
    }

    public String getHeuredeb() {
        return heuredeb;
    }

    public String getHeurefin() {
        return heurefin;
    }

    public String getEmail() {
        return email;
    }

    public String getLieu() {
        return lieu;
    }

    public String getResponsable() {
        return responsable;
    }

    public String getDescription() {
        return description;
    }
}
