package com.coursera.mascotasrecycleyview.fragment;

import com.coursera.mascotasrecycleyview.pojos.Mascota;
import com.coursera.mascotasrecycleyview.utils.MascotaAdaptadorPerfil;

import java.util.ArrayList;

public interface IPerfilFragmentView {

    public void generarLinearLayoutVertical();

    public MascotaAdaptadorPerfil crearAdaptador(ArrayList<Mascota> mascotas);

    public void inicializarAdaptadorRV(MascotaAdaptadorPerfil mascotaAdaptador);

}
