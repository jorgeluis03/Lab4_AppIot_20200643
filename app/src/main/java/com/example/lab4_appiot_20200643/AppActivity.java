package com.example.lab4_appiot_20200643;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.NavController;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lab4_appiot_20200643.databinding.ActivityAppBinding;
import com.example.lab4_appiot_20200643.entity.Contacto;
import com.example.lab4_appiot_20200643.entity.ContactoDto;
import com.example.lab4_appiot_20200643.fragmentos.AcelerometroFragmentDirections;
import com.example.lab4_appiot_20200643.fragmentos.MagnetometroFragmentDirections;
import com.example.lab4_appiot_20200643.retrofitService.ContactoSrevice;
import com.example.lab4_appiot_20200643.viewmodel.ContactoViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppActivity extends AppCompatActivity {
    private static String tag = "msg-test";
    ContactoSrevice contactoSrevice;
    List<Contacto> listContacMagnetometro = new ArrayList<>();
    List<Contacto> listContacAcelerometro = new ArrayList<>();
    private ContactoViewModel contactoViewModel;
    ActivityAppBinding binding;
    NavController navController;
    boolean estoyEnMagnetometro = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contactoViewModel = new ViewModelProvider(this).get(ContactoViewModel.class);

        // Obtiene el NavController desde el NavHostFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();

        Button buttomIrMagnetometro = findViewById(R.id.buttomIrMagnetometro);
        Button buttonAdd = binding.buttomAdd;


        buttomIrMagnetometro.setOnClickListener(view -> {
            if (estoyEnMagnetometro) {
                // Navegar al fragmento AcelerometroFragment
                navController.navigate(MagnetometroFragmentDirections.actionMagnetometroFragmentToAcelerometroFragment2());
            } else {
                // Navegar al fragmento MagnetometroFragment
                navController.navigate(AcelerometroFragmentDirections.actionAcelerometroFragmentToMagnetometroFragment());
            }
        });

        buttonAdd.setOnClickListener(view -> {
            obtenerContacto();
        });

        // Configura un listener para detectar cambios de destino en el NavController
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                // Actualiza el estado y el texto del botón según el fragmento actual
                estoyEnMagnetometro = destination.getId() == R.id.magnetometroFragment;
                updateButtonText();
            }
        });

        // Inicializa el texto del botón
        updateButtonText();
    }
    public void obtenerContacto(){

        contactoSrevice = new Retrofit.Builder()
                .baseUrl("https://randomuser.me")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ContactoSrevice.class);

        contactoSrevice.obtenerLista().enqueue(new Callback<ContactoDto>() {
            @Override
            public void onResponse(Call<ContactoDto> call, Response<ContactoDto> response) {
                if(response.isSuccessful()){
                    ContactoDto contactoDto = response.body();
                    Contacto contactoApi = contactoDto.getResults().get(0);

                    if (estoyEnMagnetometro) {
                        contactoViewModel.getContactoMagnetometro().setValue(contactoApi);
                    } else {
                        contactoViewModel.getContactoAcelerometro().setValue(contactoApi);
                    }

                    Log.d(tag,"response: "+contactoApi.getName().getNombre());

                }else {
                    Log.d(tag,"Respuesta exitosa pero vacia");
                }
            }


            @Override
            public void onFailure(Call<ContactoDto> call, Throwable t) {
                Log.d(tag,"paso algo!");
            }
        });

    }
    private void updateButtonText() {
        Button buttomIrMagnetometro = findViewById(R.id.buttomIrMagnetometro);
        if (estoyEnMagnetometro) {
            buttomIrMagnetometro.setText("Ir a Acelerometro");
        } else {
            buttomIrMagnetometro.setText("Ir a Magnetometro");
        }
    }
}