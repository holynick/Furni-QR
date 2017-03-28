package com.example.ckfyp.furniqr;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Nick Yau on 3/21/2017.
 */

public class resultadapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<cartfurnitureitem> furniture;


    public resultadapter(Activity activity, List<cartfurnitureitem> furniture) {
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
            convertView = inflater.inflate(R.layout.resultrow, null);
        TextView furniname = (TextView) convertView.findViewById(R.id.furniname);
        TextView quantity= (TextView) convertView.findViewById(R.id.quantity);
        TextView amount= (TextView) convertView.findViewById(R.id.amount);
        ImageView furniimage = (ImageView) convertView.findViewById(R.id.furniimage);
        furniname.setText(furniture.get(position).getName());
        quantity.setText(Integer.toString(furniture.get(position).getQuantity()));
        amount.setText("RM " + Float.toString(furniture.get(position).getAmount()));
        Glide.with(activity).load(furniture.get(position).getImage()).into(furniimage);

        return convertView;
    }
}
