package com.example.proyecto2;


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


public class Register extends AppCompatActivity {

    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register();
    }

    public void register(){
        email =  findViewById(R.id.emailInput);
        password = findViewById(R.id.passwordInput);
        Button reg = findViewById(R.id.registerbuttonAction);


        reg.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //igual que en el log in se puede sacar la informacion desde aqui

                        if (!(email.getText().toString().equals("")) && !(password.getText().toString().equals("")) && password.length() >= 6) {
                            Toast.makeText(Register.this, "Valid email and password", Toast.LENGTH_SHORT).show();
                            uploadRegister(email.getText().toString(),password.getText().toString());

                        } else {
                            Toast.makeText(Register.this, "User or password can't be blank or password too short", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
    //metodo para subir la informacion del usuario
    public void uploadRegister(final String pUsername, final String pass){
        try {
            String api = "https://cryptic-mesa-87439.herokuapp.com/";

            URL url = new URL(api +"/users/?email=" + pUsername + "&" + "password=" + pass);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder b = new StringBuilder();
            String input;

            while ((input = br.readLine()) != null) {
                b.append(input);
            }

            br.close();
            urlConnection.disconnect();
        } catch(MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
