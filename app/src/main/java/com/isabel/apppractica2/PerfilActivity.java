package com.isabel.apppractica2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {

    TextView tPerfil, tMostrar;
    String user, pass, correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tPerfil = findViewById(R.id.tPerfil);
        tMostrar = findViewById(R.id.tMostrar);
//****************** Recibe de principal *****************//
        Bundle extras = getIntent().getExtras();
        user = extras.getString("usuarioP");
        pass = extras.getString("contraseñaP");
        correo = extras.getString("correoP");

        tMostrar.setText("Usuario:" + user + "\n" + "Correo:" + correo);
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
            Intent APrincipal = new Intent(PerfilActivity.this,PrincipalActivity.class);
            APrincipal.putExtra("usuarioP",user);
            APrincipal.putExtra("contraseñaP",pass);
            APrincipal.putExtra("correoP",correo);
            setResult(RESULT_OK,APrincipal);
            //startActivityForResult(APrincipal,90);
            startActivity(APrincipal);

            finish();


            //Intent APrincipal = new Intent(PerfilActivity.this,PrincipalActivity.class);
            //startActivity(APrincipal);
            //finish();

        }
        else if (id == R.id.mCerrar){

            //****** debe volver a login con los datos ****///
            Intent ALogin = new Intent(PerfilActivity.this,MainActivity.class);
            ALogin.putExtra("usuarioP",user);
            ALogin.putExtra("contraseñaP",pass);
            ALogin.putExtra("correoP",correo);
            setResult(RESULT_OK,ALogin);
            //startActivityForResult(ALogin,90);
            //startActivity(APerfil);

            finish();
        }
        return super.onOptionsItemSelected(item);
    }




}
