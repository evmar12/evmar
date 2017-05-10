package com.agendaapp.agendapediatrica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*
        final Button boton = (Button) findViewById(R.id.buttonIngresar);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = ((EditText) findViewById(R.id.editTextUsuario)).getText().toString();
                String password = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();
                if (usuario.equals("admin")&& password.equals("admin")){
                    Intent segundoForm = new Intent(login.this,bienvenido.class);
                    startActivity(segundoForm);
                }
                else{
                    Toast.makeText(getApplicationContext(), "UsuarioAPI Incorrecto", Toast.LENGTH_SHORT).show();
                }
            }

        });
        */

    }
}
