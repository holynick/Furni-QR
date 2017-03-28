package com.example.ckfyp.furniqr;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Nick Yau on 3/18/2017.
 */

public class cartadapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    boolean showCheckBox;
    private List<cartfurnitureitem> furniture;


    public cartadapter(Activity activity, List<cartfurnitureitem> furniture, boolean showCheckBox) {
        this.activity = activity;
        this.furniture = furniture;
        this.showCheckBox = showCheckBox;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.cartrow, null);

        TextView furniname = (TextView) convertView.findViewById(R.id.furniname);
        TextView color = (TextView) convertView.findViewById(R.id.color);
        TextView textile = (TextView) convertView.findViewById(R.id.textile);
        final TextView cost = (TextView) convertView.findViewById(R.id.cost);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
        final Spinner quantity = (Spinner) convertView.findViewById(R.id.quantity);
        ImageView furniimage = (ImageView) convertView.findViewById(R.id.furniimage);

        final cartfurnitureitem m = furniture.get(position);
        Glide.with(activity).load(m.getImage()).into(furniimage);
        furniname.setText(m.getName());
        color.setText("Color: " + m.getColour());
        textile.setText("Textile: " +m.getTexttile());
        quantity.setSelection(m.getQuantity()-1);
        quantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int q = Integer.parseInt(quantity.getSelectedItem().toString());
                m.setQuantity(q);
                float amount = q * m.getCost();
                m.setAmount(amount);
                cost.setText("RM " + Float.toString(m.getAmount()));
                CartActivity.gettotalamount();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(!showCheckBox)
            checkBox.setVisibility(View.GONE);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true)
                    furniture.get(position).selected = true;
                else
                    furniture.get(position).selected = false;
                CartActivity.gettotalamount();
            }
        });



        return convertView;
    }
}
