package com.isabel.apppractica2;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.facebook.FacebookSdk;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.isabel.apppractica2.model.Usuarios;


//////////// LOGIN ACTIVITY  //////////////////
public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    Button bLogin;
    TextView tcRegistro, tLogin,tTemporal;
    EditText eUsuario, eContraseña;
    String usuario, contraseña, user, pass, correo;
    private SignInButton signGoogle;
    int LOGIN_CON_GOOGLE = 1;
    private LoginButton signFacebook;

//    ////******** Componentes para usar Firebase **********/////
    private FirebaseAuth firebaseAuth; //maneja la autenticacion
    private FirebaseAuth.AuthStateListener authStateListener; //listener que escucha constantemente el usuario
    private GoogleApiClient googleApiClient;
    private CallbackManager callbackManager;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        bLogin = findViewById(R.id.bLogin);
        tcRegistro = findViewById(R.id.tcRegistro);
        tLogin = findViewById(R.id.tLogin);
        eUsuario = findViewById(R.id.eUsuario);
        eContraseña = findViewById(R.id.eContraseña);
        //tTemporal = findViewById(R.id.tTemporal);
        signGoogle = findViewById(R.id.signGoogle);
        signFacebook = findViewById(R.id.signFacebook);

        SpannableString texto = new SpannableString(getString(R.string.registrar));
        texto.setSpan(new UnderlineSpan(), 0, texto.length(), 0);
        tcRegistro.setText(texto);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        inicializar();
    }

    /////***** Firebase ********///
    private void inicializar() {
        firebaseAuth = FirebaseAuth.getInstance(); //inicializar los componentes de firebase, conectese al servicio en firebase
        firebaseAuth.signOut();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //// alguien cambio el estado de la autenticacion, logearse o salirse
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();//contiene la info del que se logeo
                if (firebaseUser != null) {
                    Log.d("FirebaseUser", "Usuario logueado" + firebaseUser.getEmail());
                }
                else{
                    Log.d("FirebaseUser", "NO usuario logueado");
                }
                //////////////////////////////// con esto, se puede entrar luego del splash a main si hay ususario
            }
        };
        //Cuenta google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        //Boton de Google
        signGoogle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(i,LOGIN_CON_GOOGLE);
            }
        });
        /// Inicia sesion con Facebook
        signFacebook.setReadPermissions("email","public_profile");

        signFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Login con Facebook","Login exitoso");
                signInFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("Login con Facebook","Login Cancelado");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Login con Facebook","Login Error");
            }
        });

    }

    private void signInFacebook(AccessToken accessToken){
        final AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());

        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Log.d("Login con Facebook","Voy a principal");
                    //Toast.makeText(MainActivity.this, "voy a principal",Toast.LENGTH_SHORT).show();
                  /// DatabaseReference correosRegistrados = databaseReference.child("Usuarios").child("correo");

                    Usuarios usuario = new Usuarios(databaseReference.push().getKey(),
                            firebaseAuth.getCurrentUser().getEmail());
                    databaseReference.child("Usuarios").child(usuario.getId()).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Log.d("entre 1","ok");
                            }else{
                                Log.d("entre 2","o");
                                Log.d("Save",task.getException().toString());
                            }
                        }
                    });

                    goPrincipalActivity();
                } else {
                    Log.d("Login con Facebook","Autenticacion o exitosa");
                    //Toast.makeText(MainActivity.this, "Autenticacion con Facebook no exitosa",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //// Inicia sesion con Google
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_CON_GOOGLE){
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            signInGoogle(googleSignInResult);
        } else {
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void  signInGoogle(final GoogleSignInResult googleSignInResult){
        if (googleSignInResult.isSuccess()){
            Log.d("Login google","listo");
            AuthCredential authCredential = GoogleAuthProvider.getCredential(
                    googleSignInResult.getSignInAccount().getIdToken(),null
            );
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    Usuarios usuario = new Usuarios(databaseReference.push().getKey(),
                            googleSignInResult.getSignInAccount().getAccount().name);
                    databaseReference.child("Usuarios").child(usuario.getId()).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Log.d("entre 1","ok");
                            }else{
                                Log.d("entre 2","o");
                                Log.d("Save",task.getException().toString());
                            }
                        }
                    });
                    goPrincipalActivity();
                }
            });

        }
        else {
            Log.d("Autenticacion","Autenticacion con Google no exitosa");
            //oast.makeText(MainActivity.this,"Autenticación con Google no exitosa", Toast.LENGTH_SHORT).show();
        }
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
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        firebaseAuth.signOut();
//    }

    public void Login(View view) {

        int id = view.getId();

        usuario = eUsuario.getText().toString();
        contraseña = eContraseña.getText().toString();

        //// *************** LOGIN ********************///////////
        if (id == R.id.bLogin){

            //// Firebase


//            if (usuario.equals(user)&& contraseña.equals(pass)  ){

                //// Firebase
//                iniciarsesion(eUsuario.getText().toString(),eContraseña.getText().toString());

                //******** a principal le mando lo que obtuve de registro **********//
//                Intent Aprincipal = new Intent(MainActivity.this,PrincipalActivity.class);
//                Aprincipal.putExtra("usuarioP", user);
//                Aprincipal.putExtra("contraseñaP", pass);
//                Aprincipal.putExtra("correoP", correo);
//                //setResult(RESULT_OK, Aprincipal);
//                //finish();
//                startActivityForResult(Aprincipal,34); //espero respuesta de principal
//                //finish();
//            }
            //aqui iba else if
            //else

            if (usuario.isEmpty()|| contraseña.isEmpty()){
                Toast.makeText(this,"Ingrese datos",Toast.LENGTH_SHORT).show();//cambiar
            }

            else{
                Log.d("obtenido","estoy en el else");
                //dbusuario(eUsuario.getText().toString());

                iniciarsesion(eUsuario.getText().toString(),eContraseña.getText().toString());

            }
            eUsuario.setText(null);
            eContraseña.setText(null);
        }

    }
