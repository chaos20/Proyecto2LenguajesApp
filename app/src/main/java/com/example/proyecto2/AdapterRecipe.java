package com.example.proyecto2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterRecipe extends BaseAdapter {

    private Context c;
    public ArrayList<String> names= new ArrayList<>();
    public ArrayList<String> types= new ArrayList<>();
    public ArrayList<ArrayList<String>> ingredients= new ArrayList<>();
    public ArrayList<String> steps= new ArrayList<>();
    public ArrayList<ArrayList<String>> images = new ArrayList<>();


    public AdapterRecipe(Context ctx, int textViewResourceId, ArrayList<Recipe> recipies){
        this.c = ctx;
        for(Recipe rep : recipies){
            names.add(rep.getName());
            types.add(rep.getType());
            ingredients.add(rep.getIngridients());
            steps.add(rep.getSteps());
            images.add(rep.getImages());
        }
    }

    public AdapterRecipe(Context ctx){
        this.c = ctx;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.recipe,null);
        }

        TextView nameRV =  convertView.findViewById(R.id.nameRV);
        TextView typeRV =  convertView.findViewById(R.id.typeRV);


        nameRV.setText(names.get(position));
        typeRV.setText((types.get(position)));





        return convertView;
    }
}
