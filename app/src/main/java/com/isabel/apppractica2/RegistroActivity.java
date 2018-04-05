package com.isabel.apppractica2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
    EditText eNUsuario, ecorreo, eNContraseña, confcontra;
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
                if (firebaseUser != null) {
                    Log.d("FirebaseUser", "Usuario logueado" + firebaseUser.getEmail());
                }
                else{
                    Log.d("FirebaseUser", "NO usuario logueado");
                    }
                //////////////////////////////// con esto, se puede entrar luego del splash a main si hay ususario
            }
        };

    }

    /////********************** Boton de registro **************************//////

    public void DatosRegistro(View view) {

        Usuario = eNUsuario.getText().toString();
        Contraseña = eNContraseña.getText().toString();
        Confirmacion = confcontra.getText().toString();
        Correo = ecorreo.getText().toString();

        if (Usuario.isEmpty() || Contraseña.isEmpty() || Confirmacion.isEmpty() || Correo.isEmpty()) {

            //esta vacio
            Toast.makeText(this,"Campo vacío",Toast.LENGTH_SHORT).show(); //cambiar
            //tInfo.setText("Campo vacío");

        } else if (!Contraseña.equals(Confirmacion)) {
            //tInfo.setText("Las contraseñas no coinciden");
            Toast.makeText(this,"Contraseñas no coinciden",Toast.LENGTH_SHORT).show();//cambiar

            Contraseña = eNContraseña.getText().toString();
            Confirmacion = confcontra.getText().toString();

        }

        else {

            /// Firebase
            crearcuenta(ecorreo.getText().toString(),eNContraseña.getText().toString());

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
                            Log.d("IF usuario creado", "estoy en el if");
                            Toast.makeText(RegistroActivity.this, "cuenta creada", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RegistroActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else {
                            Toast.makeText(RegistroActivity.this, "cuenta NO reg creada", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
