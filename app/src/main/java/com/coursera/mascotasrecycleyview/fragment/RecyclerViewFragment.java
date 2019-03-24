package com.coursera.mascotasrecycleyview.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.LinearLayoutManager;
import com.coursera.mascotasrecycleyview.R;
import com.coursera.mascotasrecycleyview.pojos.Mascota;
import com.coursera.mascotasrecycleyview.presentador.IRecyclerViewFragmentPresenter;
import com.coursera.mascotasrecycleyview.presentador.RecyclerViewFragmentPresenter;
import com.coursera.mascotasrecycleyview.utils.MascotaAdaptador;
import com.coursera.mascotasrecycleyview.utils.MascotaAdaptadorPerfil;

import java.util.ArrayList;

public class RecyclerViewFragment extends Fragment implements IRecyclerViewFragmentView {

    private RecyclerView listaMascotas;
    private IRecyclerViewFragmentPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        setInitialValues(v);

        return v;
    }

    /**Metodo que agrega los valores a los componentes
     * @return void
     * */
    public void setInitialValues(View v) {
        listaMascotas =(RecyclerView) v.findViewById(R.id.rvMascotas);
        presenter = new RecyclerViewFragmentPresenter(this, getContext());
    }

    @Override
    public void generarLinearLayoutVertical() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        listaMascotas.setLayoutManager(llm);
    }

    @Override
    public MascotaAdaptador crearAdaptador(ArrayList<Mascota> mascotas) {
        return new MascotaAdaptador(mascotas, getActivity());
    }

    @Override
    public void inicializarAdaptadorRV(MascotaAdaptador adaptador) {
        listaMascotas.setAdapter(adaptador);
    }
}
