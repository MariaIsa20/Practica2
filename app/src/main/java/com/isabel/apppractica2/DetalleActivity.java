package com.isabel.apppractica2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isabel.apppractica2.Adapters.AdapterMesa;
import com.isabel.apppractica2.model.Pedido;

import java.util.ArrayList;

public class DetalleActivity extends AppCompatActivity {

    private ArrayList<Pedido> pedidoList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterPedido;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private TextView tMostrar;
    String mesaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        Bundle extras = getIntent().getExtras();
        mesaId = extras.getString("id_mesa");

        tMostrar = findViewById(R.id.tMostrar);
        //bIrCocina = findViewById(R.id.bIrCocina);

        recyclerView = findViewById(R.id.recyclerviewMesasDetalle);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        pedidoList = new ArrayList<>();

        adapterPedido = new AdapterMesa(pedidoList);

        recyclerView.setAdapter(adapterPedido);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        tMostrar.setText(mesaId);

        databaseReference.child("Pedido").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pedidoList.clear();

                if (dataSnapshot.exists()){

                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Pedido mesa = snapshot.getValue(Pedido.class);

                        if (mesa.getId_mesa().equals(mesaId)){
                            pedidoList.add(mesa);
                        }

                        //pedidoList.add(mesa);

                    }
                    adapterPedido.notifyDataSetChanged();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        /*bIrCocina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(DetalleActivity.this,CocinaActivity.class);
                startActivity(c);
            }
        });*/
    }
}
