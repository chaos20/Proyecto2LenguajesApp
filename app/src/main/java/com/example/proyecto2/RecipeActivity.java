package com.example.proyecto2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RecipeActivity extends AppCompatActivity {

    String name;
    String type;
    String ingri;
    String steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent i = getIntent();
        name=i.getExtras().getString("Name");
        type=i.getExtras().getString("Type");
        ingri=i.getExtras().getString("Ingredients");
        steps=i.getExtras().getString("Steps");


        final TextView nametx = findViewById(R.id.nameRVA);
        final TextView typetx = findViewById(R.id.typeRVA);
        final TextView ingtx = findViewById(R.id.ingredientsRVA);
        final TextView stepstx = findViewById(R.id.stepsRVA);

        nametx.setText(name);
        typetx.setText(type);
        ingtx.setText(ingri);
        stepstx.setText(steps);
    }
}
