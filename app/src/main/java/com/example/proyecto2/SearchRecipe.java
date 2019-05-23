package com.example.proyecto2;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

public class SearchRecipe extends AppCompatActivity {

    private String searchObject; //Esto es el tipo de objeto a buscar, o sea si se busca un nombre, un tipo o un ingrediente


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);

        ArrayAdapter<String> SpinnerAdapter;

        Button search = findViewById(R.id.searchButton);
        final EditText Id = findViewById(R.id.searchText); //esto es lo que se planea buscar, o sea el nombre de la receta, el tipo o el ingrediente.
        Spinner spinner = findViewById(R.id.spinnerSearch);
        ArrayList<String> list = new ArrayList<>();


        //setup del spinner

        list.add("Select a category");
        list.add("name");
        list.add("type");
        list.add("ingredient");

        SpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list){
            public View getView(int position, View convertView, ViewGroup parent){
                View v = super.getView(position, convertView, parent);
                return v;
            }
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                View v = super.getDropDownView(position,convertView,parent);
                return v;
            }

        };

        SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(SpinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchObject = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        search.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        search(Id.getText().toString(),searchObject );
                    }

                    });


    }
    public ArrayList<Recipe> createLists(){
        ArrayList<Recipe> rep = new ArrayList<>();

        ArrayList<String> f1 = new ArrayList<>();
        ArrayList<String> ss = new ArrayList<>();

        ArrayList<String> sl = new ArrayList<>();
        ArrayList<String> sl2 = new ArrayList<>();

        f1.add("pork");
        f1.add("onions");
        f1.add("salt");

        sl.add("Condiment the pork to the personal taste");
        sl.add("preheat the oven to 300 °F");
        sl.add("cook until it reaches the consistency desired");

        ss.add("Tomato sauce");
        ss.add("pasta");
        ss.add("salt");

        sl2.add("Condiment the sauce to the personal taste");
        sl2.add("cook the pasta");
        sl2.add("add the sauce");



        Recipe recipe1 = new Recipe("Roasted pork", "meat",f1,sl);
        Recipe recipe2 = new Recipe("Spaghetti Carbonara","pasta",ss,sl2);



        rep.add(recipe1);
        rep.add(recipe2);

        return rep;
    }


    // se encarga de mostrar la informacion en un list view
    //en este caso, busca las listas con create lists y luego las muestra.
    public void showData(){
        final ListView lv;

        lv = findViewById(R.id.searchListView);

        ArrayList<Recipe> repD = new ArrayList<>(createLists());

        final AdapterRecipe adap = new AdapterRecipe(this,0,repD);

        lv.setAdapter(adap);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Intent i = new Intent(getApplicationContext(),RecipeActivity.class);
                i.putExtra("Name",adap.names.get(position));
                i.putExtra("Type",adap.types.get(position));
                i.putExtra("Ingredients",adap.ingredients.get(position).toString());
                i.putExtra("Steps",adap.steps.get(position).toString());
                startActivity(i);
            }
        });
    }

    //aqui se haria el llamado a la busqueda
    public void search(String obj, String type){
                    showData();
        }





}
