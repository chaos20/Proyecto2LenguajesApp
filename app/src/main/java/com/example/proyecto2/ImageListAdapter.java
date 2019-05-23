package com.example.proyecto2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageListAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> images;

    public ImageListAdapter(Context pcontext, ArrayList<String> imageUrls) {
        super(pcontext, R.layout.image, imageUrls);
        this.context = pcontext;
        images = new ArrayList<>(imageUrls);

        inflater = LayoutInflater.from(pcontext);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.image, parent, false);
        }

        Picasso
                .get()
                .load(images.get(position))


                .into((ImageView) convertView.findViewById(R.id.imageViewL));

        return convertView;
    }
}


