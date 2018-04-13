package com.isabel.apppractica2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isabel.apppractica2.model.usuarios;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PruebaActivity extends AppCompatActivity {

    private EditText enombre, ecorreo;
    private ListView listView;
    private ArrayList<String> nombreList;
    private  ArrayList<usuarios> usuariosList;
    private ArrayAdapter personasAdapter;
    private ImageView imageView;
    private String url = "https://firebasestorage.googleapis.com/v0/b/practica2-42d34.appspot.com/o/batman.jpg?alt=media&token=c16e27e6-c77d-4480-8985-84baf6975be6";

    private DatabaseReference databaseReference;

    public void ok(View view) { //almacenamos en la base de datos

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference.child("Usuarios").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Log.d("existe","si");


                }else {
                    Log.d("existe","no");
                    usuarios User = new usuarios(firebaseUser.getUid(),
                            firebaseUser.getDisplayName(),
                            firebaseUser.getPhoneNumber(),
                            firebaseUser.getEmail());
                    databaseReference.child("Usuarios").child(firebaseUser.getUid()).setValue(User);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //usuarios usuarios = new usuarios(databaseReference.push().getKey(),enombre.getText().toString(), ecorreo.getText().toString(), "url");
        //usuarios usuarios = new usuarios(databaseReference.push().getKey(),enombre.getText().toString(), ecorreo.getText().toString(), "url");
        //databaseReference.child("Usuarios").child(String.valueOf(usuarios.getId())).setValue(usuarios);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);

        enombre = findViewById(R.id.enombre);
        ecorreo = findViewById(R.id.ecorreo);
        listView = findViewById(R.id.llist);

        imageView = findViewById(R.id.Ifoto);

        Picasso.get().load(url).into(imageView);


        //inicializar las listas
        nombreList = new ArrayList();
        usuariosList = new ArrayList<>();

       // personasAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nombreList); //esto lo comentamos para la lista
        //personalizada

       final UsuarioAdapter usuarioAdapter = new UsuarioAdapter(this,usuariosList);

        listView.setAdapter(personasAdapter);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // poner  en principal activity
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser == null){//no hay usuario logueado
            crearusuario();
            Intent i = new Intent(PruebaActivity.this,MainActivity.class);
            startActivity(i);
            finish();
       }

        //// hasta aqui

        databaseReference.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // nombreList.clear(); //limpiamos las listas porque las va almacenando en el arreglo de la aplicacion, no en firebase
                usuariosList.clear();

                if (dataSnapshot.exists()) {
                    //si existe el usuario

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        usuarios User = snapshot.getValue(usuarios.class);
                       // nombreList.add(User.getNombre());
                        usuariosList.add(User);
                    }
                }
                //personasAdapter.notifyDataSetChanged();
                usuarioAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
            });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String uid = usuariosList.get(i).getId();
               // nombreList.remove(i);
                usuariosList.remove(i);
                databaseReference.child("Usuarios").child(uid).removeValue();
                return false;
            }
        });



    }

    private void crearusuario(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Usuarios").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Log.d("existe","si");


                }else {
                    Log.d("existe","no");
                    usuarios User = new usuarios(firebaseUser.getUid(),
                            firebaseUser.getDisplayName(),
                            firebaseUser.getPhoneNumber(),
                            firebaseUser.getEmail());
                    databaseReference.child("Usuarios").child(firebaseUser.getUid()).setValue(User);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    class UsuarioAdapter extends ArrayAdapter<usuarios>{

        public UsuarioAdapter(@NonNull Context context, ArrayList<usuarios> data) {
            super(context, R.layout.list_item,data);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.list_item,null);

            usuarios Usuarios = getItem(position);

            TextView nombre = item.findViewById(R.id.Tnombre);
            nombre.setText(Usuarios.getNombre());

            TextView correo = item.findViewById(R.id.Tcorreo);
            correo.setText(Usuarios.getCorreo());

            TextView telefono = item.findViewById(R.id.Ttelefono);
            telefono.setText(Usuarios.getFoto());

            return item;
        }
    }
}


