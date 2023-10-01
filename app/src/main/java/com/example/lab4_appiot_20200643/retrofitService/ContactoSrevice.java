package com.example.lab4_appiot_20200643.retrofitService;

import com.example.lab4_appiot_20200643.entity.ContactoDto;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ContactoSrevice {
    @GET("/api/")
    Call<ContactoDto> obtenerLista();
}
