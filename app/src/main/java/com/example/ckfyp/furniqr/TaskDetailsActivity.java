package com.example.ckfyp.furniqr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskDetailsActivity extends AppCompatActivity {

    resultadapter adapter;
    List<cartfurnitureitem> cart = new ArrayList<>();
    ListView cartlist;
    TextView tvstatus, tvusername;
    List<String> furniid, quantity, amount;
    Button accept, decline;
    private JSONObject obj = null;
    String carturl = "https://furnituredatabase.000webhostapp.com/furniqrshowcartdata.php?id=";
    String furniurl = "https://furnituredatabase.000webhostapp.com/furniqrshowcatalog.php?id=";
    String statusurl = "https://furnituredatabase.000webhostapp.com/UpdateStatus.php?id=";
    ProgressDialog progressDialog;
    String cartqrcode;
    Task data = new Task();
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Intent intent = getIntent();
        data = (Task) intent.getSerializableExtra("taskdata");
        cartqrcode = data.getCartqrcode();

        cartlist = (ListView) findViewById(R.id.cartlist);
        tvstatus = (TextView) findViewById(R.id.status) ;
        tvusername = (TextView) findViewById(R.id.username) ;
        accept = (Button) findViewById(R.id.accept);
        decline = (Button) findViewById(R.id.decline);

        adapter = new resultadapter(this, cart);
        cartlist.setAdapter(adapter);

        tvusername.setText("Name: " + data.getUsername());
        tvstatus.setText("Status: " + data.getStatus());

        if (data.getStatus().equals("Accepted") || data.getStatus().equals("Declined")){
            accept.setVisibility(View.GONE);
            decline.setVisibility(View.GONE);
        }

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedata("Accepted");
            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedata("Declined");
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Retrieving task details");
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        loaddata();
    }

    private void loaddata(){
        JsonArrayRequest userReq = new JsonArrayRequest(carturl + cartqrcode,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Parsing json

                        try {
                            obj = response.getJSONObject(0);
                            furniid= Arrays.asList(obj.getString("idarray").split("\\s*,\\s*"));
                            quantity= Arrays.asList(obj.getString("quantityarray").split("\\s*,\\s*"));
                            amount= Arrays.asList(obj.getString("amountarray").split("\\s*,\\s*"));
                            getfurnidata();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }

        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(userReq);
    }
    private void getfurnidata(){

        JsonArrayRequest userReq = new JsonArrayRequest(furniurl + furniid.get(i),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Parsing json

                        try {
                            obj = response.getJSONObject(0);
                            cartfurnitureitem item = new cartfurnitureitem();
                            item.setId(furniid.get(i));
                            item.setName(obj.getString("furniname"));
                            item.setQuantity(Integer.parseInt(quantity.get(i)));
                            item.setAmount(Float.parseFloat(amount.get(i)));
                            item.setImage(obj.getString("furniimage"));
                            cart.add(item);
                            i++;
                            if ( i < furniid.size()){
                                getfurnidata();
                            } else {
                                progressDialog.dismiss();
                                i=0;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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

    private void updatedata(final String statusupdate){
        StringRequest request = new StringRequest(Request.Method.POST, statusurl + cartqrcode, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    boolean success = object.getBoolean("success");
                    if (success) {
                        Toast.makeText(getBaseContext(),"The status has updated successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TaskDetailsActivity.this, TaskActivity.class);
                        TaskDetailsActivity.this.startActivity(intent);
                    } else {
                        Toast.makeText(getBaseContext(),"Update Failed",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("statusupdate", statusupdate);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(TaskDetailsActivity.this);
        requestQueue.add(request);
    }
}