/// inicia sesion con usuario registrado
    private void iniciarsesion(final String usuario1, String contraseña1) {

        firebaseAuth.signInWithEmailAndPassword(usuario1,contraseña1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dbusuario(usuario1);
                            goPrincipalActivity();

                        } else {
                            Log.d("Login", "Cuenta no creada");
                            Toast.makeText(MainActivity.this, "La cuenta no existe", Toast.LENGTH_SHORT).show();
                        }
                    }
        });
    }

    public void dbusuario(final String username){
        Log.d("obtenido","dbusuario" + username);
        //final String username = eUsuario.getText().toString().toLowerCase();
        final boolean[] flag = {false};
        databaseReference.child("Usuarios").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot idgenerado:  dataSnapshot.getChildren()){
                    String usernameTaken = idgenerado.child("correo").getValue(String.class);
                    Log.d("obtenido","nombre leido:"+ usernameTaken);
                    Log.d("obtenido", "estado:"+ String.valueOf(flag[0]));
                    if (username.equals(usernameTaken.toLowerCase())){ // si es igual
                        flag[0] = true;

                        Log.d("obtenido","entro al if porque el nombre es igual:"+ String.valueOf(flag[0]));
                    }
                }
                if (!flag[0]){ // si es false
                    Log.d("obtenido","estoy en el if, nombre diferente"+ username);
                    Usuarios usuario = new Usuarios(databaseReference.push().getKey(),
                            username);
                            Log.d("obtenido","usuario:"+eUsuario.getText().toString());
                    databaseReference.child("Usuarios").child(usuario.getId()).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                    //flag[0]=true;

                }
                else{
                    Log.d("obtenido", "ya esta el nombre");
                    //Toast.makeText(MainActivity.this,"Existe el nombre",Toast.LENGTH_SHORT).show();
                }

                //iniciarsesion(eUsuario.getText().toString(),eContraseña.getText().toString());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void goPrincipalActivity(){
        //Intent i = new Intent(MainActivity.this,PruebaActivity.class);
        Intent i = new Intent(MainActivity.this,PrincipalActivity.class); ////OJO cambiar a esto !!!!!!!!!!!!!!!!!
        /// AQUI VA CREARUSUARIO, LO QUE MIRA SI YA EXISTE EL USUARIO
        startActivity(i);
        finish();
    }

    ////////////************** REGISTRO ********************//////////////
    public void Registro(View view) {
        int id = view.getId();
         if (id == R.id.tcRegistro){
             //************************************* espero resultado de registro ****************//
            Intent Aregistro = new Intent(MainActivity.this,RegistroActivity.class);
            startActivity(Aregistro);
            finish(); //esto lo acbé de poner
          //   startActivityForResult(Aregistro,56); //start registro con ese codigo

             eUsuario.setText(null);
             eContraseña.setText(null);


         }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // ********** Resultado que viene de registro **********//
//        if(requestCode == 56 /*&& resultCode == RESULT_OK*/ ){
//
//            user = data.getExtras().getString("usuarioR");
//            pass = data.getExtras().getString("contraseñaR");
//            correo = data.getExtras().getString("correoR");
//
//
//           // tTemporal.setText(user + "\n" + pass);
//        }
//        ////******************* vengo de cerrar sesion en principal ********************//
//        else if(requestCode == 34 && resultCode == RESULT_OK){
//
//            user = data.getExtras().getString("usuarioP");
//            pass = data.getExtras().getString("contraseñaP");
//            correo = data.getExtras().getString("correoP");
//
//           // tTemporal.setText("vengo de principal" + user + "\n" + pass);
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    ////////////////**************** OnActivityResult DE REGISTRO*************/////////////
}
