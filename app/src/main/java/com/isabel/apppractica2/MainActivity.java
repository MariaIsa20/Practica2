package com.isabel.apppractica2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//////////// LOGIN ACTIVITY  //////////////////
public class MainActivity extends AppCompatActivity {

    Button bLogin;
    TextView tcRegistro, tLogin,tTemporal;
    EditText eUsuario, eContraseña;
    String usuario, contraseña, user, pass, correo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bLogin = findViewById(R.id.bLogin);
        tcRegistro = findViewById(R.id.tcRegistro);
        tLogin = findViewById(R.id.tLogin);
        eUsuario = findViewById(R.id.eUsuario);
        eContraseña = findViewById(R.id.eContraseña);
        //tTemporal = findViewById(R.id.tTemporal);


        SpannableString texto = new SpannableString(getString(R.string.registrar));
        texto.setSpan(new UnderlineSpan(), 0, texto.length(), 0);
        tcRegistro.setText(texto);

//        Bundle extra = getIntent().getExtras();
//        user = extra.getString("usuariocerrarPerf");
//        pass = extra.getString("contraseñacerrarPerf");
//        correo = extra.getString("correocerrarPerf");
//
//        tTemporal.setText("vengo de perfil que cerro" + user);


    }

    public void Login(View view) {

        int id = view.getId();

        usuario = eUsuario.getText().toString();
        contraseña = eContraseña.getText().toString();

        //// *************** LOGIN ********************///////////
        if (id == R.id.bLogin){

            if (usuario.equals(user)&& contraseña.equals(pass)  ){
                //******** a principal le mando lo que obtuve de registro **********//
                Intent Aprincipal = new Intent(MainActivity.this,PrincipalActivity.class);
                Aprincipal.putExtra("usuarioP", user);
                Aprincipal.putExtra("contraseñaP", pass);
                Aprincipal.putExtra("correoP", correo);
                setResult(RESULT_OK, Aprincipal);
                //finish();
                startActivityForResult(Aprincipal,34); //espero respuesta de principal
                //finish();
            }
            else if (usuario.isEmpty()|| contraseña.isEmpty()){
                Toast.makeText(this,"Ingrese datos",Toast.LENGTH_SHORT).show();//cambiar
            }

            else{
                Toast.makeText(this,"Usuario o contraseña incorrecta",Toast.LENGTH_SHORT).show();//cambiar

            }
        }


    }
    ////////////************** REGISTRO ********************//////////////
    public void Registro(View view) {
        int id = view.getId();
         if (id == R.id.tcRegistro){
             //************************************* espero resultado de registro ****************//
            Intent Aregistro = new Intent(MainActivity.this,RegistroActivity.class);
            //startActivity(Aregistro);
             startActivityForResult(Aregistro,56); //start registro con ese codigo

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // ********** Resultado que viene de registro **********//
        if(requestCode == 56 && resultCode == RESULT_OK ){

            user = data.getExtras().getString("usuarioR");
            pass = data.getExtras().getString("contraseñaR");
            correo = data.getExtras().getString("correoR");

           // tTemporal.setText(user + "\n" + pass);
        }
        ////******************* vengo de cerrar sesion en principal ********************//
        else if(requestCode == 34 && resultCode == RESULT_OK ){

            user = data.getExtras().getString("usuarioP");
            pass = data.getExtras().getString("contraseñaP");
            correo = data.getExtras().getString("correoP");

           // tTemporal.setText("vengo de principal" + user + "\n" + pass);
        }




        super.onActivityResult(requestCode, resultCode, data);
    }

    ////////////////**************** OnActivityResult DE REGISTRO*************/////////////
}
