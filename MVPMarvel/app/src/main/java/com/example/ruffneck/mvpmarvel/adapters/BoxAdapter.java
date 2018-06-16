package com.example.ruffneck.mvpmarvel.adapters;

import java.lang.reflect.Type;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.example.ruffneck.mvpmarvel.models.Character;
import com.squareup.picasso.Picasso;

import com.example.ruffneck.mvpmarvel.R;

public class BoxAdapter extends BaseAdapter{
    Context ctx;
    LayoutInflater lInflater;
    Type listType = new TypeToken<ArrayList<Character>>(){}.getType();
    ArrayList<Character> objects;
    TextView TextView;
    ImageView ImageView;

    public BoxAdapter(Context context, ArrayList characters) {
        ctx = context;
        objects = characters;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        Character p = getCharacter(position);

        TextView = view.findViewById(R.id.lvName);
        ImageView = view.findViewById(R.id.lvImage);

        TextView.setText(p.getName());

        String imagePath = p.getThumbnail().getPath()+ "/standard_xlarge" + ".";
        String imageExtension =  p.getThumbnail().getExtension();
        String imageUrl = imagePath + imageExtension;
        Picasso.get().load(imageUrl).into(ImageView);

        return view;
    }

    Character getCharacter(int position) {
        return ((Character) getItem(position));
    }

}
