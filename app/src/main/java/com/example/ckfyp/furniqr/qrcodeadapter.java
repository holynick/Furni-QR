package com.example.ckfyp.furniqr;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Nick Yau on 3/26/2017.
 */

public class qrcodeadapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<String> qrcode;


    public qrcodeadapter(Activity activity, List<String> qrcode) {
        this.activity = activity;
        this.qrcode = qrcode;
    }
    public int getCount() {
        return qrcode.size();
    }
    public Object getItem(int location) {
        return qrcode.get(location);
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.qrcoderow, null);


        return convertView;
    }
}
