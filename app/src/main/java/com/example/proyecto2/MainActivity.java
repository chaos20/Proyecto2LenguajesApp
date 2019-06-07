package com.example.proyecto2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
                        if( !(token.equals("unauthorized"))){
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

                String api = "http://www.recetaslocas.club/";

                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                OkHttpClient client = builder.build();


                URL url = new URL(api +"auth/login/?email="+username+"&"+"password="+pass);
                HttpURLConnection urlConnection = null;
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("POST"); //por defecto "GET"
                urlConnection.connect();

                if(urlConnection.getErrorStream() == null){
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder b = new StringBuilder();
                    String input;
                    while((input = br.readLine()) != null){
                        b.append(input);
                    }
                    JSONObject par = new JSONObject(b.toString());
                    token = par.getString("token");
                    br.close();
                }
                else{

                    token = "unauthorized";
                }
                Log.d("token",token);

                urlConnection.disconnect();
            } catch(MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }catch(JSONException e){
                e.printStackTrace();
            }
        }

    }

