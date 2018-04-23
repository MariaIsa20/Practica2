package com.isabel.apppractica2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

public class PerfilActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    TextView tPerfil, tMostrar;
    ImageView Iperfil;
    String user, pass, correo;

    //    ////******** Componentes para usar Firebase **********/////
    private FirebaseAuth firebaseAuth; //maneja la autenticacion
    private FirebaseAuth.AuthStateListener authStateListener; //listener que escucha constantemente el usuario
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tPerfil = findViewById(R.id.tPerfil);
        tMostrar = findViewById(R.id.tMostrar);
        Iperfil = findViewById(R.id.user);

//****************** Recibe de principal *****************//
//        Bundle extras = getIntent().getExtras();
//        user = extras.getString("usuarioP");
//        pass = extras.getString("contraseñaP");
//        correo = extras.getString("correoP");
//
//        tMostrar.setText("Usuario:" + user + "\n" + "Correo:" + correo);

        inicializar();
    }

    /////***** Firebase ********///
    private void inicializar() {
        firebaseAuth = FirebaseAuth.getInstance(); //inicializar los componentes de firebase, conectese al servicio en firebase
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //// alguien cambio el estado de la autenticacion, logearse o salirse
                //FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();//contiene la info del que se logeo

                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {

                    tMostrar.setText("Usuario:" + firebaseUser.getEmail());
                    Picasso.get().load(firebaseUser.getPhotoUrl()).into(Iperfil);
                }

//                if (firebaseUser != null) {
////                    user = firebaseUser.getEmail();
////                    pass = firebaseUser.getProviderId();
////                    correo = firebaseUser.getUid();
////
////                    tMostrar.setText("Usuario:" + user + "\n" + "otra cosa" + pass + "\n" + correo);
//                    Log.d("FirebaseUser", "Usuario logueado" + firebaseUser.getEmail());
//                }
                else{

                    tMostrar.setText(null);
                    Log.d("FirebaseUser", "NO usuario logueado, sesion cerrada");
                }
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
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.mPrincipal){

            //********** los devuelvo a principal **************//
//            Intent APrincipal = new Intent(PerfilActivity.this,PrincipalActivity.class);
//            APrincipal.putExtra("usuarioP",user);
//            APrincipal.putExtra("contraseñaP",pass);
//            APrincipal.putExtra("correoP",correo);
//            //setResult(RESULT_OK,APrincipal);
//            //startActivityForResult(APrincipal,90);
//            startActivity(APrincipal);
            Intent i = new Intent(PerfilActivity.this,PrincipalActivity.class);
            startActivity(i);
            finish();

        }


        else if (id == R.id.mCerrar){

            //****** debe volver a login con los datos ****///
//            Intent ALogin = new Intent(PerfilActivity.this,MainActivity.class);
//            ALogin.putExtra("usuarioP",user);
//            ALogin.putExtra("contraseñaP",pass);
//            ALogin.putExtra("correoP",correo);
//            setResult(RESULT_OK,ALogin);
//            //startActivityForResult(ALogin,90);
//            //startActivity(APerfil);
//
//            finish();
            firebaseAuth.signOut();
            if (Auth.GoogleSignInApi != null) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            goLoginActivity();
                        } else {
                            Toast.makeText(PerfilActivity.this, "Error cerrado sesion con Google", Toast.LENGTH_SHORT).show();
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

    private void goLoginActivity(){
        Intent i = new Intent(PerfilActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
