package com.isabel.apppractica2.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.isabel.apppractica2.R;
import com.isabel.apppractica2.model.Productos;

import java.util.ArrayList;

/**
 * Created by Isabel on 6/05/2018.
 */

public class AdapterProductos extends RecyclerView.Adapter<AdapterProductos.ProductosViewHolder>{

    private ArrayList<Productos> productosList;
    private int resource;
    private Activity activity;

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
        private EditText eCantidad;
        private CheckBox Check;

        public ProductosViewHolder(View itemView) {
            super(itemView);
            tProducto = itemView.findViewById(R.id.tProducto);
            tValor = itemView.findViewById(R.id.tValor);
            eCantidad = itemView.findViewById(R.id.eCantidad);
            Check = itemView.findViewById(R.id.Check);
        }



        public void bindproductos(Productos productos, Activity activity) {
            tProducto.setText(productos.getNombre());
            tValor.setText(String.valueOf(productos.getValor()));
            eCantidad.getText().toString();

            Check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        Toast.makeText(AdapterProductos.this.activity,"seleccionado"+ tProducto.getText() + eCantidad.getText(),Toast.LENGTH_SHORT).show();
                    }else{}
                }
            });

        }


    }
}
