package com.example.lab4_appiot_20200643.fragmentos;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lab4_appiot_20200643.R;
import com.example.lab4_appiot_20200643.adapter.AcelerometroAdapter;
import com.example.lab4_appiot_20200643.adapter.MagnetometroAdapter;
import com.example.lab4_appiot_20200643.databinding.FragmentAcelerometroBinding;
import com.example.lab4_appiot_20200643.entity.Contacto;
import com.example.lab4_appiot_20200643.viewmodel.ContactoViewModel;

import java.util.ArrayList;
import java.util.List;

public class AcelerometroFragment extends Fragment implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor acelerometro;
    private static final float UMBRAL_ACELERACION = 20.0f;
    FragmentAcelerometroBinding binding;
    List<Contacto> listaContacAcelerometro = new ArrayList<>();
    private static String tag = "msg-test";
    private RecyclerView recyclerView;
    private AcelerometroAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentAcelerometroBinding.inflate(inflater,container,false);

        ContactoViewModel contactoViewModel = new ViewModelProvider(requireActivity()).get(ContactoViewModel.class);
        contactoViewModel.getContactoAcelerometro().observe(getViewLifecycleOwner(),contacto -> {
            Log.d("msg-test","contacto livedata recibido: "+contacto.getName().getNombre());
            listaContacAcelerometro.add(contacto);
            obtenerContacMagneto();
        });

        recyclerView = binding.recycleViewAcelerometro;
        adapter = new AcelerometroAdapter();
        adapter.setContext(getContext());
        adapter.setListaAcelerometro(listaContacAcelerometro);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager = (SensorManager) requireContext().getSystemService(requireContext().SENSOR_SERVICE);
        if (sensorManager != null) {
            acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (acelerometro != null) {
                sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sensorManager != null && acelerometro != null) {
            sensorManager.unregisterListener(this);
        }
    }

    public void obtenerContacMagneto(){
        adapter.setListaAcelerometro(listaContacAcelerometro);
        adapter.notifyDataSetChanged();
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float aceleracion = Math.abs(event.values[0]) + Math.abs(event.values[1]) + Math.abs(event.values[2]);
            if (aceleracion > UMBRAL_ACELERACION) {
                scrollToLastItem();
                Toast.makeText(requireContext(), "Su aceleración: " + aceleracion + " m/s^2", Toast.LENGTH_SHORT).show();

            }
        }
    }
    private void scrollToLastItem() {
        if (recyclerView.getAdapter() != null) {
            int lastItemPosition = recyclerView.getAdapter().getItemCount() - 1;
            if (lastItemPosition >= 0) {
                recyclerView.smoothScrollToPosition(lastItemPosition);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}