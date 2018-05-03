package com.isabel.apppractica2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PrincipalActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    TextView tPrincipal; //tTemporal2;
    String user, pass, correo,usuario,contraseña;
    Button bMesero, bCocina;

    private DatabaseReference databaseReference;

    private FirebaseAuth firebaseAuth; //maneja la autenticacion
    private FirebaseAuth.AuthStateListener authStateListener; //listener que escucha constantemente el usuario
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        tPrincipal = findViewById(R.id.tPrincipal);
        //tTemporal2 = findViewById(R.id.tTemporal2);
        bMesero = findViewById(R.id.bMesero);
        bCocina = findViewById(R.id.bCocina);

        //********** principal recibe desde login *************//
//        Bundle extras = getIntent().getExtras();
//        user = extras.getString("usuarioP");
//        pass = extras.getString("contraseñaP");
//        correo = extras.getString("correoP");

      //  tTemporal2.setText(user);

        inicializar();


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


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
///////////////////*//**************PERFIL*****************//*////////
        if(id == R.id.mPerfil){
            /*//**********+ voy a perfil con los datos ****************************//*/
//            Intent APerfil = new Intent(PrincipalActivity.this,PerfilActivity.class);
//            APerfil.putExtra("usuarioP",user);
//            APerfil.putExtra("contraseñaP",pass);
//            APerfil.putExtra("correoP",correo);
//            //setResult(RESULT_OK,APerfil);
//            startActivityForResult(APerfil,78);
//            //startActivity(APerfil);
//
//            finish();

            Intent i = new Intent(PrincipalActivity.this,PerfilActivity.class);
            startActivity(i);
            finish();


//////////*//*********CERRAR*************//*////////////////////
        }
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
                            Intent i = new Intent(PrincipalActivity.this,MainActivity.class);
                            startActivity(i);

                            finish();
                        } else {
                            Toast.makeText(PrincipalActivity.this, "Error cerrado sesion con Google", Toast.LENGTH_SHORT).show();
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
*/
    public void Mesero(View view) {
        Intent i = new Intent(PrincipalActivity.this,TabActivity.class);
        startActivity(i);
        finish();
    }

    public void Cocina(View view) {
        Intent i = new Intent(PrincipalActivity.this,CocinaActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //******************** recibo de perfil *********************//
//        if(requestCode == 78 /*&& resultCode == RESULT_OK*/ ){
//
//            user = data.getExtras().getString("usuarioP");
//            pass = data.getExtras().getString("contraseñaP");
//            correo = data.getExtras().getString("correoP");
//
//           // tTemporal2.setText("vengo de perfil" + user + "\n" + pass);
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }



}
