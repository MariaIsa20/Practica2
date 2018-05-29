package com.isabel.apppractica2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isabel.apppractica2.Adapters.AdapterOrdenes;
import com.isabel.apppractica2.model.Ordenes;

import java.util.ArrayList;

public class CocinaActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth firebaseAuth; //maneja la autenticacion
    private FirebaseAuth.AuthStateListener authStateListener; //listener que escucha constantemente el usuario
    private GoogleApiClient googleApiClient;

    // Recycler View
    private ArrayList<Ordenes> ordenesList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterOrden;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocina);

        inicializar();

        recyclerView = findViewById(R.id.RVcocina);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ordenesList = new ArrayList<>();
        adapterOrden = new AdapterOrdenes(ordenesList);
        recyclerView.setAdapter(adapterOrden);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Pedido").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ordenesList.clear();

                if (dataSnapshot.exists()){

                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                        Ordenes ordenes = snapshot.getValue(Ordenes.class);
                        if (ordenes.getEstado().equals("Pendiente")){
                            ordenesList.add(ordenes);
                        }
                        //ordenesList.add(ordenes);
                    }
                    adapterOrden.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void inicializar() {
        firebaseAuth = FirebaseAuth.getInstance(); //inicializar los componentes de firebase, conectese al servicio en firebase
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //// alguien cambio el estado de la autenticacion, logearse o salirse
                //FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();//contiene la info del que se logeo

                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                //////////////////////////////// con esto, se puede entrar luego del splash a main si hay ususario
            }
        };

        //Cuenta google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
        googleApiClient.disconnect();

    }

    @Override
    protected void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(this);
        googleApiClient.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        googleApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        googleApiClient.stopAutoManage(this);
        googleApiClient.disconnect();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
///////////////////*************PERFIL****************////////
        if(id == R.id.mPrincipal){
            /*********+ voy a perfil con los datos ***************************/
//            Intent APerfil = new Intent(PrincipalActivity.this,PerfilActivity.class);
//            APerfil.putExtra("usuarioP",user);
//            APerfil.putExtra("contraseñaP",pass);
//            APerfil.putExtra("correoP",correo);
//            //setResult(RESULT_OK,APerfil);
//            startActivityForResult(APerfil,78);
//            //startActivity(APerfil);
//
//            finish();

            Intent i = new Intent(CocinaActivity.this,PrincipalActivity.class);
            startActivity(i);
            finish();

        }
        else if (id == R.id.mPerfil){
            Intent i = new Intent(CocinaActivity.this,PerfilActivity.class);
            startActivity(i);
            finish();

        }

        //////////********CERRAR************////////////////////
        else if (id == R.id.mCerrar){
            //Intent ALogin = new Intent(PrincipalActivity.this,MainActivity.class);
//            Intent ALogin = new Intent();
//            startActivity(ALogin);
//            finish();
//            // si doy cerrar sesion en principal, deberia devolver a login con los datos
//            Intent Datosalogin = new Intent(PrincipalActivity.this,MainActivity.class);
//            Datosalogin.putExtra("usuarioP", user);
//            Datosalogin.putExtra("contraseñaP", pass);
//            Datosalogin.putExtra("correoP", correo);
//            setResult(RESULT_OK, Datosalogin);
//            //startActivityForResult(Datosalogin, 78);
            firebaseAuth.signOut();

            if (Auth.GoogleSignInApi != null) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            Intent i = new Intent(CocinaActivity.this,MainActivity.class);
                            startActivity(i);

                            finish();
                        } else {
                            Toast.makeText(CocinaActivity.this, "Error cerrado sesion con Google", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            if (LoginManager.getInstance() != null){
                LoginManager.getInstance().logOut();
            }


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
