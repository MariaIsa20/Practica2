package com.isabel.apppractica2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isabel.apppractica2.Adapters.AdapterEntrega;
import com.isabel.apppractica2.model.Entrega;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificacionesFragment extends Fragment {
    private ArrayList<Entrega> entregasList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterEntrega;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public NotificacionesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notificaciones, container, false);

        recyclerView = view.findViewById(R.id.RVentrega);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        entregasList = new ArrayList<>();

        adapterEntrega = new AdapterEntrega(entregasList,R.layout.cardview_entrega,getActivity());
        recyclerView.setAdapter(adapterEntrega);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        final String bandera = "Listo";
        databaseReference.child("Pedido").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                entregasList.clear();

                if (dataSnapshot.exists()){


                    // Log.d("entrega",entrega.toString());
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Entrega entrega = snapshot.getValue(Entrega.class);
                        //entregasList.add(entrega);
                        Log.d("entrega",bandera + "ordenes" + entrega.getEstado() );
                        if (entrega.getEstado().equals(bandera)){
                            entregasList.add(entrega);
                        }

                    }
                    adapterEntrega.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

}
