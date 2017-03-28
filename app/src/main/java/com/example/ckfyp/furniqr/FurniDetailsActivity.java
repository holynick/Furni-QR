package com.example.ckfyp.furniqr;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class FurniDetailsActivity extends AppCompatActivity {

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furni_details);

        final List<cartfurnitureitem> cart = CartManager.getCart();

        final furnitureitem f = new furnitureitem();
        final cartfurnitureitem c = new cartfurnitureitem();
        String url = "https://furnituredatabase.000webhostapp.com/furniqrshowcatalog.php?id=";
        Intent intent = getIntent();
        String id = intent.getStringExtra("furniid");

        final TextView furniname = (TextView) findViewById(R.id.furniname);
        final TextView furnidescription = (TextView) findViewById(R.id.furnidescription);
        final TextView furnicost = (TextView) findViewById(R.id.furnicost);
        final ImageView furniimage = (ImageView) findViewById(R.id.furniimage);

        final Spinner colorbox = (Spinner) findViewById(R.id.colorbox);
        final Spinner textilebox = (Spinner) findViewById(R.id.textilebox);
        final Button addtocart = (Button) findViewById(R.id.addtocart);

        loading = new ProgressDialog(FurniDetailsActivity.this);
        loading.setTitle("Retrieving the furniture information");
        loading.setMessage("Please wait...");
        loading.show();


        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FurniDetailsActivity.this);
                builder.setTitle("Add to cart");
                builder.setMessage("Do you sure you want add this item to cart?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        c.setId(f.getId());
                        c.setName(f.getName());
                        c.setCost(f.getCost());
                        c.setAmount(f.getCost());
                        c.setColour(colorbox.getSelectedItem().toString());
                        c.setTexttile(textilebox.getSelectedItem().toString());
                        c.setImage(f.getImage());
                        c.setQuantity(1);
                        cart.add(c);
                    }
                });
               builder.setNegativeButton("No",null);
                builder.show();
            }
        });

        JsonArrayRequest request = new JsonArrayRequest(url + id,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                            try {
                                JSONObject obj = response.getJSONObject(0);
                                f.setId(obj.getString("furni_id"));
                                f.setName(obj.getString("furniname"));
                                f.setImage(obj.getString("furniimage"));
                                f.setDescription(obj.getString("furnidescription"));
                                f.setCost(Float.parseFloat(obj.getString("furnicost")));
                                f.setColour(obj.getString("furnicolour"));
                                f.setTexttile(obj.getString("furnitextile"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        furniname.setText(f.getName());
                        furnidescription.setText(f.getDescription());
                        furnicost.setText("RM " + Float.toString(f.getCost()));
                        Glide.with(FurniDetailsActivity.this).load(f.getImage()).into(furniimage);
                        List<String> colorarray = Arrays.asList(f.getColour().split("\\s*,\\s*"));
                        List<String> textilearray = Arrays.asList(f.getTexttile().split("\\s*,\\s*"));

                        ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(FurniDetailsActivity.this, android.R.layout.simple_spinner_item, colorarray); //selected item will look like a spinner set from XML
                        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        colorbox.setAdapter(spinneradapter);

                        ArrayAdapter<String> spinneradapter2 = new ArrayAdapter<String>(FurniDetailsActivity.this, android.R.layout.simple_spinner_item, textilearray); //selected item will look like a spinner set from XML
                        spinneradapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        textilebox.setAdapter(spinneradapter2);
                        loading.dismiss();
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
}
