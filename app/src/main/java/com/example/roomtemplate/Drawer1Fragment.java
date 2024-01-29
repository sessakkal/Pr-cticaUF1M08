package com.example.roomtemplate;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.roomtemplate.databinding.FragmentDrawer1Binding;

import com.example.roomtemplate.databinding.FragmentDrawer1Binding;

public class Drawer1Fragment extends Fragment {
    private FragmentDrawer1Binding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentDrawer1Binding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar mainToolbar = requireActivity().findViewById(R.id.toolbar);
        mainToolbar.setTitle("Buscar");

        ItunesViewModel itunesViewModel = new ViewModelProvider(this).get(ItunesViewModel.class);

        binding.texto.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) { return false; }

            @Override
            public boolean onQueryTextChange(String s) {
                itunesViewModel.buscar(s);
                return false;
            }
        });

        ContenidosAdapter contenidosAdapter = new ContenidosAdapter();
        //contenidosAdapter.setOnItemClickListener(contenido -> abrirDetallesFragment(contenido));
        binding.recyclerviewContenidos.setAdapter(contenidosAdapter);

        itunesViewModel.respuestaMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Itunes.Respuesta>() {
            @Override
            public void onChanged(Itunes.Respuesta respuesta) {
                if (respuesta != null) {
                    contenidosAdapter.establecerListaContenido(respuesta.results);
                }
            }
        });
    }

}