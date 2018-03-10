package com.isabel.apppractica2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    TextView tRegistro;
    EditText eNUsuario, ecorreo, eNContraseña, confcontra;
    Button bResgistrar2;
    String Usuario, Contraseña, Confirmacion, Correo;

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



    }

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
            Intent Datosalogin = new Intent(RegistroActivity.this,MainActivity.class);
            Datosalogin.putExtra("usuarioR", Usuario);
            Datosalogin.putExtra("contraseñaR", Contraseña);
            Datosalogin.putExtra("confirmacionR", Confirmacion);
            Datosalogin.putExtra("correoR", Correo);
            setResult(RESULT_OK, Datosalogin);
            finish();
        }
    }


}
