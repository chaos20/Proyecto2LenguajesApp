package com.example.proyecto2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecipeList extends AppCompatActivity {
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        Intent i = getIntent();
        token = i.getExtras().getString("token");
        Log.d("Token recipe list",token);
        showData();
    }


    // este seria el metodo que carga la informacion de las listas
    //se crea el objeto recipe(nombre,tipo[ingrdientes],[pasos],[imagenes] -> "https://s3.us-east-2.amazonaws.com/jose-tec-lenguajes/new/" + imagen)
    // se inserta en rep.add(recipe);
    public ArrayList<Recipe> createLists(){
        ArrayList<Recipe> rep = new ArrayList<>();
        try{
            String api = "https://cryptic-mesa-87439.herokuapp.com/";

            URL url = new URL(api +"recipes");
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection)url.openConnection();

            urlConnection.setRequestProperty("Authorization",token); //-> headers

            urlConnection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder b = new StringBuilder();
            String input;

            while((input = br.readLine()) != null){
                b.append(input);
            }

            JSONObject newList = new JSONObject(b.toString());
            JSONArray recipes = newList.getJSONArray("recipes");
            for(int n = 0; n < recipes.length(); n++)
            {
                JSONArray object = recipes.getJSONArray(n);

                for(int s = 0; s < object.length(); s++) {

                    String nombre = object.getString(s);
                    s++;

                    String tipo = object.getString(s);
                    s++;
                    ArrayList<String> pIng = new ArrayList<>();
                    JSONArray jsonIng = new JSONArray();

                    jsonIng = object.getJSONArray(s);

                    for (int i = 0, count = jsonIng.length(); i < count; i++) {

                        pIng.add(jsonIng.getString(i));
                    }

                    s++;
                    String pSteps;
                    pSteps = object.getString(s);

                    s++;
                    ArrayList<String> pImag = new ArrayList<>();
                    JSONArray jsonImag = new JSONArray();

                    jsonImag = object.getJSONArray(s);

                    for (int j = 0, count = jsonImag.length(); j < count; j++) {


                        pImag.add("https://s3.us-east-2.amazonaws.com/jose-tec-lenguajes/new/" + jsonImag.getString(j));

                    }
                    nombre = nombre.replace("_"," ");

                    Recipe newRep = new Recipe(nombre,tipo,pIng,pSteps,pImag);
                    rep.add(newRep);
                }


            }
            urlConnection.disconnect();

        }catch(JSONException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        return rep;
    }


    // se encarga de mostrar la informacion en un list view
    public void showData(){
        final ListView lv;

        lv = findViewById(R.id.listViewRep);

        ArrayList<Recipe> repD = new ArrayList<>(createLists());

        final AdapterRecipe adap = new AdapterRecipe(this,0,repD);

        lv.setAdapter(adap);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Intent  i = new Intent(getApplicationContext(),RecipeActivity.class);
                i.putExtra("Name",adap.names.get(position));
                i.putExtra("Type",adap.types.get(position));
                i.putExtra("Ingredients",adap.ingredients.get(position).toString());
                i.putExtra("Steps",adap.steps.get(position));
                i.putExtra("Images",adap.images.get(position));


                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //estas son las opciones del menu de contexto, o sea los ...
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                // About option clicked.
                Intent add = new Intent(".NewRecipe");
                add.putExtra("token",token);
                startActivity(add);
                return true;
            case R.id.action_search:
                Intent search = new Intent(".SearchRecipe");
                search.putExtra("token",token);
                startActivity(search);
                return true;
            case R.id.action_about:
                Toast.makeText(getBaseContext(),"Proyecto de Lenguajes 2: Jose Fabio H, Pablo Zuñiga y Josua Jimenez",Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_refresh:
                showData();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
