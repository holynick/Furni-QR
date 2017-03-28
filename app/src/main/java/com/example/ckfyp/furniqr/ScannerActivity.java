package com.example.ckfyp.furniqr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScannerActivity extends AppCompatActivity {

    Button scanner;
    String scanresult;
    ProgressDialog progressDialog;
    String cartqrcode;
    public static String Atype;
    String carturl = "https://furnituredatabase.000webhostapp.com/furniqrshowcartdata.php?id=";
    String furniurl = "https://furnituredatabase.000webhostapp.com/furniqrshowcatalog.php?id=";
    private JSONObject obj = null;
    List<String> furniid, quantity, amount;
    JsonArrayRequest userReq= null;
    public static List<cartfurnitureitem> cart = new ArrayList<>();
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        scanner = (Button) findViewById(R.id.scanner);
        progressDialog = new ProgressDialog(ScannerActivity.this);

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(ScannerActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("Scan");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Retrieving Information...");
        progressDialog.show();
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            if (result.getContents() == null) {
                Toast.makeText(this, "You have cancelled the scan.", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
            else {
                scanresult = result.getContents().toString();
                scanresult = scanresult.replaceAll("\\s+","");
                if (scanresult .length() < 5)
                    Toast.makeText(getBaseContext(),"Invalid Qr code", Toast.LENGTH_SHORT).show();
                else{
                    cartqrcode = scanresult.substring(0,4);
                    Atype =scanresult.substring(4);
                    getCartdata();
                }

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getCartdata(){
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

            userReq = new JsonArrayRequest(furniurl + furniid.get(i),
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
                                    Intent intent = new Intent(ScannerActivity.this, ScannerResultActivity.class);
                                    ScannerActivity.this.startActivity(intent);
                                }
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
}
