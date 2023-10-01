package com.example.lab4_appiot_20200643.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContactoDto {
    @SerializedName("results")
    List<Contacto> results;

    public List<Contacto> getResults() {
        return results;
    }
}
