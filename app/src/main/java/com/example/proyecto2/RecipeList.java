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
import java.util.List;

public class RecipeList extends AppCompatActivity {
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        Intent i = getIntent();
        token = i.getExtras().getString("token");
        showData();
    }


    // este seria el metodo que carga la informacion de las listas
    public ArrayList<Recipe> createLists(){
        ArrayList<Recipe> rep = new ArrayList<>();
        try{
            String api = "https://api-recetas.herokuapp.com/";

            URL url = new URL(api +"recipes");
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestProperty("Autenticacion",token);
            urlConnection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder b = new StringBuilder();
            String input;

            while((input = br.readLine()) != null){
                b.append(input);
            }

            JSONArray ar = new JSONArray(b.toString());
            for(int n = 0; n < ar.length(); n++)
            {
                JSONObject object = ar.getJSONObject(n); //-> no se que sigue
                //se crea el objeto recipe(nombre,tipo[ingrdientes],[pasos],[imagenes] -> "https://s3.us-east-2.amazonaws.com/jose-tec-lenguajes/new/" + imagen)
                // se inserta en rep.add(recipe);

            }

            br.close();
            urlConnection.disconnect();
        } catch(MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch(JSONException e){
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
                i.putExtra("Steps",adap.steps.get(position).toString());
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
