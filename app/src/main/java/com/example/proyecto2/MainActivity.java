package com.example.proyecto2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginButton();
    }

    public void LoginButton(){
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login);
        login_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(username.getText().toString().equals("User") && password.getText().toString().equals("1234")){
                            Toast.makeText(MainActivity.this,"User and password is correct",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(".RecipeList");
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"User and password is not correct",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
}
