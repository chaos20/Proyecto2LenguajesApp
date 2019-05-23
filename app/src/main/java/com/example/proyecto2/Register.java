package com.example.proyecto2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register();
    }

    public void register(){
        email =  findViewById(R.id.emailInput);
        password = findViewById(R.id.passwordInput);
        reg = findViewById(R.id.registerbuttonAction);


        reg.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //igual que en el log in se puede sacar la informacion desde aqui

                        if (!(email.getText().toString().equals("")) || !(password.getText().toString().equals(""))) {
                            Toast.makeText(Register.this, "Valid email and password", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(Register.this, "User or password can't be blank", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
}
