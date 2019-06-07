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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

public class SearchRecipe extends AppCompatActivity {

    private String searchObject; //Esto es el tipo de objeto a buscar, o sea si se busca un nombre, un tipo o un ingrediente
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);

        Intent i = getIntent();
        token = i.getExtras().getString("token");

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
    public ArrayList<Recipe> createLists(String obj, String searchType){

        ArrayList<Recipe> rep = new ArrayList<>();

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


            URL url = null;
            if(searchType.equals("name")){
                url = new URL(api +"recipe-by-name/?name='"+obj+"'");
            }
            else if(searchType.equals("type")){
                url = new URL(api+"recipe-by-type/?tipo='"+obj+"'");
            }
            else if(searchType.equals("ingredient")){
                url = new URL(api+"recipe-by-ingredients/?ingredients='"+obj+"'");
            }

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
    //en este caso, busca las listas con create lists y luego las muestra.
    public void showData(String obj, String searchType){
        final ListView lv;

        lv = findViewById(R.id.searchListView);

        ArrayList<Recipe> repD = new ArrayList<>(createLists(obj,searchType));

        final AdapterRecipe adap = new AdapterRecipe(this,0,repD);

        lv.setAdapter(adap);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Intent i = new Intent(getApplicationContext(),RecipeActivity.class);
                i.putExtra("Name",adap.names.get(position));
                i.putExtra("Type",adap.types.get(position));
                i.putExtra("Ingredients",turnArrToS(adap.ingredients.get(position)));
                i.putExtra("Steps",adap.steps.get(position).toString());
                i.putExtra("Images",adap.images.get(position));
                startActivity(i);
            }
        });
    }

    //aqui se haria el llamado a la busqueda y con los objetos encontrados, se pasan a tipo Recipe y se usa show data
    public void search(String obj, String type){
            obj = obj.replace(" ","_"); // -> to do
            showData(obj,type);
        }

    private String turnArrToS(ArrayList<String> l){
        String result = "";

        String fin = l.get(l.size()-1);

        for(String value : l){
            if(!value.equals(fin)){
                result = result  +value+",";
            }
            else{
                result = result  +value;
            }
        }
        return result;
    }
}
