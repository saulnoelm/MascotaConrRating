package com.coursera.mascotasrecycleyview.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coursera.mascotasrecycleyview.R;
import com.coursera.mascotasrecycleyview.db.ConstructorMascotas;
import com.coursera.mascotasrecycleyview.pojos.Mascota;

import java.util.ArrayList;

public class MascotaAdaptadorPerfil extends RecyclerView.Adapter<MascotaAdaptadorPerfil.MascotaViewHolder> {

    private ArrayList<Mascota> mascotas;
    private Activity activity;

    public MascotaAdaptadorPerfil(ArrayList<Mascota> mascotas, Activity activity) {
        this.mascotas = mascotas;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MascotaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_mascotas_perfil, viewGroup, false);
        return new MascotaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MascotaViewHolder mascotaViewHolder, int i) {
        final Mascota mascota = mascotas.get(i);
        mascotaViewHolder.imgFotoPerfil.setImageResource(mascota.getFoto());
        mascotaViewHolder.tvNombrePerfil.setText(mascota.getNombre());
        mascotaViewHolder.tvLikesPerfil.setText(String.valueOf(mascota.getLikes()) + " Likes");
    }

    @Override
    public int getItemCount() {
        return mascotas.size();
    }

    public static class MascotaViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgFotoPerfil;
        private TextView tvNombrePerfil;
        private TextView tvLikesPerfil;

        public MascotaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFotoPerfil = (ImageView) itemView.findViewById(R.id.imgFotoPerfil);
            tvNombrePerfil = (TextView) itemView.findViewById(R.id.tvNombrePerfil);
            tvLikesPerfil = (TextView) itemView.findViewById(R.id.tvLikesPerfil);
        }
    }
}
