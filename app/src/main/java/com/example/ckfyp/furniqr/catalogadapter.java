package com.example.ckfyp.furniqr;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Nick Yau on 3/17/2017.
 */

public class catalogadapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<furnitureitem> furniture;


    public catalogadapter(Activity activity, List<furnitureitem> furniture) {
        this.activity = activity;
        this.furniture = furniture;
    }
    public int getCount() {
        return furniture.size();
    }
    public Object getItem(int location) {
        return furniture.get(location);
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.catalogrow, null);
        TextView furniname = (TextView) convertView.findViewById(R.id.furniname);
        TextView furnidescription = (TextView) convertView.findViewById(R.id.furnidescription);
        TextView furnicost = (TextView) convertView.findViewById(R.id.furnicost);
        ImageView furniimage = (ImageView) convertView.findViewById(R.id.furniimage);
        furniname.setText(furniture.get(position).getName());
        furnidescription.setText(furniture.get(position).getDescription());
        furnicost.setText("RM " + Float.toString(furniture.get(position).getCost()));
        Glide.with(activity).load(furniture.get(position).getImage()).into(furniimage);

        return convertView;
    }
}
