package com.example.lab4_appiot_20200643.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Contacto implements Serializable {
    @SerializedName("picture")
    private Picture picture;
    @SerializedName("name")
    private Name name;
    @SerializedName("gender")
    private String genereo;
    @SerializedName("location")
    private Ubicacion ubicacion;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String telefono;

    //SubClases
    public class Ubicacion{
        @SerializedName("city")
        private String city;

        @SerializedName("country")
        private String pais;

        public String getCity() {
            return city;
        }

        public String getPais() {
            return pais;
        }
    }
    public class Name{
        @SerializedName("first")
        private String nombre;
        @SerializedName("last")
        private String apellido;
        @SerializedName("title")
        private String title;

        public String getTitle() {
            return title;
        }
        public String getNombre() {
            return nombre;
        }
        public String getApellido() {
            return apellido;
        }
    }
    public class Picture {
        @SerializedName("large")
        private String foto;

        public String getFoto() {
            return foto;
        }
    }

    //encapsulamiento
    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getGenereo() {
        return genereo;
    }

    public void setGenereo(String genereo) {
        this.genereo = genereo;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
