package com.example.proyecto2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    //variables para enlazar con la interfaz
    private EditText username;
    private EditText password;
    private Button login_btn;
    private Button reg_btn;
    private String token;

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

                        login(username.getText().toString(), password.getText().toString());
                        if( token == "Autenticado"){
                            Toast.makeText(MainActivity.this,"User and password is correct",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(".RecipeList");
                            intent.putExtra("token",token);


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

        public void login(String username, String pass){
            try{
                String api = "https://api-recetas.herokuapp.com/";

                URL url = new URL(api +"login?correo="+username+"&"+"password="+pass);
                HttpURLConnection urlConnection = null;
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder b = new StringBuilder();
                String input;

                while((input = br.readLine()) != null){
                    b.append(input);
                }

                token = b.toString();

                br.close();
                urlConnection.disconnect();
            } catch(MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

