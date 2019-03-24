package com.coursera.mascotasrecycleyview.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coursera.mascotasrecycleyview.R;
import com.coursera.mascotasrecycleyview.pojos.Mascota;
import com.coursera.mascotasrecycleyview.presentador.IPerfilFragmentPresenter;
import com.coursera.mascotasrecycleyview.presentador.PerfilFragmentPresenter;
import com.coursera.mascotasrecycleyview.utils.MascotaAdaptadorPerfil;

import java.util.ArrayList;

public class PerfilFragmentView extends Fragment implements IPerfilFragmentView {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView listaMascotas;
    private IPerfilFragmentPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);

        setInitialValues(v);
        setUpUIInteraction();

        return v;
    }

    /**Metodo que agrega los valores a los componentes
     * @return void
     * */
    public void setInitialValues(View v) {
        swipeRefreshLayout = v.findViewById(R.id.swipe);
        listaMascotas =(RecyclerView) v.findViewById(R.id.rvPerfilMascotas);
        presenter = new PerfilFragmentPresenter(this, getContext());
    }

    /**Metodo donde se definen los comportamientos de los componente con los eventos de usuario
     * @return void
     * */
    public void setUpUIInteraction() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }

    @Override
    public void generarLinearLayoutVertical() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listaMascotas.setLayoutManager(llm);
    }

    @Override
    public MascotaAdaptadorPerfil crearAdaptador(ArrayList<Mascota> mascotas) {
        return new MascotaAdaptadorPerfil(mascotas, getActivity());
    }

    @Override
    public void inicializarAdaptadorRV(MascotaAdaptadorPerfil adaptador) {
        listaMascotas.setAdapter(adaptador);
    }

}
