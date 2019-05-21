package com.example.proyecto2;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private String type;
    private ArrayList<String> ingridients;
    private ArrayList<String> steps;

    public Recipe(String pname, String ptype, ArrayList<String> pIngridients, ArrayList<String> pSteps){
        this.name = pname;
        this.type = ptype;
        this.ingridients = new ArrayList<>(pIngridients);
        this.steps = new ArrayList<>(pSteps);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getIngridients() {
        return ingridients;
    }

    public void setIngridients(ArrayList<String> ingridients) {
        this.ingridients = ingridients;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }


    public String IngrearrayToList(){
        String result = "";
        for(String st: ingridients){
            result += " " + st;

        }
        return result;
    }

    public String stepsarrayToList(){
        String result = "";
        for(String st: steps){
            result += " " + st;

        }
        return result;
    }
}
