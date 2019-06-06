package com.example.proyecto2;

import java.util.ArrayList;


public class Recipe {
    private String nombre;
    private String tipo;
    private ArrayList<String> ing = new ArrayList<>();
    private String steps;
    private ArrayList<String> imags = new ArrayList<>();

    public Recipe(String pname, String ptype, ArrayList<String> pIngridients, String pSteps, ArrayList<String>pImag){
        this.nombre = pname;
        this.tipo = ptype;
        this.ing = new ArrayList<>(pIngridients);
        this.steps = pSteps;
        this.imags = new ArrayList<>(pImag);
    }

    public String getName() {
        return nombre;
    }

    public void setName(String name) {
        this.nombre = name;
    }

    public String getType() {
        return tipo;
    }

    public void setType(String type) {
        this.tipo = type;
    }

    public ArrayList<String> getImages() {
        return imags;
    }

    public void setImages(ArrayList<String> images) {
        this.imags = images;
    }

    public ArrayList<String> getIngridients() {
        return ing;
    }

    public void setIngridients(ArrayList<String> ingridients) {
        this.ing = ingridients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }


    public String IngrearrayToList(){
        String result = "";
        for(String st: ing){
            result += " " + st;

        }
        return result;
    }

}