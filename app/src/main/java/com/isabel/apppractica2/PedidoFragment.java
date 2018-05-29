package com.isabel.apppractica2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isabel.apppractica2.Adapters.AdapterPedido;
import com.isabel.apppractica2.model.Mesa;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PedidoFragment extends Fragment {

    private ArrayList<Mesa> mesaList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterMesas;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public PedidoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pedido, container, false);

        recyclerView = view.findViewById(R.id.recyclerviewMesas);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mesaList = new ArrayList<>();

        adapterMesas = new AdapterPedido(mesaList,R.layout.cardview_mesa,getActivity());
        recyclerView.setAdapter(adapterMesas);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Pedido").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mesaList.clear();

                if (dataSnapshot.exists()){
                    Mesa idmesa = dataSnapshot.getValue(Mesa.class);

                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                        Mesa mesa = snapshot.getValue(Mesa.class);
                        if (!mesa.getId_mesa().equals(idmesa.getId_mesa())){
                            mesaList.add(mesa);
                            //idmesa.setId_mesa(mesa.getId_mesa());
                        }
                        idmesa.setId_mesa(mesa.getId_mesa());
                    }
                    adapterMesas.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

}
