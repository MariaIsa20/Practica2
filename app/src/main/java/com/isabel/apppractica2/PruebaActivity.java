package com.isabel.apppractica2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isabel.apppractica2.model.usuarios;

import java.util.ArrayList;

public class PruebaActivity extends AppCompatActivity {

    private EditText enombre, ecorreo;
    private ListView listView;
    private ArrayList<String> nombreList;
    private  ArrayList<usuarios> usuariosList;
    private ArrayAdapter personasAdapter;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);

        enombre = findViewById(R.id.enombre);
        ecorreo = findViewById(R.id.ecorreo);
        listView = findViewById(R.id.llist);

        //inicializar las listas
        nombreList = new ArrayList();
        usuariosList = new ArrayList<>();
        personasAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(personasAdapter);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nombreList.clear(); //limpiamos las listas porque las va almacenando en el arreglo de la aplicacion, no en firebase
                //usuariosList.clear();

                //if (dataSnapshot.exists()){
                    //si existe el usuario
                    Log.d("donde estoy?","en el if");
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        usuarios User = snapshot.getValue(usuarios.class);
                        nombreList.add(User.getNombre());
                    }


              //  }
                personasAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String uid = usuariosList.get(i).getId();
                usuariosList.remove(i);
                nombreList.remove(i);
                databaseReference.child("Usuarios").child(uid).removeValue();
                return false;
            }
        });



    }

    public void ok(View view) { //almacenamos en la base de datos
        usuarios usuarios = new usuarios(databaseReference.push().getKey(),enombre.getText().toString(), ecorreo.getText().toString(), "url");
        databaseReference.child("Usuarios").child(String.valueOf(usuarios.getId())).setValue(usuarios);


    }
}
