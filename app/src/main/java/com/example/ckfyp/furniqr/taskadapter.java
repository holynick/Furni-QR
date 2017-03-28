package com.example.ckfyp.furniqr;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
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

public class taskadapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<Task> task;


    public taskadapter(Activity activity, List<Task> task) {
        this.activity = activity;
        this.task = task;
    }
    public int getCount() {
        return task.size();
    }
    public Object getItem(int location) {
        return task.get(location);
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.taskrow, null);

        TextView username = (TextView) convertView.findViewById(R.id.username);
        TextView status = (TextView) convertView.findViewById(R.id.status);
        TextView taskid = (TextView) convertView.findViewById(R.id.taskid);

        username.setText(task.get(position).getUsername());
        status.setText(task.get(position).getStatus());

        if(task.get(position).getStatus().equals("Accepted"))
            status.setTextColor(ContextCompat.getColor(activity, R.color.acceptedclr));
        else if(task.get(position).getStatus().equals("Pending"))
            status.setTextColor(ContextCompat.getColor(activity, R.color.pendingclr));
        else if(task.get(position).getStatus().equals("Declined"))
            status.setTextColor(ContextCompat.getColor(activity, R.color.declinedclr));

        taskid.setText("Cart ID: " + task.get(position).getTaskid());



        return convertView;
    }
}
