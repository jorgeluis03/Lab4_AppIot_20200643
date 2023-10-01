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
import android.widget.TextView;
import com.example.lab4_appiot_20200643.adapter.MagnetometroAdapter;
import com.example.lab4_appiot_20200643.databinding.FragmentMagnetometroBinding;
import android.graphics.drawable.Drawable;
import com.example.lab4_appiot_20200643.entity.Contacto;

import com.example.lab4_appiot_20200643.viewmodel.ContactoViewModel;

import java.util.ArrayList;
import java.util.List;


public class MagnetometroFragment extends Fragment implements SensorEventListener {
    //idea: que haya una lista aca para l recycle view y que desde el activity se envie los contactos y los agregue .add
    private SensorManager sensorManager;
    private Sensor magnetometro;
    private static final float UMBRAL_NORTE = 5.0f; // Umbral para determinar que apunta al norte (ejemplo)
    private FragmentMagnetometroBinding binding;
    private List<Contacto> listaContacMagnetometro = new ArrayList<>();
    private RecyclerView recyclerView;
    private MagnetometroAdapter adapter;
    private TextView textViewMagnetometro;
    private Drawable listaBackground;
    private View listaMask;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentMagnetometroBinding.inflate(inflater,container,false);

        ContactoViewModel contactoViewModel = new ViewModelProvider(requireActivity()).get(ContactoViewModel.class);
        contactoViewModel.getContactoMagnetometro().observe(getViewLifecycleOwner(),contacto -> {
            Log.d("msg-test","contacto livedata recibido: "+contacto.getName().getNombre());
            listaContacMagnetometro.add(contacto);
            for (Contacto c : listaContacMagnetometro){
                Log.d("msg-test","lista magneto: "+c.getName().getNombre());
            }
            obtenerContacMagneto();
        });

        recyclerView = binding.recycleViewMagnetometro;
        adapter = new MagnetometroAdapter();
        adapter.setContext(getContext());
        adapter.setLista(listaContacMagnetometro);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listaBackground = recyclerView.getBackground();
        listaMask = binding.listaMask;


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager = (SensorManager) requireContext().getSystemService(requireContext().SENSOR_SERVICE);
        if (sensorManager != null) {
            magnetometro = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            if (magnetometro != null) {
                sensorManager.registerListener(this, magnetometro, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (sensorManager != null && magnetometro != null) {
            sensorManager.unregisterListener(this);
        }

    }

    public void obtenerContacMagneto(){
        adapter.setLista(listaContacMagnetometro);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float[] magneticFieldValues = event.values;
            float x = magneticFieldValues[0];
            float y = magneticFieldValues[1];

            // Calcula el ángulo de dirección magnética en grados
            double angle = Math.atan2(y, x);
            angle = Math.toDegrees(angle);
            if (angle < 0) {
                angle += 360;
            }

            // Actualiza la visibilidad de la lista en función del ángulo
            actualizarVisibilidadLista(angle);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void actualizarVisibilidadLista(double angle) {
        double anguloMinimoVisible = 5.0;
        double anguloMaximoVisible = 90.0;

        if (angle >= anguloMinimoVisible && angle <= anguloMaximoVisible) {
            listaBackground.setAlpha(255);
            listaMask.setVisibility(View.GONE);
        } else {
            double porcentajeVisibilidad = calcularPorcentajeVisibilidad(angle);
            int alpha = (int) (255 * porcentajeVisibilidad);
            listaBackground.setAlpha(alpha);
            listaMask.setVisibility(View.VISIBLE);
            listaMask.getBackground().setAlpha(255 - alpha);
        }
    }

    private double calcularPorcentajeVisibilidad(double angle) {
        double anguloMinimoVisible = 5.0;
        double anguloMaximoVisible = 90.0;

        if (angle < anguloMinimoVisible) {
            return 1.0;
        } else if (angle > anguloMaximoVisible) {
            return 0.0;
        } else {
            return 1.0 - (angle - anguloMinimoVisible) / (anguloMaximoVisible - anguloMinimoVisible);
        }
    }
}