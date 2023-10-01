package com.example.lab4_appiot_20200643.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab4_appiot_20200643.entity.Contacto;

public class ContactoViewModel extends ViewModel {
    MutableLiveData<Contacto> contactoMagnetometro  = new MutableLiveData<>();
    MutableLiveData<Contacto> contactoAcelerometro  = new MutableLiveData<>();

    public MutableLiveData<Contacto> getContactoMagnetometro() {
        return contactoMagnetometro;
    }

    public MutableLiveData<Contacto> getContactoAcelerometro() {
        return contactoAcelerometro;
    }

}
