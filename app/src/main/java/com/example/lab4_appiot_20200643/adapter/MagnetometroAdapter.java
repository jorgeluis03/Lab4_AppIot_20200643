package com.example.lab4_appiot_20200643.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_appiot_20200643.R;
import com.example.lab4_appiot_20200643.entity.Contacto;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MagnetometroAdapter extends RecyclerView.Adapter<MagnetometroAdapter.MagnetrometroViewHolder>{
    private List<Contacto> lista;
    private Context context;

    @NonNull
    @Override
    public MagnetrometroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.irv_magnetometro,parent,false);
        return new MagnetrometroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MagnetrometroViewHolder holder, int position) {
        Contacto contac =lista.get(position);
        holder.contacto = contac;

        ImageView imageView = holder.itemView.findViewById(R.id.foto);
        TextView tvNombre = holder.itemView.findViewById(R.id.nombre);
        TextView tvGenero = holder.itemView.findViewById(R.id.genero);
        TextView tvCiudad = holder.itemView.findViewById(R.id.ciudad);
        TextView tvPais = holder.itemView.findViewById(R.id.pais);
        TextView tvEmail = holder.itemView.findViewById(R.id.email);
        TextView tvTelefono = holder.itemView.findViewById(R.id.telefono);

        tvNombre.setText(contac.getName().getTitle()+' '+contac.getName().getNombre()+' '+contac.getName().getApellido());
        tvGenero.setText("Genero: "+contac.getGenereo());
        tvEmail.setText("Email: "+contac.getEmail());
        tvTelefono.setText("Telefono: "+contac.getTelefono());
        tvCiudad.setText("Ciudad: "+contac.getUbicacion().getCity());
        tvPais.setText("Pais: "+contac.getUbicacion().getPais());
        Picasso.get().load(contac.getPicture().getFoto()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    //SUBCLASE
    public class MagnetrometroViewHolder extends RecyclerView.ViewHolder{
        Contacto contacto;
        public MagnetrometroViewHolder(@NonNull View itemView) {
            super(itemView);
            Button buttonRemove = itemView.findViewById(R.id.remove);
            buttonRemove.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    lista.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }
    }

    //encapsulamiento
    public List<Contacto> getLista() {
        return lista;
    }

    public void setLista(List<Contacto> lista) {
        this.lista = lista;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
