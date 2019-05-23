package com.example.proyecto2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //variables para enlazar con la interfaz
    private EditText username;
    private EditText password;
    private Button login_btn;
    private Button reg_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //algo asi como el "Main"
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginButton(); //metodo que hace que los botones funcionen
    }

    public void LoginButton(){
        //variables enlazadas
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login);
        reg_btn = findViewById(R.id.register);


        //Hace que se haga el log in y se pase a la lista de recetas
        login_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // la funcion getText() te da el texto que la app obtiene del campo con toString() lo convierte en un string
                        if(username.getText().toString().equals("User") && password.getText().toString().equals("1234")){
                            Toast.makeText(MainActivity.this,"User and password is correct",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(".RecipeList");
                            //Cualquier envio de datos o verificacion debe hacerse antes de aqui, si no se va a pasar a la
                            //siguiente actividad


                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"User and password is not correct",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        //aqui termina el log in, lo que sigue es registrar
        reg_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
            }
        });


        }
    }

