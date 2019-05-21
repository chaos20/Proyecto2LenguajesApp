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

import java.util.ArrayList;
import java.util.List;

public class RecipeList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        final ListView lv;

        lv = findViewById(R.id.listViewRep);


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





        ArrayList<String> huh = new ArrayList<>();
        huh.add("Fuck");
        huh.add("This");
        huh.add("SHit");

        final AdapterRecipe adap = new AdapterRecipe(this,0,rep);

        ArrayAdapter<Recipe> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, rep);
        lv.setAdapter(adap);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Intent  i = new Intent(getApplicationContext(),RecipeActivity.class);
                i.putExtra("Name",adap.names.get(position));
                i.putExtra("Type",adap.types.get(position));
                i.putExtra("Ingredients",adap.ingredients.get(position).toString());
                i.putExtra("Steps",adap.steps.get(position).toString());
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                // About option clicked.
                Intent add = new Intent(".NewRecipe");
                startActivity(add);
                return true;
            case R.id.action_search:
                Intent search = new Intent(".SearchRecipe");
                startActivity(search);
                return true;
            case R.id.action_about:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
