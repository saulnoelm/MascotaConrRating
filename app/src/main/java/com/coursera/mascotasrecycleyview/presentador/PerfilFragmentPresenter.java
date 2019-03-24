package com.coursera.mascotasrecycleyview.presentador;

import android.content.Context;

import com.coursera.mascotasrecycleyview.db.ConstructorMascotas;
import com.coursera.mascotasrecycleyview.fragment.IPerfilFragmentView;
import com.coursera.mascotasrecycleyview.pojos.Mascota;

import java.util.ArrayList;

public class PerfilFragmentPresenter implements IPerfilFragmentPresenter {

    private IPerfilFragmentView iPerfilFragmentView;
    private Context context;
    private ConstructorMascotas constructorMascotas;
    private ArrayList<Mascota> mascotas;

    public PerfilFragmentPresenter(IPerfilFragmentView iRecyclerViewFragmentView, Context context) {
        this.iPerfilFragmentView = iRecyclerViewFragmentView;
        this.context = context;
        obtenerMascotasBaseDatos();
    }

    @Override
    public void obtenerMascotasBaseDatos() {
        constructorMascotas = new ConstructorMascotas(context);
        mascotas = constructorMascotas.obtenerDatosPerfil();
        mostrarMascotasRV();
    }

    @Override
    public void mostrarMascotasRV() {
        iPerfilFragmentView.inicializarAdaptadorRV(iPerfilFragmentView.crearAdaptador(mascotas));
        iPerfilFragmentView.generarLinearLayoutVertical();
    }

}
