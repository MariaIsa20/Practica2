package com.isabel.apppractica2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistroActivity extends AppCompatActivity {

    TextView tRegistro;
    TextInputLayout eNUsuario, ecorreo, eNContraseña, confcontra;
    //EditText eNUsuario, ecorreo, eNContraseña, confcontra;
    Button bResgistrar2;
    String Usuario, Contraseña, Confirmacion, Correo;


    ////******** Componentes para usar Firebase **********/////
    private FirebaseAuth firebaseAuth; //maneja la autenticacion
    private FirebaseAuth.AuthStateListener authStateListener; //listener que escucha constantemente el usuario


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        tRegistro = findViewById(R.id.tRegistro);
        eNUsuario = findViewById(R.id.eNUsuario);
        ecorreo = findViewById(R.id.ecorreo);
        eNContraseña = findViewById(R.id.eNContraseña);
        confcontra = findViewById(R.id.econfcontra);
        bResgistrar2 = findViewById(R.id.bregistrar2);


        ///**** Firebase ***////

        inicializar();
    }
    /////***** Firebase ********///
    private void inicializar() {
        firebaseAuth = FirebaseAuth.getInstance(); //inicializar los componentes de firebase, conectese al servicio en firebase
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            //// alguien cambio el estado de la autenticacion, logearse o salirse
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();//contiene la info del que se logeo
//                if (firebaseUser != null) {
//                    Log.d("FirebaseUser", "Usuario logueado" + firebaseUser.getEmail());
//                }
//                else{
//                    Log.d("FirebaseUser", "No hay usuario logueado");
//                    }
                //////////////////////////////// con esto, se puede entrar luego del splash a main si hay ususario
            }
        };

    }

    /////********************** Boton de registro **************************//////

    public void DatosRegistro(View view) {

        Usuario = eNUsuario.getEditText().getText().toString();
        Contraseña = eNContraseña.getEditText().getText().toString();
        Confirmacion = confcontra.getEditText().getText().toString();
        Correo = ecorreo.getEditText().getText().toString();

        if (Usuario.isEmpty() | Contraseña.isEmpty() | Confirmacion.isEmpty() | Correo.isEmpty()) {

            eNUsuario.setError(null);
            eNContraseña.setError(null);
            confcontra.setError(null);
            ecorreo.setError(null);

           if (Usuario.isEmpty()) {
               eNUsuario.setError("Campo vacío");
           } if (Contraseña.isEmpty()) {
               eNContraseña.setError("Campo vacío");
           } if (Confirmacion.isEmpty()) {
               confcontra.setError("Campo vacío");
           } if (Correo.isEmpty()) {
               ecorreo.setError("Campo vacío");
           }



            //esta vacio
            //Toast.makeText(this,"Campo vacío",Toast.LENGTH_SHORT).show(); //cambiar
            //tInfo.setText("Campo vacío");

        }else if (Contraseña.length()<8 || Confirmacion.length()<8){

            eNUsuario.setError(null);
            eNContraseña.setError(null);
            confcontra.setError(null);
            ecorreo.setError(null);

            eNContraseña.setError("Debe contener 8 o más caracteres");
            confcontra.setError("Debe contener 8 o más caracteres");
            //Toast.makeText(this,"Las contraseñas deben tener 8 o mas caracteres",Toast.LENGTH_SHORT).show(); //cambiar

        }else if (!Contraseña.equals(Confirmacion)) {
            //tInfo.setText("Las contraseñas no coinciden");
            //Toast.makeText(this,"Contraseñas no coinciden",Toast.LENGTH_SHORT).show();//cambiar

            //eNContraseña.setError("Las contraseñas no coinciden");
            eNUsuario.setError(null);
            eNContraseña.setError(null);
            confcontra.setError(null);
            ecorreo.setError(null);

            confcontra.setError("Las contraseñas no coinciden");

            Contraseña = eNContraseña.getEditText().getText().toString();
            Confirmacion = confcontra.getEditText().getText().toString();

        } else if (Usuario.contains(" ")|| Contraseña.contains(" ")||Confirmacion.contains(" ")||Correo.contains(" ")){

            eNUsuario.setError(null);
            eNContraseña.setError(null);
            confcontra.setError(null);
            ecorreo.setError(null);

            //Toast.makeText(this,"Las entradas no deben contener espacios",Toast.LENGTH_SHORT).show();//cambiar

            if (Usuario.contains(" ")) {
                eNUsuario.setError("Las entradas no deben contener espacios");
            }else if (Contraseña.contains(" ")) {
                eNContraseña.setError("Las entradas no deben contener espacios");
            }else if (Confirmacion.contains(" ")) {
                confcontra.setError("Las entradas no deben contener espacios");
            }else if (Correo.contains(" ")) {
                ecorreo.setError("Las entradas no deben contener espacios");
            }

        }

        else {

            /// Firebase
            crearcuenta(ecorreo.getEditText().getText().toString(),eNContraseña.getEditText().getText().toString());

            ////// envia a login //////
//            Intent Datosalogin = new Intent(RegistroActivity.this,MainActivity.class);
//            Datosalogin.putExtra("usuarioR", Usuario);
//            Datosalogin.putExtra("contraseñaR", Contraseña);
//            Datosalogin.putExtra("confirmacionR", Confirmacion);
//            Datosalogin.putExtra("correoR", Correo);
//
//            setResult(RESULT_OK, Datosalogin);
//
//            finish();
        }
    }

    private void crearcuenta(String usuario1, String Contraseña) {
        firebaseAuth.createUserWithEmailAndPassword(usuario1,Contraseña). /////tiene que ser correo!!!
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(RegistroActivity.this, "Cuenta creada", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RegistroActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else {
                            Toast.makeText(RegistroActivity.this, "Falló el registro", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
