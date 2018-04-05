package com.isabel.apppractica2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class PrincipalActivity extends AppCompatActivity {

    TextView tPrincipal; //tTemporal2;
    String user, pass, correo,usuario,contraseña;
    Button bMesero, bCocina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
////////////////////**************PERFIL*****************/////////
        if(id == R.id.mPerfil){
            //**********+ voy a perfil con los datos ****************************//
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


///////////*********CERRAR*************/////////////////////
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

            Intent i = new Intent(PrincipalActivity.this,MainActivity.class);
            startActivity(i);

            finish();

        }
        return super.onOptionsItemSelected(item);
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
