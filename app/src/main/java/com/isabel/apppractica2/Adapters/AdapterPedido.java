package com.isabel.apppractica2.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isabel.apppractica2.DetalleActivity;
import com.isabel.apppractica2.R;
import com.isabel.apppractica2.model.Mesa;

import java.util.ArrayList;

/**
 * Created by Isabel on 29/05/2018.
 */

public class AdapterPedido extends RecyclerView.Adapter<AdapterPedido.MesaViewHolder>{

    private ArrayList<Mesa> mesaList;
    private int resource;
    private Activity activity;

    private DatabaseReference databaseReference;

    public AdapterPedido(ArrayList<Mesa> mesaList, int resource, Activity activity) {
        this.mesaList = mesaList;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public AdapterPedido.MesaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abre actividad con detalle
            }
        });
        return new MesaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterPedido.MesaViewHolder holder, int position) {
        Mesa mesas = mesaList.get(position);
        holder.bindmesas(mesas,activity);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abre actividad con detalle

                Intent intent = new Intent(activity, DetalleActivity.class);
                intent.putExtra("id_mesa",holder.tMesa.getText());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mesaList.size();
    }

    public class MesaViewHolder extends RecyclerView.ViewHolder {
        public TextView tMesa;

        public MesaViewHolder(View itemView) {
            super(itemView);
            tMesa = itemView.findViewById(R.id.tMesa);
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }

        public void bindmesas(final Mesa mesas, Activity activity) {

            final String[] idbandera = {"1"};
            final boolean[] flag = {false};
            databaseReference.child("Pedido").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String idtomado = "";
                    for (DataSnapshot idendb:dataSnapshot.getChildren()){

                        idtomado = idendb.child("id_mesa").getValue(String.class);

                        Log.d("infomesas","id leido:"+ idtomado);
                        Log.d("infomesas", "estado:"+ String.valueOf(flag[0]));

                        if (idbandera[0].equals(idtomado)){//es igual
                            flag[0] = true;
                            //idbandera[0] = idtomado;
                            Log.d("infomesas","entro al if porque el id es igual:"+ String.valueOf(flag[0]));

                        }else if (!idbandera[0].equals(idtomado)){
                            Log.d("infomesas","estoy en else if");
                            flag [0] = false;
                        }
                        //

                    }

                    if (!flag[0]){// es false
                        Log.d("infomesas","estoy en el if, id diferente");
                        Log.d("infomesas","id mostrar"+mesas.getId_mesa());
                        tMesa.setText(mesas.getId_mesa());
                        idbandera[0] = idtomado;

                        // idbandera [0] = mesas.getId_mesa();
                    }
                    else {
                        Log.d("infomesas","existe mesa");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }


    }
}
