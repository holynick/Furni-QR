package com.example.ckfyp.furniqr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskActivity extends AppCompatActivity {

    ListView tasklist;
    taskadapter adapter;
    List<Task> task =  new ArrayList<>();
    String carturl = "https://furnituredatabase.000webhostapp.com/furniqrshowcartdata.php?id=";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        tasklist = (ListView) findViewById(R.id.tasklist);

        adapter = new taskadapter(this, task);
        tasklist.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Retrieving Tasks");
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        tasklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TaskActivity.this, TaskDetailsActivity.class);
                intent.putExtra("taskdata",task.get(position));
                TaskActivity.this.startActivity(intent);
            }
        });

        JsonArrayRequest userReq = new JsonArrayRequest(carturl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();
                        for (int i = 0; i <response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Task data = new Task();
                                data.setTaskid(Integer.parseInt(obj.getString("cart_id")));
                                data.setIdarray(obj.getString("idarray"));
                                data.setQuantityarray(obj.getString("quantityarray"));
                                data.setAmountarray(obj.getString("amountarray"));
                                data.setTotalamount(obj.getString("totalamount"));
                                data.setCartqrcode(obj.getString("cartqrcode"));
                                data.setUserid(obj.getString("userid"));
                                data.setUsername(obj.getString("username"));
                                data.setStatus(obj.getString("status"));
                                task.add(data);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }

        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(userReq);

    }
}
