package com.isabel.apppractica2.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isabel.apppractica2.R;
import com.isabel.apppractica2.model.Ordenes;
import com.isabel.apppractica2.model.Pedido;
import com.isabel.apppractica2.model.Productos;

import java.util.ArrayList;

/**
 * Created by Isabel on 6/05/2018.
 */

public class AdapterProductos extends RecyclerView.Adapter<AdapterProductos.ProductosViewHolder>{

    private ArrayList<Productos> productosList;
    private int resource;
    private Activity activity;

    private String Cantidad, Producto;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth; //maneja la autenticacion
    private FirebaseAuth.AuthStateListener authStateListener; //listener que escucha constantemente el usuario

    public AdapterProductos(ArrayList<Productos> productosList, int resource, Activity activity) {
        this.productosList = productosList;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public ProductosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(activity,"Abre actividad con detalle",Toast.LENGTH_SHORT).show();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference();//se obtiene la referencia
        firebaseAuth = FirebaseAuth.getInstance(); //inicializar los componentes de firebase, conectese al servicio en firebase

        return new ProductosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductosViewHolder holder, int position) {
        Productos productos = productosList.get(position);
        holder.bindproductos(productos,activity);
    }

    @Override
    public int getItemCount() {
        return productosList.size();
    }


    public class ProductosViewHolder extends RecyclerView.ViewHolder {
        private TextView tProducto, tValor;
        private EditText eCantidad, eMesa;
        //private CheckBox Check;
        public Button bOK;

        public ProductosViewHolder(View itemView) {
            super(itemView);
            tProducto = itemView.findViewById(R.id.tProducto);
            tValor = itemView.findViewById(R.id.tValor);
            eCantidad = itemView.findViewById(R.id.eCantidad);
            eMesa = activity.findViewById(R.id.eMesa);
            bOK = itemView.findViewById(R.id.bOK);
            //Check = itemView.findViewById(R.id.Check);

        }

        public void bindproductos(Productos productos, Activity activity) {
            tProducto.setText(productos.getNombre());
            tValor.setText(String.valueOf(productos.getValor()));
            eCantidad.getText().toString();
            eMesa.getText().toString();

            bOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();//contiene la info del que se logeo
                    databaseReference.child("Productos").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String user = firebaseUser.getEmail() ;
                            String state = "Pendiente";
                            final Pedido pedido = new Pedido(
                                    databaseReference.push().getKey(), //id_pedido
                                    user,
                                    Integer.valueOf(tValor.getText().toString()),
                                    Integer.valueOf(eCantidad.getText().toString()),
                                    eMesa.getText().toString(),
                                    tProducto.getText().toString(),
                                    state
                            );

                           // String state = "Pendiente";
                            /*Ordenes ordenes = new Ordenes(
                                    databaseReference.push().getKey(),
                                    eMesa.getText().toString(),
                                    tProducto.getText().toString(),
                                    Integer.valueOf(eCantidad.getText().toString()),
                                    state
                            );*/

                            databaseReference.child("Pedido").child(pedido.getId_pedido()).setValue(pedido).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Log.d("entre1","ok");
                                    }else{
                                        Log.d("entre 2","Ok");
                                        Log.d("Save",task.getException().toString());
                                    }
                                }
                            });

                            /*databaseReference.child("Ordenes").child(ordenes.getId_orden()).setValue(ordenes).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Log.d("entre1","ok");
                                    }else{
                                        Log.d("entre 2","Ok");
                                        Log.d("Save",task.getException().toString());
                                    }
                                }
                            });*/
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });

           /* Check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        Cantidad += eCantidad.getText().toString();
                        Producto += tProducto.getText().toString();
                        Log.d("Seleccionado",Cantidad + Producto);
                        //Toast.makeText(AdapterProductos.this.activity,"seleccionado"+ Cantidad + Producto,Toast.LENGTH_SHORT).show();
                    }else{

                    }
                }
            });*/

        }
    }
}
