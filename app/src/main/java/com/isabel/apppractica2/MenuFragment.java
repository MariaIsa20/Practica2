package com.isabel.apppractica2;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isabel.apppractica2.Adapters.AdapterProductos;
import com.isabel.apppractica2.model.Productos;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    private  ArrayList<Productos> productosList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterProductos;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    FloatingActionButton fab;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_menu, container, false);

        fab = itemView.findViewById(R.id.FloatCheck);

        recyclerView = itemView.findViewById(R.id.recyclerviewMenu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        productosList = new ArrayList<>();

        adapterProductos = new AdapterProductos(productosList,R.layout.cardview,getActivity());
        recyclerView.setAdapter(adapterProductos);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productosList.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Productos productos = snapshot.getValue(Productos.class);
                        productosList.add(productos);
                    }
                    adapterProductos.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MenuFragment.this.getContext(),"Envio lo que esta checked al otro fragment",Toast.LENGTH_SHORT).show();
                }
        });



        return itemView;
    }

    //// Boton flotante



}
