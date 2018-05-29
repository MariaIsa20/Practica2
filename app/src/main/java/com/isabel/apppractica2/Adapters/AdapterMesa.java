package com.isabel.apppractica2.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isabel.apppractica2.R;
import com.isabel.apppractica2.model.Pedido;

import java.util.ArrayList;

/**
 * Created by Isabel on 29/05/2018.
 */

public class AdapterMesa extends RecyclerView.Adapter<AdapterMesa.MesaViewHolder> {
    private ArrayList<Pedido> pedidolist;
    private Activity activity;
    private DatabaseReference databaseReference; //objeto para referencia a la base de datos

    public AdapterMesa(ArrayList<Pedido> pedidolist) {
        this.pedidolist = pedidolist;
    }

    @Override
    public AdapterMesa.MesaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_mesadetalle,null,false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //on click en el card view
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();

        return new MesaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterMesa.MesaViewHolder holder, int position) {
        Pedido pedidocompleto = pedidolist.get(position);
        holder.bindpedido(pedidocompleto,activity);
    }

    @Override
    public int getItemCount() {
        return pedidolist.size();
    }

    public class MesaViewHolder extends RecyclerView.ViewHolder {
        private TextView tNombre, tValor, tCantidad, tMesa;
        private Button bEliminar;

        public MesaViewHolder(View itemView) {
            super(itemView);
            tNombre = itemView.findViewById(R.id.tNombre2);
            tValor = itemView.findViewById(R.id.tValor2);
            tCantidad = itemView.findViewById(R.id.tCantidad2);
            bEliminar = itemView.findViewById(R.id.bDelete);
        }

        public void bindpedido(final Pedido pedidocompleto, Activity activity) {
            tNombre.setText(pedidocompleto.getNombre());
            tValor.setText(String.valueOf(pedidocompleto.getValor()));
            tCantidad.setText(String.valueOf(pedidocompleto.getCantidad()));

            bEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseReference.child("Pedido").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Log.d("bdelete","datasnapshot"+ pedidocompleto.getId_mesa()+pedidocompleto.getId_pedido());
                            databaseReference.child("Pedido").child(pedidocompleto.getId_pedido()).removeValue();
//                            for (DataSnapshot mesasnapshot:dataSnapshot.getChildren()){
//
//                                Pedido pedido = mesasnapshot.getValue(Pedido.class);
//
//                                Log.d("bdelete","datasnapshot"+ pedidocompleto.getId_mesa()+pedidocompleto.getId_pedido());
////                                if (pedido.getId_mesa().equals(Tmesa)){
////                                    //String pos = getAdapterPosition();
////                                    Log.d("bdelete","datasnapshot"+ pedidocompleto);
////                                }
//
//
//                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });
        }
    }
}
