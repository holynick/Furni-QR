package com.example.ckfyp.furniqr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    ProgressDialog loading;
    String special = "", hotitem = "";
    boolean specialb = false, hotitemb = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        Intent intent =  getIntent();
        if (intent.hasExtra("special")){
            special = intent.getStringExtra("special");
            specialb = true;
        } else if (intent.hasExtra("hotitem")){
            hotitem = intent.getStringExtra("hotitem");
            hotitemb = true;
        }

        final ListView cataloglist = (ListView) findViewById(R.id.cataloglist);
        final List<furnitureitem> furniture = new ArrayList<>();
        final catalogadapter catalogadapter = new catalogadapter(this,furniture);

        loading = new ProgressDialog(CatalogActivity.this);
        loading.setTitle("Retrieving the latest catalog");
        loading.setMessage("Please wait...");
        loading.show();




        String url = "https://furnituredatabase.000webhostapp.com/furniqrshowcatalog.php?id=";
        cataloglist.setAdapter(catalogadapter);

        cataloglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String furniid = furniture.get(position).getId();
                Intent intent = new Intent(CatalogActivity.this, FurniDetailsActivity.class);
                intent.putExtra("furniid", furniid);
                CatalogActivity.this.startActivity(intent);
            }
        });



        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        loading.dismiss();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                furnitureitem f = new furnitureitem();
                                f.setId(obj.getString("furni_id"));
                                f.setName(obj.getString("furniname"));
                                f.setImage(obj.getString("furniimage"));
                                f.setDescription(obj.getString("furnidescription"));
                                f.setCost(Float.parseFloat(obj.getString("furnicost")));
                                String specialX = obj.getString("furnispecial");
                                String hotitemX = "";
                                if (specialb){
                                    if (specialX.equals(special)){
                                        furniture.add(f);
                                    }
                                } else if (hotitemb){
                                    if (hotitemX.equals(hotitem)){
                                        furniture.add(f);
                                    }
                                } else
                                furniture.add(f);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        catalogadapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cartmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_cart:
                Intent intent = new Intent(CatalogActivity.this, CartActivity.class);
                CatalogActivity.this.startActivity(intent);
                break;
            default:
                break;
        }

        return true;
    }
}
