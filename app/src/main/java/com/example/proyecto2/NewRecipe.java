package com.example.proyecto2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class NewRecipe extends AppCompatActivity {

    private EditText name;
    private EditText type;
    private EditText ingri;
    private EditText step;
    private ArrayList<String> ingridients = new ArrayList<>();
    private ArrayList<String> steps = new ArrayList<>();
    private Button addIngri;
    private Button addStep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        name = findViewById(R.id.nameAddEdit);
        type = findViewById(R.id.typeAddEdit);

        ingri = findViewById(R.id.ingriAddEdit);
        addIngri = findViewById(R.id.addButtonIngriTxt);
        addIngri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getIngi = ingri.getText().toString();
                if(ingridients.contains(getIngi)){
                    Toast.makeText(getBaseContext(),"Item already in the list", Toast.LENGTH_LONG).show();
                }
                else{
                    ingridients.add(getIngi);
                    Toast.makeText(getBaseContext(),"Item added to the list", Toast.LENGTH_LONG).show();
                }
            }
        });

        step = findViewById(R.id.stepsAddEdit);
        addStep = findViewById(R.id.addButtonStepsTxt);

        addStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getStep = step.getText().toString();
                if(steps.contains(getStep)){
                    Toast.makeText(getBaseContext(),"Item already in the list", Toast.LENGTH_LONG).show();
                }
                else{
                    steps.add(getStep);
                    Toast.makeText(getBaseContext(),"Item added to the list", Toast.LENGTH_LONG).show();
                }
            }
        });

        Recipe newRep = new Recipe(name.getText().toString(),type.getText().toString(),ingridients,steps,null); // -> falta agregar imagenes
        saveRecipe(newRep);


    }

    public void saveRecipe(Recipe rep){
        //guardar la receta usando la api
    }
}
